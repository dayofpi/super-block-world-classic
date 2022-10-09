package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.common.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.common.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.common.entities.hostile.TweesterEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

public class TweesterTask extends Task<PeteyPiranhaEntity> {
    private static final int SOUND_DELAY = MathHelper.ceil(16.0);
    private static final int RUN_TIME = MathHelper.ceil(30.0f);

    public TweesterTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, PeteyPiranhaEntity entity) {
        if (entity.getNextAttack() != 2)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 10.0, 10.0)).isPresent();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        return true;
    }

    @Override
    protected void run(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);
        serverWorld.sendEntityStatus(entity, EntityStatuses.SONIC_BOOM);
    }

    @Override
    protected void keepRunning(ServerWorld world, PeteyPiranhaEntity entity, long l) {
        entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> entity.getLookControl().lookAt(target.getPos()));
        if (entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            return;
        }
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - SOUND_DELAY);
        Random random = entity.getRandom();
        for (int i = 0; i < 3; ++i) {
            TweesterEntity tweesterEntity = ModEntities.TWEESTER.create(world);
            if (tweesterEntity == null)
                return;
            tweesterEntity.updatePosition(entity.getX() + random.nextFloat(), entity.getY(), entity.getZ() + random.nextFloat());
            Vec3i vector = entity.getHorizontalFacing().getVector();
            tweesterEntity.setVelocity(vector.getX() * 0.5, 0.0D, vector.getZ() * 0.5);
            world.spawnEntity(tweesterEntity);
        }
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        Random random = entity.getRandom();
        ModBossEntity.cooldown(entity, 50);
        entity.setNextAttack(random.nextInt(entity.getMaxAttacks()));
    }
}

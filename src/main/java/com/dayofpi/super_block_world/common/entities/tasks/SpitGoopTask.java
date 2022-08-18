package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.common.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.common.entities.projectile.GoopEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

public class SpitGoopTask extends Task<PeteyPiranhaEntity> {
    private static final int SOUND_DELAY = MathHelper.ceil(34.0);
    private static final int RUN_TIME = MathHelper.ceil(10.0f);

    public SpitGoopTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, PeteyPiranhaEntity entity) {
        if (entity.getNextAttack() != 0)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 10.0, 2.0)).isPresent();
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
        entity.playSound(Sounds.ENTITY_PETEY_PIRANHA_STOCKPILE, 3.0f, 1.0f);
    }

    @Override
    protected void keepRunning(ServerWorld world, PeteyPiranhaEntity entity, long l) {
        entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> entity.getLookControl().lookAt(target.getPos()));
        if (entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            return;
        }
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - SOUND_DELAY);
        entity.playSound(Sounds.ENTITY_PETEY_PIRANHA_SPIT, 3.0F, 1.0F);
        GoopEntity goopEntity = new GoopEntity(world, entity);
        goopEntity.setVelocity(entity, entity.getPitch(), entity.getYaw(), 0.0F, 1.0F, 3.0F);
        world.spawnEntity(goopEntity);
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        ModBossEntity.cooldown(entity, 50);
        Random random = entity.getRandom();
        entity.setNextAttack(random.nextInt(entity.getMaxAttacks()));
    }
}

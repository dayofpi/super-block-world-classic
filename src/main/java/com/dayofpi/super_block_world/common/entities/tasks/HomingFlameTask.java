package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.common.entities.boss.KingBooEntity;
import com.dayofpi.super_block_world.common.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.common.entities.projectile.HomingFlameEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;

public class HomingFlameTask extends Task<KingBooEntity> {
    private static final int SOUND_DELAY = MathHelper.ceil(34.0);
    private static final int RUN_TIME = MathHelper.ceil(10.0f);

    public HomingFlameTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBooEntity entity) {
        if (entity.getNextAttack() != 2)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 20.0, 10.0)).isPresent();
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBooEntity entity, long l) {
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);
        entity.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 3.0F, 1.0F);
        HomingFlameEntity homingFlame = new HomingFlameEntity(serverWorld, entity);
        serverWorld.spawnEntity(homingFlame);
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, KingBooEntity entity, long l) {
        ModBossEntity.cooldown(entity, 50);
        entity.setNextAttack(entity.getRandom().nextInt(entity.getMaxAttacks()));
    }
}

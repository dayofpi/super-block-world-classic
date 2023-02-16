package com.dayofpi.super_block_world.entity.entities.tasks;

import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

import java.util.Optional;

public class PickUpTargetTask extends Task<KingBobOmbEntity> {
    private final int interval;

    public PickUpTargetTask(int interval) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT));
        this.interval = interval;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBobOmbEntity mobEntity) {
        LivingEntity livingEntity = this.getAttackTarget(mobEntity);
        return livingEntity != null && mobEntity.getCarriedEntity() == null && mobEntity.isInAttackRange(livingEntity);
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBobOmbEntity entity, long l) {
        LivingEntity livingEntity = this.getAttackTarget(entity);
        LookTargetUtil.lookAt(entity, livingEntity);
        entity.swingHand(Hand.MAIN_HAND);
        entity.setCarriedEntity(livingEntity.getUuid());
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);
    }

    private LivingEntity getAttackTarget(MobEntity entity) {
        Optional<LivingEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optional.orElse(null);
    }
}

package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3i;

public class ThrowTargetTask extends Task<KingBobOmbEntity> {
    private final int interval;

    public ThrowTargetTask(int interval) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT));
        this.interval = interval;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBobOmbEntity mobEntity) {
        return mobEntity.getCarriedEntity() != null;
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBobOmbEntity entity, long l) {
        Entity carriedEntity = entity.getCarriedEntity();
        if (carriedEntity instanceof LivingEntity) {
            LookTargetUtil.lookAt(entity, (LivingEntity) carriedEntity);
            entity.swingHand(Hand.MAIN_HAND);
            entity.playSound(Sounds.ENTITY_KING_BOB_OMB_THROW, 2.0F, 0.5F);
            Vec3i vector = entity.getHorizontalFacing().getVector();
            carriedEntity.stopRiding();
            carriedEntity.setVelocity(vector.getX(), 1.7, vector.getZ());
            entity.setCarriedEntity(null);
            entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);
        }
    }
}

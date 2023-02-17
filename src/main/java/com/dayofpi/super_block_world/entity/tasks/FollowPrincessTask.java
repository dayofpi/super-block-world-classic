package com.dayofpi.super_block_world.entity.tasks;

import com.dayofpi.super_block_world.item.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class FollowPrincessTask extends MultiTickTask<PassiveEntity> {
    private final UniformIntProvider executionRange;
    private final float speed;
    
    public FollowPrincessTask(UniformIntProvider executionRange, float speed) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
        this.executionRange = executionRange;
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PassiveEntity entity) {
        PlayerEntity playerEntity = this.getNearestPrincess(entity);
        return entity.isInRange(playerEntity, this.executionRange.getMax() + 1) && !entity.isInRange(playerEntity, this.executionRange.getMin()) && playerEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.PRINCESS_CROWN);
    }

    @Override
    protected void run(ServerWorld world, PassiveEntity entity, long time) {
        LookTargetUtil.walkTowards(entity, this.getNearestPrincess(entity), this.speed, this.executionRange.getMin() - 1);
    }

    private PlayerEntity getNearestPrincess(PassiveEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
    }
}

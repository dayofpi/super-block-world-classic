package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class FollowMagnetTask extends Task<FuzzyEntity> {
    private final UniformIntProvider executionRange;
    private final float speed;

    public FollowMagnetTask(UniformIntProvider executionRange, float speed) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleState.VALUE_PRESENT));
        this.executionRange = executionRange;
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FuzzyEntity entity) {
        PlayerEntity playerEntity = this.getNearestPlayer(entity);
        return entity.isInRange(playerEntity, this.executionRange.getMax() + 1) && !entity.isInRange(playerEntity, this.executionRange.getMin()) && playerEntity.isUsingItem() && playerEntity.getActiveItem().isOf(ModItems.FUZZY_MAGNET);
    }

    @Override
    protected void run(ServerWorld world, FuzzyEntity entity, long time) {
        if (!entity.isGlowing())
            entity.setGlowing(true);
        LookTargetUtil.walkTowards(entity, this.getNearestPlayer(entity), this.speed, this.executionRange.getMin() - 1);
    }

    @Override
    protected void finishRunning(ServerWorld world, FuzzyEntity entity, long time) {
        entity.setGlowing(false);
    }

    private PlayerEntity getNearestPlayer(FuzzyEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
    }
}

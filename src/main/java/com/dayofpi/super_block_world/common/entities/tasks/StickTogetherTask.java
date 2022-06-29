package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class StickTogetherTask extends Task<FuzzyEntity> {
    private final UniformIntProvider executionRange;
    private final float speed;

    public StickTogetherTask(UniformIntProvider executionRange, float speed) {
        super(ImmutableMap.of(Main.NEAREST_FUZZY, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
        this.executionRange = executionRange;
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FuzzyEntity entity) {
        FuzzyEntity playerEntity = this.getClosestFuzzy(entity);
        return entity.isInRange(playerEntity, this.executionRange.getMax() + 1) && !entity.isInRange(playerEntity, this.executionRange.getMin()) && playerEntity.isUsingItem() && playerEntity.getActiveItem().isOf(ModItems.FUZZY_MAGNET);
    }

    @Override
    protected void run(ServerWorld world, FuzzyEntity entity, long time) {
        LookTargetUtil.walkTowards(entity, this.getClosestFuzzy(entity), this.speed, this.executionRange.getMin() - 1);
    }

    private FuzzyEntity getClosestFuzzy(FuzzyEntity entity) {
        return entity.getBrain().getOptionalMemory(Main.NEAREST_FUZZY).get();
    }
}

package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

public class StickToFuzzballTask extends Task<FuzzyEntity> {
    private final int range;
    private final float speed;

    public StickToFuzzballTask(int range, float speed) {
        super(ImmutableMap.of());
        this.range = range;
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FuzzyEntity entity) {
        if (entity.getBlockStateAtPos().isOf(ModBlocks.FUZZBALL)) {
            return !entity.getBlockStateAtPos().get(Properties.POWERED);
        }
        return true;
    }

    @Override
    protected void run(ServerWorld world, FuzzyEntity entity, long time) {
        BlockPos blockPos = null;
        BlockPos blockPos2 = null;
        BlockPos origin = entity.getBlockPos();
        Iterable<BlockPos> iterable = BlockPos.iterateOutwards(origin, this.range, this.range, this.range);

        for (BlockPos blockPos4 : iterable) {
            if (blockPos4.getX() != origin.getX() || blockPos4.getZ() != origin.getZ()) {
                BlockState blockState = entity.world.getBlockState(blockPos4.up());
                BlockState blockState2 = entity.world.getBlockState(blockPos4);
                if (blockState2.isOf(ModBlocks.FUZZBALL) && blockState2.get(Properties.POWERED)) {
                    if (blockState.isAir()) {
                        blockPos = blockPos4.toImmutable();
                        break;
                    }

                    if (blockPos2 == null && !blockPos4.isWithinDistance(entity.getPos(), 1.5)) {
                        blockPos2 = blockPos4.toImmutable();
                    }
                }
            }
        }

        if (blockPos == null) {
            blockPos = blockPos2;
        }

        if (blockPos != null) {
            LookTargetUtil.walkTowards(entity, blockPos, this.speed, 0);
        }

    }
}

package com.dayofpi.super_block_world.common.block.plant;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class FlowerbedBlock extends CarpetBlock implements Fertilizable {
    public FlowerbedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int k = pos.getY();
        if (k >= world.getBottomY() + 1 && k + 1 < world.getTopY()) {
            int i = 8;
            int j = 4;
            BlockState newState = state;
            if (random.nextInt(20) == 0) {
                newState = PlantBlocks.HYBRID_FLOWERBED.getDefaultState();
            }
            for (int m = 0; m < i * i; ++m) {
                BlockPos blockPos = pos.add(random.nextInt(i) - random.nextInt(i), random.nextInt(j) - random.nextInt(j), random.nextInt(i) - random.nextInt(i));
                if (world.isAir(blockPos) && blockPos.getY() > world.getBottomY() && canGrowOn(world, blockPos)) {
                    world.setBlockState(blockPos, newState, Block.NOTIFY_LISTENERS);
                }
            }
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    public boolean canGrowOn(WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT);
    }
}

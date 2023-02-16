package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CherrySoilBlock extends Block implements Fertilizable {
    public CherrySoilBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos blockPos, BlockState state) {
        for (BlockPos soil : BlockPos.iterateOutwards(blockPos, 3, 3, 3)) {
            if (world.getBlockState(soil).isOf(ModBlocks.CHERRY_SOIL) && !world.getBlockState(soil.up()).isSideSolidFullSquare(world, soil.up(), Direction.DOWN))
                world.setBlockState(soil, ModBlocks.CHERRY_GRASS.getDefaultState());
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.isOf(ModBlocks.CHERRY_SOIL) && !world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
}

package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ToppedVanillateBlock extends Block implements Fertilizable {
    public ToppedVanillateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos roof = pos.up();
        label46:
        for (int i = 0; i < 128; ++i) {
            BlockPos roof1 = roof;

            for (int j = 0; j < i / 16; ++j) {
                roof1 = roof1.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!world.getBlockState(roof1.down()).isOf(this) || world.getBlockState(roof1).isFullCube(world, roof1)) {
                    continue label46;
                }
            }

            BlockState blockState2 = world.getBlockState(roof1);
            if (blockState2.isAir()) {
                world.setBlockState(roof1, (random.nextBoolean() ? ModBlocks.CAVE_MUSHROOMS : ModBlocks.BLUE_CAVE_MUSHROOMS).getDefaultState());
            }
        }
    }
}

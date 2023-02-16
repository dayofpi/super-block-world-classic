package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BeanstalkBlock extends AbstractPlantStemBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);

    public BeanstalkBlock(Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.2);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.BEANSTALK_STEM;
    }
}

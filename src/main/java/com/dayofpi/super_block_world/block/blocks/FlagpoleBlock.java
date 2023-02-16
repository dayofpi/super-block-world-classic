package com.dayofpi.super_block_world.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class FlagpoleBlock extends Block {
    protected static final VoxelShape FLAGPOLE_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);

    public FlagpoleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FLAGPOLE_SHAPE;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }
}

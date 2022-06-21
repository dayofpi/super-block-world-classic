package com.dayofpi.super_block_world.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.piston.PistonBehavior;

@SuppressWarnings("deprecation")
public class WarpFrameBlock extends PillarBlock {
    public WarpFrameBlock(Settings settings) {
        super(settings);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }
}

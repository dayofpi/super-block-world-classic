package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.registry.ModBlocks;
import net.kyrptonaught.customportalapi.portal.frame.FlatPortalAreaHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ModFrameTester extends FlatPortalAreaHelper {
    public ModFrameTester() {
    }

    @Override
    public void createPortal(World world, BlockPos pos, BlockState blockState, Direction.Axis axis) {
        super.createPortal(world, pos, blockState, axis);
    }

    protected void placeLandingPad(World world, BlockPos pos, BlockState blockState) {
        blockState = ModBlocks.HARDSTONE_BRICKS.getDefaultState();
        if (!world.getBlockState(pos).getMaterial().isSolid())
            world.setBlockState(pos, blockState);
    }
}
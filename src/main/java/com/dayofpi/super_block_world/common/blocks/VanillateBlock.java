package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class VanillateBlock extends Block {
    public VanillateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && world.getFluidState(neighborPos).isIn(FluidTags.LAVA))
            return ModBlocks.TOPPED_VANILLATE.getDefaultState();
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}

package com.dayofpi.super_block_world.main.common.block.building;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.misc.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("deprecation")
public class VanillateBlock extends Block {
    public VanillateBlock(Settings settings) {
        super(settings);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        FluidState fluidState = world.getFluidState(pos.up());
        if (direction == Direction.UP && fluidState.isIn(FluidTags.LAVA) && !fluidState.isIn(TagRegistry.POISON)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        FluidState fluidState = world.getFluidState(pos.up());
        if (fluidState.isIn(FluidTags.LAVA) && !fluidState.isIn(TagRegistry.POISON)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
    }

    private  BlockState getToppedState(BlockState originalState) {
        return originalState.isOf(BlockRegistry.FROSTY_VANILLATE) ? BlockRegistry.FROSTED_VANILLATE.getDefaultState() : BlockRegistry.TOPPED_VANILLATE.getDefaultState();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, getToppedState(state), world, pos));
    }

}

package com.dayofpi.sbw_main.common.block.type;

import com.dayofpi.sbw_main.util.ModTags;
import com.dayofpi.sbw_main.registry.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PoisonBlock extends FluidBlock {
    public PoisonBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (this.receiveNeighborFluids(world, pos)) {
            world.createAndScheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (this.receiveNeighborFluids(world, pos)) {
            world.createAndScheduleFluidTick(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }

    private boolean receiveNeighborFluids(World world, BlockPos pos) {
        if (this.fluid.isIn(ModTags.POISON)) {
            int size = FLOW_DIRECTIONS.size();
            for (int i = 0; i < size; i++) {
                BlockPos blockPos = pos.add(1, 1, 1);
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER) && !world.getFluidState(blockPos).isIn(ModTags.POISON)) {
                    Block block = Blocks.AIR;
                    world.setBlockState(blockPos, block.getDefaultState());
                    playExtinguishEvent(world, pos);
                    return false;
                }

                if (world.getFluidState(blockPos).isIn(FluidTags.LAVA)) {
                    Block block = ModBlocks.VANILLATE_CRUMBLE;
                    world.setBlockState(pos, block.getDefaultState());
                    this.playExtinguishEvent(world, pos);
                    return false;
                }
            }
        }
        return true;
    }

    private void playExtinguishEvent(WorldAccess world, BlockPos pos) {
        world.syncWorldEvent(1501, pos, 0);
    }
}

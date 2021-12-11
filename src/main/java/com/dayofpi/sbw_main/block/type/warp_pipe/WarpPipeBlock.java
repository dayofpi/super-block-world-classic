package com.dayofpi.sbw_main.block.type.warp_pipe;

import com.dayofpi.sbw_main.block.block_entity.WarpPipeBE;
import com.dayofpi.sbw_main.block.block_entity.WarpPipeTree;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class WarpPipeBlock extends BronzePipeBlock implements BlockEntityProvider {

    public static final WarpPipeTree warpPipeTree = new WarpPipeTree();

    public WarpPipeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (state.get(FACING) == Direction.UP) {
            if (state.get(WATERLOGGED))
                world.createAndScheduleBlockTick(pos, this, 20);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.isOf(Blocks.WATER)) {
            world.createAndScheduleBlockTick(pos, this, 20);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BubbleColumnBlock.update(world, pos.up(), state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if (state.hasBlockEntity() && !state.get(WATERLOGGED) && state.get(FACING) == Direction.UP && !state.isOf(newState.getBlock())) {
            warpPipeTree.removeBlockFromChunk(pos.getX()/16, pos.getZ()/16, pos); //if destroyed, remove from list
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WarpPipeBE(pos, state);
    }

}

package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.blocks.block_entities.WarpPipeBE;
import com.dayofpi.super_block_world.common.util.block.WarpPipeTree;
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
public class WarpPipeBlock extends AbstractPipe implements BlockEntityProvider {

    public static final WarpPipeTree warpPipeTree = new WarpPipeTree();

    public WarpPipeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos blockPos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, blockPos, oldState, notify);
        if (state.get(FACING) == Direction.UP) {
            warpPipeTree.addBlockToChunk(blockPos.getX()/16, blockPos.getZ()/16, blockPos);
            if (state.get(WATERLOGGED))
                world.createAndScheduleBlockTick(blockPos, this, 20);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.isOf(Blocks.WATER)) {
            world.createAndScheduleBlockTick(blockPos, this, 20);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, blockPos, neighborPos);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        BubbleColumnBlock.update(world, blockPos.up(), state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, blockPos, newState, moved);
        if (world.getBlockEntity(blockPos) != null && !state.isOf(newState.getBlock()) && state.get(FACING) == Direction.UP) {
            warpPipeTree.removeBlockFromChunk(blockPos.getX()/16, blockPos.getZ()/16, blockPos); //if destroyed, remove from list
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState state) {
        return new WarpPipeBE(blockPos, state);
    }

}

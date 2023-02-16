package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.entity.entities.misc.PropellerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class PropellerBlock extends HorizontalFacingBlock {
    public PropellerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world instanceof ServerWorld) {
            this.rise(state, (ServerWorld) world, pos);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world instanceof ServerWorld) {
            this.rise(state, (ServerWorld) world, pos);
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    private void rise(BlockState blockState, ServerWorld world, BlockPos blockPos) {
        if (!world.isReceivingRedstonePower(blockPos) || blockPos.getY() >= world.getTopY()) {
            return;
        }
        PropellerBlockEntity.spawnFromBlock(world, blockPos, blockState);
    }
}

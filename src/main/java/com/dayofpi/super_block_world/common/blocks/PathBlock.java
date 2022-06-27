package com.dayofpi.super_block_world.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class PathBlock extends DirtPathBlock {
    private final Block soil;
    public PathBlock(Block soil, Settings settings) {
        super(settings);
        this.soil = soil;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (!this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return Block.pushEntitiesUpBeforeBlockChange(this.getDefaultState(), soil.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, soil.getDefaultState(), world, pos));
    }
}

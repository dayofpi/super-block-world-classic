package com.dayofpi.super_block_world.main.common.block_entity;

import com.dayofpi.super_block_world.main.common.block.decoration.pipe.WarpPipeBlock;
import com.dayofpi.super_block_world.main.registry.misc.TagRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WarpPipeBE extends BlockEntity {
    public WarpPipeBE(BlockPos blockPos, BlockState state) {
        super(BlockEntityRegistry.WARP_PIPE, blockPos, state);
        if (!WarpPipeBlock.warpPipeTree.getChunk(blockPos.getX() / 16, blockPos.getZ() / 16).warpList.contains(blockPos)) {
            if (state.isIn(TagRegistry.WARP_PIPES) && state.get(Properties.FACING) == Direction.UP)
                WarpPipeBlock.warpPipeTree.addBlockToChunk(blockPos.getX() / 16, blockPos.getZ() / 16, blockPos);
        }
    }
}
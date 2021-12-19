package com.dayofpi.super_block_world.main.common.block_entity;

import com.dayofpi.super_block_world.main.registry.block.BlockEntityRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.common.block.pipe.WarpPipeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WarpPipeBE extends BlockEntity {
    public WarpPipeBE(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WARP_PIPE, pos, state);
        if (!WarpPipeBlock.warpPipeTree.getChunk(pos.getX() / 16, pos.getZ() / 16).warpList.contains(pos)) {
            if (state.isOf(BlockRegistry.WARP_PIPE) && state.get(Properties.FACING) == Direction.UP)
                WarpPipeBlock.warpPipeTree.addBlockToChunk(pos.getX() / 16, pos.getZ() / 16, pos);
        }
    }
}
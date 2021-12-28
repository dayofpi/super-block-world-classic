package com.dayofpi.super_block_world.main.common.block_entity;

import com.dayofpi.super_block_world.main.common.block.pipe.WarpPipeBlock;
import com.dayofpi.super_block_world.main.registry.general.TagRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WarpPipeBE extends BlockEntity {
    public WarpPipeBE(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WARP_PIPE, pos, state);
        if (!WarpPipeBlock.warpPipeTree.getChunk(pos.getX() / 16, pos.getZ() / 16).warpList.contains(pos)) {
            if (state.isIn(TagRegistry.WARP_PIPES) && state.get(Properties.FACING) == Direction.UP)
                WarpPipeBlock.warpPipeTree.addBlockToChunk(pos.getX() / 16, pos.getZ() / 16, pos);
        }
    }
}
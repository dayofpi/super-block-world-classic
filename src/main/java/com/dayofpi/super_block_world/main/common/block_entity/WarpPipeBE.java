package com.dayofpi.super_block_world.main.common.block_entity;

import com.dayofpi.super_block_world.main.registry.block.BlockEntityRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.common.block.pipe.WarpPipeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WarpPipeBE extends BlockEntity {
    public WarpPipeBE(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WARP_PIPE, pos, state);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        if (!WarpPipeBlock.warpPipeTree.getChunk(pos.getX() / 16, pos.getZ() / 16).warpList.contains(pos)) {
            final BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf(BlockRegistry.WARP_PIPE) && blockState.get(Properties.FACING) == Direction.UP && !blockState.get(Properties.WATERLOGGED))
                WarpPipeBlock.warpPipeTree.addBlockToChunk(pos.getX() / 16, pos.getZ() / 16, pos);
        }
    }
}

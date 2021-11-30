package com.dayofpi.sbw_main.block.block_entity;

import com.dayofpi.sbw_main.block.registry.ModBlockEntities;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.type.warp_pipe.WarpPipeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WarpPipeBE extends BlockEntity {
    public WarpPipeBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WARP_PIPE, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {
        super.readNbt(nbtCompound);
        if (this.world != null)
            if (this.world.getBlockState(pos).isOf(ModBlocks.WARP_PIPE) && this.world.getBlockState(pos).get(Properties.FACING) == Direction.UP)
                WarpPipeBlock.warpPipeTree.addBlockToChunk(pos.getX()/16, pos.getZ()/16, pos);
    }
}

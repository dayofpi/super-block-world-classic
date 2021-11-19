package com.dayofpi.sbw_main.block.block_entity;

import com.dayofpi.sbw_main.block.registry.ModBlockEntities;
import com.dayofpi.sbw_main.block.types.WarpPipeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WarpPipeBE extends BlockEntity {
    public WarpPipeBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WARP_PIPE, pos, state);
    }

    public void setWorld(World world) {
        super.setWorld(world);
        WarpPipeBlock.warpPipeTree.addBlockToChunk(pos.getX()/16, pos.getZ()/16, pos);
    }


    @Override
    public void readNbt(NbtCompound nbtCompound) {
        super.readNbt(nbtCompound);
        WarpPipeBlock.warpPipeTree.addBlockToChunk(pos.getX()/16, pos.getZ()/16, pos);
    }
}

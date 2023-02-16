package com.dayofpi.super_block_world.block.block_entities;

import com.dayofpi.super_block_world.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SpikeTrapBE extends BlockEntity {
    public SpikeTrapBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPIKE_TRAP, pos, state);
    }
}

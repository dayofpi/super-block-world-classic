package com.dayofpi.super_block_world.block.block_entities;

import com.dayofpi.super_block_world.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PlacedItemBE extends BlockEntity {
    public PlacedItemBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLACED_ITEM, pos, state);
    }
}

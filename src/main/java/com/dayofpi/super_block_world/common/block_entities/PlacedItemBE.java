package com.dayofpi.super_block_world.common.block_entities;

import com.dayofpi.super_block_world.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PlacedItemBE extends BlockEntity {
    public PlacedItemBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLACED_ITEM, pos, state);
    }
}

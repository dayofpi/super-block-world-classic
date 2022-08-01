package com.dayofpi.super_block_world.common.block_entities;

import com.dayofpi.super_block_world.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SuperPickaxBE extends BlockEntity {
    public SuperPickaxBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SUPER_PICKAX, pos, state);
    }
}

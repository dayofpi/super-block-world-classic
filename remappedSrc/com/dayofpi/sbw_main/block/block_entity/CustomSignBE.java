package com.dayofpi.sbw_main.block.block_entity;

import com.dayofpi.sbw_main.block.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;

public class CustomSignBE extends SignBlockEntity {
    public CustomSignBE(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public BlockEntityType<?> getType() {
        return ModBlockEntities.MOD_SIGN;
    }
}

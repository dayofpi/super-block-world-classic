package com.dayofpi.super_block_world.main.common.block_entity;

import com.dayofpi.super_block_world.main.registry.block.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;

public class CustomSignBE extends SignBlockEntity {
    public CustomSignBE(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public BlockEntityType<?> getType() {
        return BlockEntityRegistry.SIGN;
    }
}

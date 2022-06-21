package com.dayofpi.super_block_world.common.block_entities;

import com.dayofpi.super_block_world.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class YoshiEggBE extends BlockEntity {
    private NbtCompound storedEntity = new NbtCompound();

    public YoshiEggBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.YOSHI_EGG, pos, state);
    }

    public NbtCompound getStoredEntity() {
        return storedEntity;
    }

    public void setStoredEntity(NbtCompound nbtCompound) {
        this.storedEntity = nbtCompound;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.getStoredEntity().isEmpty()) {
            nbt.put("StoredEntity", this.getStoredEntity());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("StoredEntity")) {
            this.setStoredEntity(nbt.getCompound("StoredEntity"));
        }
    }
}

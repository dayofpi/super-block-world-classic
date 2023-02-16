package com.dayofpi.super_block_world.block.block_entities;

import com.dayofpi.super_block_world.block.blocks.FlagBlock;
import com.dayofpi.super_block_world.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FlagBE extends BlockEntity {
    private final boolean rainbow;
    private final DyeColor color;
    @Nullable
    private Text customName;

    public FlagBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLAG, pos, state);
        this.rainbow = ((FlagBlock) state.getBlock()).isRainbow();
        this.color = ((FlagBlock) state.getBlock()).getColor();
    }

    public FlagBE(BlockPos pos, BlockState state, DyeColor color, boolean rainbow) {
        super(ModBlockEntities.FLAG, pos, state);
        this.rainbow = rainbow;
        this.color = color;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", 8)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
    }

    public @Nullable Text getCustomName() {
        return customName;
    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    public boolean isRainbow() {
        return this.rainbow;
    }

    public DyeColor getColor() {
        return this.color;
    }
}

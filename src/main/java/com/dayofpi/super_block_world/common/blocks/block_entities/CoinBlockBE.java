package com.dayofpi.super_block_world.common.blocks.block_entities;

import com.dayofpi.super_block_world.registry.main.BlockEntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.common.util.block.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CoinBlockBE extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, new ItemStack(ItemInit.COIN));

    public CoinBlockBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.COIN_BLOCK, pos, state);
    }

    // Serialize the BlockEntity
    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, items);
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, items);}


    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
}

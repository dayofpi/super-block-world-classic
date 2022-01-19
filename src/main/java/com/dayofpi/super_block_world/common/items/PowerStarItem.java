package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class PowerStarItem extends BlockItem {
    public PowerStarItem(Settings settings) {
        super(BlockInit.POWER_STAR, settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}

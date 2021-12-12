package com.dayofpi.sbw_main.common.item.types;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PowerStarItem extends Item {
    public PowerStarItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}

package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum LilOinkVariant {
    WHITE("white", ModItems.SUPER_MUSHROOM), BLACK("black", ModItems.POISON_MUSHROOM), FLORAL("floral", ModItems.SHROOM_SHAKE), PINK("pink", ModItems.FIRE_FLOWER), TIGER("tiger", ModItems.SUPER_HEART), MYSTERY("mystery", Items.ENCHANTED_BOOK), FUNGAL("fungal", ModItems.GOLDEN_MUSHROOM), STARRY("starry", ModItems.SUPER_STAR), SILVER("silver", Items.IRON_BLOCK), GOLD("gold", Items.GOLDEN_APPLE);

    private final String name;
    private final Item item;

    LilOinkVariant(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public static LilOinkVariant fromName(String name) {
        for (LilOinkVariant type : LilOinkVariant.values()) {
            if (!type.name.equals(name)) continue;
            return type;
        }
        return WHITE;
    }

    public String getName() {
        return name;
    }

    public Item getItem() {
        return item;
    }
}

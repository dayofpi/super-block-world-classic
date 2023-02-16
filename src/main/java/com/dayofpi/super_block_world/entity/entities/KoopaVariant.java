package com.dayofpi.super_block_world.entity.entities;

import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public enum KoopaVariant implements StringIdentifiable {
    GREEN(0, "green", ModItems.GREEN_SHELL),
    RED(1, "red", ModItems.RED_SHELL),
    BLUE(2, "blue", ModItems.BLUE_SHELL),
    GOLD(3, "gold", ModItems.GOLD_SHELL);

    private final int id;
    private final String name;
    private final Item shell;

    KoopaVariant(int id, String name, Item shell) {
        this.id = id;
        this.name = name;
        this.shell = shell;
    }

    public static Item getShell(int id) {
        return fromId(id).shell;
    }

    public static KoopaVariant fromId(int id) {
        for (KoopaVariant type : KoopaVariant.values()) {
            if (type.id != id) continue;
            return type;
        }
        return GREEN;
    }

    public static String getName(int id) {
        return fromId(id).asString();
    }

    @Override
    public String asString() {
        return this.name;
    }
}

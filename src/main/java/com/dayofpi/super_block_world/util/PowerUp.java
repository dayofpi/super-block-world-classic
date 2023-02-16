package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

public enum PowerUp implements StringIdentifiable {
    NONE("none", null),
    TANOOKI("tanooki", new Identifier(Main.MOD_ID, "textures/entity/power_up/tanooki.png")),
    CAT("cat", new Identifier(Main.MOD_ID, "textures/entity/power_up/cat.png")),
    BEE("bee", new Identifier(Main.MOD_ID, "textures/entity/power_up/bee.png"));


    private final String name;
    private final Identifier identifier;

    PowerUp(String name, Identifier identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static PowerUp fromName(String name) {
        for (PowerUp type : PowerUp.values()) {
            if (!type.name.equals(name)) continue;
            return type;
        }
        return NONE;
    }

    public static boolean hasPowerUp(LivingEntity player, PowerUp powerUp) {
        if (!player.isPlayer())
            return false;
        return player.getDataTracker().get(FormManager.POWER_UP).equals(powerUp.asString());
    }

    public static boolean hasPowerUp(LivingEntity player) {
        if (!player.isPlayer())
            return false;
        return !player.getDataTracker().get(FormManager.POWER_UP).equals(NONE.asString());
    }

    public static ItemStack getItemStack(String name) {
        return switch (fromName(name)) {
            case NONE -> ItemStack.EMPTY;
            case TANOOKI -> new ItemStack(ModItems.SUPER_LEAF);
            case CAT -> new ItemStack(ModItems.SUPER_BELL);
            case BEE -> new ItemStack(ModItems.BEE_MUSHROOM);
        };
    }

    public static Identifier getTexture(String name) {
        return fromName(name).identifier;
    }
}

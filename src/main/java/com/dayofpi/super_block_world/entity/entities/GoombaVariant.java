package com.dayofpi.super_block_world.entity.entities;

import net.minecraft.util.StringIdentifiable;

public enum GoombaVariant implements StringIdentifiable {
    GOOMBA("goomba"),
    GLOOMBA("gloomba"),
    DARK("dark_goomba"),
    GOLD("gold_goomba");

    private final String name;

    GoombaVariant(String name) {
        this.name = name;
    }

    public static GoombaVariant fromName(String name) {
        for (GoombaVariant type : GoombaVariant.values()) {
            if (!type.name.equals(name)) continue;
            return type;
        }
        return GOOMBA;
    }

    @Override
    public String asString() {
        return this.name;
    }
}

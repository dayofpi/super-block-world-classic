package com.dayofpi.super_block_world.util;

import net.minecraft.util.StringIdentifiable;

public enum HorsetailPart implements StringIdentifiable {
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;

    HorsetailPart(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}

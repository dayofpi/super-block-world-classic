package com.dayofpi.super_block_world.common.util.mixin_aid;

import net.minecraft.entity.vehicle.BoatEntity;

public class ModBoatType {
    static {
        //noinspection ResultOfMethodCallIgnored
        BoatEntity.Type.values();
    }

    public static BoatEntity.Type AMANITA;
    public static BoatEntity.Type DARK_AMANITA;
    public static BoatEntity.Type BELL;
}

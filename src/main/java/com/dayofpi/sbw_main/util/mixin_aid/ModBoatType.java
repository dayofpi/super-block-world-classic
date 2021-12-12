package com.dayofpi.sbw_main.util.mixin_aid;

import net.minecraft.entity.vehicle.BoatEntity;

public class ModBoatType {
    static {
        BoatEntity.Type.values();
    }

    public static BoatEntity.Type AMANITA;
    public static BoatEntity.Type DARK_AMANITA;
}

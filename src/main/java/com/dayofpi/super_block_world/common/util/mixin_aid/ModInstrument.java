package com.dayofpi.super_block_world.common.util.mixin_aid;

import net.minecraft.block.enums.Instrument;

public class ModInstrument {
    static {
        //noinspection ResultOfMethodCallIgnored
        Instrument.values(); // Ensure class is loaded before the variant is accessed
    }

    public static Instrument BLING;
}

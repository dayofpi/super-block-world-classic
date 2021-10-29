package com.dayofpi.sbw_main.misc;

import net.minecraft.block.enums.Instrument;

public class EnumInstruments {
    static {
        Instrument.values(); // Ensure class is loaded before the variant is accessed
    }

    public static Instrument BLING;
}

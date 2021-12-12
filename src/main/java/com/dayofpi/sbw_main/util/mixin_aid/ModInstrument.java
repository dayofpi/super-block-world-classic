package com.dayofpi.sbw_main.util.mixin_aid;

import net.minecraft.block.enums.Instrument;

public class ModInstrument {
    static {
        Instrument.values(); // Ensure class is loaded before the variant is accessed
    }

    public static Instrument BLING;
}

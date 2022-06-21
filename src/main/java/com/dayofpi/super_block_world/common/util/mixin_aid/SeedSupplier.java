package com.dayofpi.super_block_world.common.util.mixin_aid;

public class SeedSupplier {
    public static long MARKER = 0L;

    public static long getSeed() {
        return MARKER;
    }

    public static void setSeed(long seed) {
        MARKER = seed;
    }
}

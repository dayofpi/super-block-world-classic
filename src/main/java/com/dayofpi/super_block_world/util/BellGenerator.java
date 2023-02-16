package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.world.ConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class BellGenerator extends SaplingGenerator {
    public BellGenerator() {
    }

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(ConfiguredFeatures.BELL);
    }
}

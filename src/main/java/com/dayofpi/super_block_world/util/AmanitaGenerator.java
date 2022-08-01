package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.registry.ConfiguredFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class AmanitaGenerator extends LargeTreeSaplingGenerator {
    public AmanitaGenerator() {
    }

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return RegistryEntry.of(ConfiguredFeatures.AMANITA_LARGE);
    }

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(ConfiguredFeatures.AMANITA);
    }
}

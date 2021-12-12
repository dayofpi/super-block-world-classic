/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.sbw_main.world.feature;

import com.dayofpi.sbw_main.world.feature.registry.configured_feature.ConfiguredTrees;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DarkAmanitaSaplingGenerator
extends LargeTreeSaplingGenerator {
    @Override
    @Nullable
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return null;
    }

    @Override
    @Nullable
    protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
        return ConfiguredTrees.DARK_AMANITA;
    }
}


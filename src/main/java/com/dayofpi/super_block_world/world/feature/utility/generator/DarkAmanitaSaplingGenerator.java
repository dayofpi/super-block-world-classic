/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.world.feature.utility.generator;

import com.dayofpi.super_block_world.world.feature.configured.ConfiguredTrees;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DarkAmanitaSaplingGenerator
extends LargeTreeSaplingGenerator {
    @Override
    @Nullable
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return ConfiguredTrees.DARK_AMANITA_MOUNTAIN;
    }

    @Override
    @Nullable
    protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
        return ConfiguredTrees.DARK_AMANITA_SAPLING;
    }
}


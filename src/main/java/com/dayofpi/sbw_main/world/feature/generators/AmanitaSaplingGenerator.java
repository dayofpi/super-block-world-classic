package com.dayofpi.sbw_main.world.feature.generators;

import com.dayofpi.sbw_main.world.registry.ModFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AmanitaSaplingGenerator extends SaplingGenerator {
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return random.nextInt(6) == 2 ? ModFeatures.AMANITA_FRUITS : ModFeatures.AMANITA;
    }
}

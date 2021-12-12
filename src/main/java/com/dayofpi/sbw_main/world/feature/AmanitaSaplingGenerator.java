package com.dayofpi.sbw_main.world.feature;

import com.dayofpi.sbw_main.world.feature.registry.configured_feature.ConfiguredTrees;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AmanitaSaplingGenerator extends SaplingGenerator {
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return random.nextInt(6) == 2 ? ConfiguredTrees.AMANITA_FRUIT : ConfiguredTrees.AMANITA;
    }
}

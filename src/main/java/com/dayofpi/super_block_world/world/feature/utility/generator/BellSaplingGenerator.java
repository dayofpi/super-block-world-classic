package com.dayofpi.super_block_world.world.feature.utility.generator;

import com.dayofpi.super_block_world.world.feature.configured.ConfiguredTrees;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BellSaplingGenerator extends SaplingGenerator {
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return ConfiguredTrees.BELL_TREE;
    }
}
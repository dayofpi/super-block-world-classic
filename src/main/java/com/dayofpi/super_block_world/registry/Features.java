package com.dayofpi.super_block_world.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.features.BellTreeFeature;
import com.dayofpi.super_block_world.common.features.RandomPatchFeature;
import com.dayofpi.super_block_world.common.features.RandomPatchFeatureConfig;
import com.dayofpi.super_block_world.common.features.StrawberryCoralFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class Features {
    public static final Feature<DefaultFeatureConfig> BELL_TREE = new BellTreeFeature(DefaultFeatureConfig.CODEC);
    private static final Feature<DefaultFeatureConfig> STRAWBERRY_CORAL = new StrawberryCoralFeature(DefaultFeatureConfig.CODEC);
    private static final Feature<RandomPatchFeatureConfig> RANDOM_PATCH = new RandomPatchFeature(RandomPatchFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "bell_tree"), BELL_TREE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "strawberry_coral"), STRAWBERRY_CORAL);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "random_patch_feature"), RANDOM_PATCH);
    }
}
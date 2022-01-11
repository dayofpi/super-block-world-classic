package com.dayofpi.super_block_world.registry.world.feature;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.world.feature.config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.common.world.feature.config.GiantMushroomFeatureConfig;
import com.dayofpi.super_block_world.common.world.feature.type.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class FeatureInit {
    public static final Feature<GlowLichenFeatureConfig> AMANITA_CARPET = new AmanitaCarpetFeature(GlowLichenFeatureConfig.CODEC);
    public static final Feature<GiantMushroomFeatureConfig> GIANT_MUSHROOM = new GiantMushroomFeature(GiantMushroomFeatureConfig.CODEC);
    public static final Feature<SimpleBlockFeatureConfig> SINGLE_BLOCK = new SingleBlockFeature(SimpleBlockFeatureConfig.CODEC);
    public static final Feature<ExtraRandomPatchFeatureConfig> EXTRA_RANDOM_PATCH = new ExtraRandomPatchFeature(ExtraRandomPatchFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> STRAWBERRY_CORAL = new StrawberryCoralFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> JELLYBEAM = new JellybeamFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> PIPE = new PipeFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> UNDERWATER_PIPE = new UnderwaterPipeFeature(DefaultFeatureConfig.CODEC);

    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "amanita_carpet"), AMANITA_CARPET);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "giant_mushroom"), GIANT_MUSHROOM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "single_block"), SINGLE_BLOCK);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "extra_random_patch"), EXTRA_RANDOM_PATCH);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "strawberry_coral"), STRAWBERRY_CORAL);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "pipe"), PIPE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "underwater_pipe"), UNDERWATER_PIPE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "jellybeam"), JELLYBEAM);
    }
}

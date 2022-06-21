package com.dayofpi.super_block_world.world.feature;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.world.feature.utility.feature_config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.world.feature.utility.feature_config.GiantMushroomFeatureConfig;
import com.dayofpi.super_block_world.world.feature.types.*;
import com.dayofpi.super_block_world.world.feature.utility.feature_config.PipeFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class FeatureInit {
    public static final Feature<GlowLichenFeatureConfig> AMANITA_CARPET = new AmanitaCarpetFeature(GlowLichenFeatureConfig.CODEC);
    public static final Feature<GiantMushroomFeatureConfig> GIANT_MUSHROOM = new GiantMushroomFeature(GiantMushroomFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BELL_TREE = new BellTreeFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<SimpleBlockFeatureConfig> SINGLE_BLOCK = new SingleBlockFeature(SimpleBlockFeatureConfig.CODEC);
    public static final Feature<ExtraRandomPatchFeatureConfig> EXTRA_RANDOM_PATCH = new ExtraRandomPatchFeature(ExtraRandomPatchFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> STRAWBERRY_CORAL = new StrawberryCoralFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> JELLYBEAM = new JellybeamFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<PipeFeatureConfig> PIPE = new PipeFeature(PipeFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> UNDERWATER_PIPE = new UnderwaterPipeFeature(DefaultFeatureConfig.CODEC);

    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "amanita_carpet"), AMANITA_CARPET);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "giant_mushroom"), GIANT_MUSHROOM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "bell_tree"), BELL_TREE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "single_block"), SINGLE_BLOCK);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "extra_random_patch"), EXTRA_RANDOM_PATCH);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "strawberry_coral"), STRAWBERRY_CORAL);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "pipe"), PIPE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "underwater_pipe"), UNDERWATER_PIPE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "jellybeam"), JELLYBEAM);
    }
}

package com.dayofpi.super_block_world.main.registry.world.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.world.feature.config.BlockLineFeatureConfig;
import com.dayofpi.super_block_world.main.common.world.feature.config.GiantMushroomFeatureConfig;
import com.dayofpi.super_block_world.main.common.world.feature.type.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class FeatureRegistry {
    public static final Feature<GlowLichenFeatureConfig> AMANITA_CARPET = new AmanitaCarpetFeature(GlowLichenFeatureConfig.CODEC);
    public static final Feature<GiantMushroomFeatureConfig> GIANT_MUSHROOM = new GiantMushroomFeature(GiantMushroomFeatureConfig.CODEC);
    public static final Feature<SimpleBlockFeatureConfig> SINGLE_BLOCK = new BlockSingleFeature(SimpleBlockFeatureConfig.CODEC);
    public static final Feature<BlockLineFeatureConfig> BLOCK_LINE = new BlockLineFeature(BlockLineFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> STRAWBERRY_CORAL = new StrawberryCoralFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> JELLYBEAM = new JellybeamFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WARP_PIPE_WATER = new UnderwaterPipeFeature(DefaultFeatureConfig.CODEC);

    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "amanita_carpet"), AMANITA_CARPET);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "giant_mushroom"), GIANT_MUSHROOM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "single_block"), SINGLE_BLOCK);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "block_line"), BLOCK_LINE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "strawberry_coral"), STRAWBERRY_CORAL);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "underwater_pipe"), WARP_PIPE_WATER);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "jellybeam"), JELLYBEAM);
    }
}

package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.world.feature_config.BlockLineFeatureConfig;
import com.dayofpi.sbw_main.world.feature_config.GiantMushroomFeatureConfig;
import com.dayofpi.sbw_main.world.feature_type.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ModFeature {
    public static final Feature<GiantMushroomFeatureConfig> HUGE_MUSHROOM = new GiantMushroomFeature(GiantMushroomFeatureConfig.CODEC);
    public static final Feature<BlockLineFeatureConfig> BLOCK_LINE = new BlockLineFeature(BlockLineFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> COLUMN_CORAL = new StrawberryCoralFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> JELLYBEAM = new JellybeamFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WARP_PIPE_WATER = new UnderwaterPipeFeature(DefaultFeatureConfig.CODEC);

    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "huge_mushroom"), HUGE_MUSHROOM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "block_line"), BLOCK_LINE);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "column_coral"), COLUMN_CORAL);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "jellybeam"), JELLYBEAM);
        Registry.register(Registry.FEATURE, new Identifier(Main.MOD_ID, "underwater_pipe"), WARP_PIPE_WATER);
    }
}

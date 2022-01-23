package com.dayofpi.super_block_world.world.feature.configured;

import com.dayofpi.super_block_world.world.feature.utility.feature_config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.world.feature.FeatureInit;
import com.dayofpi.super_block_world.world.feature.placed.PlacedPipes;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredPipes {
    public static final ConfiguredFeature<?, ?> GREEN_PIPE_PATCH = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(80, 1, 1, 50, () -> PlacedPipes.GREEN_PIPE));
    public static final ConfiguredFeature<?, ?> RED_PIPE_PATCH = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(80, 1, 1, 50, () -> PlacedPipes.RED_PIPE));
}

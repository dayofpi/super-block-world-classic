package com.dayofpi.super_block_world.registry.world.feature.configured;

import com.dayofpi.super_block_world.common.world.feature.config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.registry.world.feature.FeatureInit;
import com.dayofpi.super_block_world.registry.world.feature.placed.PlacedPipes;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredPipes {
    public static final ConfiguredFeature<?, ?> PIPE_PATCH = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(64, 1, 1,50, () -> PlacedPipes.PIPE));
}

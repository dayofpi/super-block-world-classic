package com.dayofpi.super_block_world.main.registry.world.feature.configured;

import com.dayofpi.super_block_world.main.common.world.feature.config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedPipe;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredPipes {
    public static final ConfiguredFeature<?, ?> PIPE_PATCH = FeatureRegistry.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(42, 1, 1,42, () -> PlacedPipe.PIPE));
}

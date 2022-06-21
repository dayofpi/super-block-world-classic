package com.dayofpi.super_block_world.world.feature.utility.feature_config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class PipeFeatureConfig implements FeatureConfig {
    public static final Codec<PipeFeatureConfig> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter((config) -> config.pipeProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((config) -> config.bodyProvider)).apply(instance, (PipeFeatureConfig::new)));
    public final BlockStateProvider pipeProvider;
    public final BlockStateProvider bodyProvider;

    public PipeFeatureConfig(BlockStateProvider state, BlockStateProvider state2) {
        this.pipeProvider = state;
        this.bodyProvider = state2;
    }
}
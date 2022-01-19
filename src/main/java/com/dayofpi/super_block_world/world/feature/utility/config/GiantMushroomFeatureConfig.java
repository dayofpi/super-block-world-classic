package com.dayofpi.super_block_world.world.feature.utility.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class GiantMushroomFeatureConfig implements FeatureConfig {
    public static final Codec<GiantMushroomFeatureConfig> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter((config) -> config.capProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((config) -> config.stemProvider),
                    IntProvider.VALUE_CODEC.fieldOf("stem_height").forGetter((config) -> config.stemHeight),
                    IntProvider.VALUE_CODEC.fieldOf("cap_radius").forGetter((config) -> config.capRadius),
                            Codec.BOOL.fieldOf("is_flat").forGetter((config -> config.isFlat)))
                            .apply(instance, (GiantMushroomFeatureConfig::new)));
    public final BlockStateProvider capProvider;
    public final BlockStateProvider stemProvider;
    public final IntProvider stemHeight;
    public final IntProvider capRadius;
    public final boolean isFlat;

    public GiantMushroomFeatureConfig(BlockStateProvider state, BlockStateProvider state2, IntProvider stemHeight, IntProvider capRadius, boolean flat) {
        this.capProvider = state;
        this.stemProvider = state2;
        this.stemHeight = stemHeight;
        this.capRadius = capRadius;
        this.isFlat = flat;
    }
}
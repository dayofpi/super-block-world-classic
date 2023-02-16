/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.world.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public record RandomPatchFeatureConfig(int tries, int xSpread, int zSpread, int ySpread,
                                       RegistryEntry<PlacedFeature> feature) implements FeatureConfig {
    public static final Codec<RandomPatchFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(RandomPatchFeatureConfig::tries),
                    Codecs.NONNEGATIVE_INT.fieldOf("x_spread").orElse(7).forGetter(RandomPatchFeatureConfig::xSpread),
                    Codecs.NONNEGATIVE_INT.fieldOf("z_spread").orElse(7).forGetter(RandomPatchFeatureConfig::zSpread),
                    Codecs.NONNEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(RandomPatchFeatureConfig::ySpread),
                    PlacedFeature.REGISTRY_CODEC.fieldOf("feature").forGetter(RandomPatchFeatureConfig::feature))
            .apply(instance, RandomPatchFeatureConfig::new));

    public int tries() {
        return this.tries;
    }

    public int xSpread() {
        return this.xSpread;
    }

    public int zSpread() {
        return this.zSpread;
    }

    public int ySpread() {
        return this.ySpread;
    }

    public RegistryEntry<PlacedFeature> feature() {
        return this.feature;
    }
}


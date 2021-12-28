/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.main.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Supplier;

public record ExtraRandomPatchFeatureConfig(int tries, int xSpread, int zSpread, int ySpread, Supplier<PlacedFeature> feature) implements FeatureConfig {
    public static final Codec<ExtraRandomPatchFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(ExtraRandomPatchFeatureConfig::tries),
            Codecs.NONNEGATIVE_INT.fieldOf("x_spread").orElse(7).forGetter(ExtraRandomPatchFeatureConfig::xSpread),
            Codecs.NONNEGATIVE_INT.fieldOf("z_spread").orElse(7).forGetter(ExtraRandomPatchFeatureConfig::zSpread),
            Codecs.NONNEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(ExtraRandomPatchFeatureConfig::ySpread),
            PlacedFeature.REGISTRY_CODEC.fieldOf("feature").forGetter(ExtraRandomPatchFeatureConfig::feature))
            .apply(instance, ExtraRandomPatchFeatureConfig::new));

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

    public Supplier<PlacedFeature> feature() {
        return this.feature;
    }
}


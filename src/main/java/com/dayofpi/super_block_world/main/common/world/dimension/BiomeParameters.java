package com.dayofpi.super_block_world.main.common.world.dimension;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.util.ModBiomeKeys;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

public class BiomeParameters {
    public static final MultiNoiseBiomeSource.Preset MUSHROOM_KINGDOM =
            new MultiNoiseBiomeSource.Preset(new Identifier(Main.MOD_ID,"mushroom_kingdom"), registry -> new MultiNoiseUtil.Entries<>(ImmutableList.of(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.2f,
                            0.4f,
                            0.0f,
                            -0.2f,
                            0.0f,
                            0.0f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.MUSHROOM_GRASSLANDS)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.3f,
                            0.2f,
                            0.0f,
                            0.2f,
                            0.0f,
                            0.0f,
                            0.4f), () -> registry.getOrThrow(ModBiomeKeys.MOO_MOO_MEADOW)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.3f,
                            0.4f,
                            0.0f,
                            -0.1f,
                            0.0f,
                            0.0f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.AMANITA_FOREST)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.2f,
                            0.4f,
                            0.6f,
                            0.0f, 0.0f,
                            0.0f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.FOREST_OF_ILLUSION)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.4f,
                            0.0f,
                            0.4f,
                            -0.25f,
                            0.0f,
                            0.2f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.DRY_DRY_DESERT)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.3f,
                            0.5f,
                            -0.2f,
                            0.2f,
                            0.0f,
                            0.00f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.CHEEP_CHEEP_REEF)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.25f,
                            0.1f,
                            -0.1f,
                            -0.25f,
                            0.0f,
                            0.0f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.AUTUMN_FOREST)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.15f,
                            0.3f,
                            0f,
                            -0.5f,
                            0.0f,
                            0.45f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.MUSHROOM_GORGE)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            0.3f,
                            0.5f,
                            -0.2f,
                            -0.25f,
                            0.0f,
                            0.5f,
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.FOSSIL_FALLS))
            )));

}

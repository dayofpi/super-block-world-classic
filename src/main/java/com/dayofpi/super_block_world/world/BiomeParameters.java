package com.dayofpi.super_block_world.world;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.util.key.ModBiomeKeys;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

public class BiomeParameters {
    public static final MultiNoiseBiomeSource.Preset MUSHROOM_KINGDOM =
            new MultiNoiseBiomeSource.Preset(new Identifier(Main.MOD_ID,"mushroom_kingdom"), registry -> new MultiNoiseUtil.Entries<>(ImmutableList.of(Pair.of(
                    MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0f, 0.1f),
                            MultiNoiseUtil.ParameterRange.of(0.1f),
                            MultiNoiseUtil.ParameterRange.of(0.02f, 0.2f),
                            MultiNoiseUtil.ParameterRange.of(-0.2f, -0.12f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.MUSHROOM_GRASSLANDS)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(-0.2f, -0.1f),
                            MultiNoiseUtil.ParameterRange.of(0.11f),
                            MultiNoiseUtil.ParameterRange.of(0.02f, 0.2f),
                            MultiNoiseUtil.ParameterRange.of(-0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.SHERBET_LAND)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.3f),
                            MultiNoiseUtil.ParameterRange.of(0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.1f, 0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.1f), () -> registry.getOrThrow(ModBiomeKeys.MOO_MOO_MEADOW)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(-0.3f, -0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.3f),
                            MultiNoiseUtil.ParameterRange.of(0.02f, 0.3f),
                            MultiNoiseUtil.ParameterRange.of(-0.05f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.AMANITA_FOREST)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(-0.2f, -0.1f),
                            MultiNoiseUtil.ParameterRange.of(0.3f),
                            MultiNoiseUtil.ParameterRange.of(0.3f, 0.5f),
                            MultiNoiseUtil.ParameterRange.of(-0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.FOREST_OF_ILLUSION)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.4f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.2f, 0.3f),
                            MultiNoiseUtil.ParameterRange.of(-0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.DRY_DRY_DESERT)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.25f),
                            MultiNoiseUtil.ParameterRange.of(0.4f),
                            MultiNoiseUtil.ParameterRange.of(-0.5f, -0.1f),
                            MultiNoiseUtil.ParameterRange.of(0.0f, 0.4f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.CHEEP_CHEEP_REEF)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(-0.5f, 0.5f),
                            MultiNoiseUtil.ParameterRange.of(0.35f),
                            MultiNoiseUtil.ParameterRange.of(-0.5f, 0.5f),
                            MultiNoiseUtil.ParameterRange.of(0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.2f),
                            0.25f), () -> registry.getOrThrow(ModBiomeKeys.CHEEP_CHEEP_REEF)),


                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.02f),
                            MultiNoiseUtil.ParameterRange.of(0.15f),
                            MultiNoiseUtil.ParameterRange.of(-0.3f, -0.1f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.AUTUMN_FOREST)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.02f),
                            MultiNoiseUtil.ParameterRange.of(0.15f),
                            MultiNoiseUtil.ParameterRange.of(-0.6f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.AUTUMN_MOUNTAIN)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.25f),
                            MultiNoiseUtil.ParameterRange.of(0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.5f),
                            MultiNoiseUtil.ParameterRange.of(-0.4f, -0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.3f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.MUSHROOM_GORGE)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.3f),
                            MultiNoiseUtil.ParameterRange.of(0.2f),
                            MultiNoiseUtil.ParameterRange.of(0.5f, 0.8f),
                            MultiNoiseUtil.ParameterRange.of(-0.9f, -0.7f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.5f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.TALL_TALL_MOUNTAIN)),

                    Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.of(0.25f),
                            MultiNoiseUtil.ParameterRange.of(0.2f),
                            MultiNoiseUtil.ParameterRange.of(-0.2f),
                            MultiNoiseUtil.ParameterRange.of(-0.5f, -0.3f),
                            MultiNoiseUtil.ParameterRange.of(0.0f),
                            MultiNoiseUtil.ParameterRange.of(0.4f),
                            0.0f), () -> registry.getOrThrow(ModBiomeKeys.FOSSIL_FALLS))
            )));

}

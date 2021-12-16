package com.dayofpi.super_block_world.main.registry.world.biome;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public class BiomeBuilder {
    public static final int DEFAULT_GRASS_COLOR = 6879535;
    public static final int DEFAULT_FOLIAGE_COLOR = 6408218;

    private static final int DEFAULT_SKY_COLOR = 5156850;
    private static final int DEFAULT_FOG_COLOR = 11337727;
    private static final int DARK_GRASS_COLOR = 4499737;
    private static final int REEF_GRASS_COLOR = 12121385;
    private static final int DEFAULT_WATER_COLOR = 2868223;

    protected static Biome createMushroomGrasslands() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        BiomeTemplates.addCaveMobs(spawnSettings);
        BiomeTemplates.addMushroomGrasslandMobs(spawnSettings);

        BiomeTemplates.addBasicFeatures(generationSettings);
        BiomeTemplates.addMushroomGrasslandsFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                        .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                        .grassColor(DEFAULT_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                        .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                        .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createAmanitaForest() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        BiomeTemplates.addCaveMobs(spawnSettings);
        BiomeTemplates.addAmanitaForestMobs(spawnSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(DARK_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();

    }

    protected static Biome createMushroomGorge() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        BiomeTemplates.addCaveMobs(spawnSettings);
        BiomeTemplates.addMushroomGorgeMobs(spawnSettings);

        BiomeTemplates.addBasicFeatures(generationSettings);
        BiomeTemplates.addMushroomGorgeFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(DARK_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createDryDryDesert() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        BiomeTemplates.addCaveMobs(spawnSettings);

        BiomeTemplates.addDryDryDesertFeatures(generationSettings);
        BiomeTemplates.addBasicFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(REEF_GRASS_COLOR).foliageColor(REEF_GRASS_COLOR)
                .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.DESERT).precipitation(Biome.Precipitation.NONE).temperature(0.9f).downfall(0.4f).build();

    }
}

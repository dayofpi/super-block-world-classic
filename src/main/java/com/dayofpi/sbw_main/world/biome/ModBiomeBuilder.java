package com.dayofpi.sbw_main.world.biome;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public class ModBiomeBuilder {
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

        ModBiomeTemplate.addCaveMobs(spawnSettings);
        ModBiomeTemplate.addMushroomGrasslandMobs(spawnSettings);

        ModBiomeTemplate.addBasicFeatures(generationSettings);
        ModBiomeTemplate.addMushroomGrasslandsFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                        .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                        .grassColor(DEFAULT_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                        .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                        .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createAmanitaForest() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        ModBiomeTemplate.addCaveMobs(spawnSettings);
        ModBiomeTemplate.addAmanitaForestMobs(spawnSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(DARK_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();

    }

    protected static Biome createMushroomGorge() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        ModBiomeTemplate.addCaveMobs(spawnSettings);
        ModBiomeTemplate.addMushroomGorgeMobs(spawnSettings);

        ModBiomeTemplate.addBasicFeatures(generationSettings);
        ModBiomeTemplate.addMushroomGorgeFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(DARK_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createDryDryDesert() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        ModBiomeTemplate.addCaveMobs(spawnSettings);

        ModBiomeTemplate.addDryDryDesertFeatures(generationSettings);
        ModBiomeTemplate.addBasicFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(REEF_GRASS_COLOR).foliageColor(REEF_GRASS_COLOR)
                .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.DESERT).precipitation(Biome.Precipitation.NONE).temperature(0.9f).downfall(0.4f).build();

    }
}

package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.client.sound.ModMusic;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public class BiomeBuilder {
    public static final int DEFAULT_GRASS_COLOR = 6879535;
    public static final int DEFAULT_FOLIAGE_COLOR = 6408218;

    private static final int DEFAULT_SKY = 5156850;
    private static final int DEFAULT_FOG_COLOR = 11337727;
    private static final int DEFAULT_WATER = 2868223;

    private static final int FOREST_FOG = 11387566;
    private static final int FOREST_GRASS = 6263838;

    private static final int AUTUMN_SKY = 13432786;
    private static final int AUTUMN_FOG = 12637889;
    private static final int AUTUMN_WATER = 59345;
    private static final int AUTUMN_GRASS = 16758835;

    private static final int DARK_GRASS = 4499737;
    private static final int REEF_GRASS = 12121385;
    private static final int REEF_FOLIAGE = 443968;


    protected static Biome createMushroomGrasslands() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addMushroomGrasslandMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addMushroomGrasslandsFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(DEFAULT_SKY).fogColor(DEFAULT_FOG_COLOR).grassColor(DEFAULT_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR).waterColor(DEFAULT_WATER).waterFogColor(DEFAULT_WATER).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createAmanitaForest() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addAmanitaForestMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addAmanitaForestFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(DEFAULT_SKY).fogColor(FOREST_FOG)
                .grassColor(FOREST_GRASS).foliageColor(FOREST_GRASS)
                .waterColor(DEFAULT_WATER).waterFogColor(DEFAULT_WATER).moodSound(BiomeMoodSound.CAVE).music(ModMusic.FOREST).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();

    }

    protected static Biome createAutumnForest() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addAutumnForestMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addAutumnForestFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(AUTUMN_SKY).fogColor(AUTUMN_FOG)
                .grassColor(AUTUMN_GRASS).foliageColor(AUTUMN_GRASS)
                .waterColor(AUTUMN_WATER).waterFogColor(AUTUMN_SKY).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createCheepCheepReef() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addCheepCheepReefMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addCheepCheepReefFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(DEFAULT_SKY).fogColor(AUTUMN_FOG)
                .grassColor(REEF_GRASS).foliageColor(REEF_FOLIAGE)
                .waterColor(AUTUMN_WATER).waterFogColor(AUTUMN_WATER).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createMushroomGorge() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addMushroomGorgeMobs(spawnSettings);

        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addMushroomGorgeFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(DARK_GRASS).foliageColor(DEFAULT_FOLIAGE_COLOR)
                .waterColor(DEFAULT_WATER).waterFogColor(DEFAULT_WATER)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }
    protected static Biome createDryDryDesert() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        DefaultTemplates.addCaveMobs(spawnSettings);

        GenerationTemplates.addDryDryDesertFeatures(generationSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder()
                .skyColor(DEFAULT_SKY).fogColor(DEFAULT_FOG_COLOR)
                .grassColor(REEF_GRASS).foliageColor(AUTUMN_GRASS)
                .waterColor(DEFAULT_WATER).waterFogColor(DEFAULT_WATER)
                .moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.DESERT).precipitation(Biome.Precipitation.NONE).temperature(0.9f).downfall(0.4f).build();

    }
}

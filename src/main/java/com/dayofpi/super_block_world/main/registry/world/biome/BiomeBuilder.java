package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.client.sound.ModMusic;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class BiomeBuilder {
    protected static Biome createMushroomGrasslands() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addMushroomGrasslandMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addMushroomGrasslandsFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.DEFAULT_FOG).grassColor(BiomeColors.DEFAULT_GRASS).foliageColor(BiomeColors.DEFAULT_FOLIAGE).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createMooMooMeadow() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addMooMooMeadowMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addMooMooMeadowFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.AUTUMN_SKY).fogColor(BiomeColors.AUTUMN_FOG).grassColor(BiomeColors.MEADOW_GRASS).foliageColor(BiomeColors.MEADOW_FOLIAGE).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.9f).downfall(0.4f).build();
    }

    protected static Biome createAmanitaForest() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addAmanitaForestMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addAmanitaForestFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.FOREST_FOG).grassColor(BiomeColors.FOREST_GRASS).foliageColor(BiomeColors.FOREST_GRASS).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).music(ModMusic.FOREST).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createForestOfIllusion() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addForestOfIllusionMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addForestOfIllusionFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.FOREST_FOG).grassColor(BiomeColors.FOREST_GRASS).foliageColor(BiomeColors.ILLUSION_FOLIAGE).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).music(ModMusic.ILLUSION).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createFossilFalls() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addFossilFallsFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.DEFAULT_FOG).grassColor(BiomeColors.FOSSIL_FALLS_GRASS).foliageColor(BiomeColors.FOSSIL_FALLS_GRASS).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createAutumnForest() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addAutumnForestMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addAutumnForestFeatures(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.AUTUMN_SKY).fogColor(BiomeColors.AUTUMN_FOG).grassColor(BiomeColors.AUTUMN_GRASS).foliageColor(BiomeColors.AUTUMN_GRASS).waterColor(BiomeColors.AUTUMN_WATER).waterFogColor(BiomeColors.AUTUMN_SKY).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }


    protected static Biome createCheepCheepReef() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addCheepCheepReefMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addCheepCheepReefFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.AUTUMN_FOG).grassColor(BiomeColors.REEF_GRASS).foliageColor(BiomeColors.REEF_FOLIAGE).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).music(ModMusic.WATER).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }

    protected static Biome createMushroomGorge() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addMushroomGorgeMobs(spawnSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);
        GenerationTemplates.addMushroomGorgeFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.DEFAULT_FOG).grassColor(BiomeColors.GORGE_GRASS).foliageColor(BiomeColors.DEFAULT_FOLIAGE).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.NONE).precipitation(Biome.Precipitation.RAIN).temperature(0.7f).downfall(0.4f).build();
    }
    protected static Biome createDryDryDesert() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addDryDryDesertMobs(spawnSettings);
        GenerationTemplates.addDryDryDesertFeatures(generationSettings);
        DefaultTemplates.addBasicFeatures(generationSettings);

        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.DEFAULT_SKY).fogColor(BiomeColors.DEFAULT_FOG).grassColor(BiomeColors.REEF_GRASS).foliageColor(BiomeColors.AUTUMN_GRASS).waterColor(BiomeColors.DEFAULT_WATER).waterFogColor(BiomeColors.DEFAULT_WATER_FOG).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.DESERT).precipitation(Biome.Precipitation.NONE).temperature(0.9f).downfall(0.4f).build();
    }

    protected static Biome createSherbetLand() {
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultTemplates.addCaveMobs(spawnSettings);
        SpawnTemplates.addSherbetLandMobs(spawnSettings);
        DefaultTemplates.addIcyFeatures(generationSettings);
        GenerationTemplates.addSherbetLandFeatures(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
        return new Biome.Builder().effects(new BiomeEffects.Builder().skyColor(BiomeColors.AUTUMN_SKY).fogColor(BiomeColors.FOREST_FOG).grassColor(BiomeColors.SHERBET_GRASS).foliageColor(BiomeColors.SHERBET_FOLIAGE).waterColor(BiomeColors.SHERBET_WATER).waterFogColor(BiomeColors.AUTUMN_FOG).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).category(Biome.Category.ICY).precipitation(Biome.Precipitation.SNOW).temperature(-0.3f).downfall(0.9f).build();
    }
}
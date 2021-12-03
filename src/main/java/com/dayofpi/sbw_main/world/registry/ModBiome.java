package com.dayofpi.sbw_main.world.registry;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public class ModBiome {
    private static final Biome MUSHROOM_GRASSLANDS = createMushroomGrasslands();
    private static final int DEFAULT_SKY_COLOR = 5156850, DEFAULT_FOG_COLOR = 11337727;
    private static final int DEFAULT_GRASS_COLOR = 6879535, DEFAULT_FOLIAGE_COLOR = 891656;
    private static final int DEFAULT_WATER_COLOR = 47847;

    private static Biome createMushroomGrasslands() {
        GenerationSettings.Builder gen = new GenerationSettings.Builder();
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();

        ModBiomeBuilder.addLandCarvers(gen);

        ModBiomeBuilder.addMushroomGrasslandMobs(spawn);
        ModBiomeBuilder.addCaveMobs(spawn);

        return new Biome.Builder()
                .effects(new BiomeEffects.Builder()
                        .skyColor(DEFAULT_SKY_COLOR).fogColor(DEFAULT_FOG_COLOR)
                        .grassColor(DEFAULT_GRASS_COLOR).foliageColor(DEFAULT_FOLIAGE_COLOR)
                        .waterColor(DEFAULT_WATER_COLOR).waterFogColor(DEFAULT_WATER_COLOR)
                        .moodSound(BiomeMoodSound.CAVE).build())
                .spawnSettings(spawn.build())
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.7f).downfall(0.4f)
                .build();
    }
}

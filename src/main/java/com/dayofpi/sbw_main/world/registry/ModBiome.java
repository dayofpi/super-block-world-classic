package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.entity.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class ModBiome {
    private static final Biome MUSHROOM_GRASSLANDS = createMushroomGrasslands();
    private static final int DEFAULT_SKY_COLOR = 5156850, DEFAULT_FOG_COLOR = 11337727;
    private static final int DEFAULT_GRASS_COLOR = 6879535, DEFAULT_FOLIAGE_COLOR = 891656;
    private static final int DEFAULT_WATER_COLOR = 47847;

    private static void addCaveMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.BUZZY_BEETLE, 100, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.SPIKE_TOP, 50, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.BOB_OMB, 30, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.HAMMER_BRO, 25, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.THWOMP, 1, 1, 1));
    }

    private static void addMushroomGrasslandMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 90, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.MOO_MOO, 10, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
    }

    private static Biome createMushroomGrasslands() {
        GenerationSettings.Builder gen = new GenerationSettings.Builder();
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addLandCarvers(gen);

        addMushroomGrasslandMobs(spawn);
        addCaveMobs(spawn);

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

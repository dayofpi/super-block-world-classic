package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedBlocks;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedMisc;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedVegetation;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class GenerationTemplates {
    static void addMushroomGrasslandsFeatures(GenerationSettings.Builder builder) {
        builder.feature(10, () -> PlacedVegetation.HORSETAIL);
        builder.feature(10, () -> PlacedVegetation.STUMP);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_PLANTS);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_FLOWERS);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_MUSHROOMS);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_VEGETATION);
    }

    static void addAmanitaForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(1, () -> PlacedMisc.LAKE_POISON_FOREST);
        builder.feature(10, () -> VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(10, () -> PlacedVegetation.MUNCHER_MANY);
        builder.feature(10, () -> PlacedVegetation.STUMP);
        builder.feature(10, () -> PlacedVegetation.WATER_PLANTS);
        builder.feature(10, () -> PlacedVegetation.AMANITA_FOREST_MUSHROOMS);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_PLANTS);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_FLOWERS);
        builder.feature(10, () -> PlacedVegetation.AMANITA_FOREST_VEGETATION);
    }

    static void addAutumnForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(1, () -> PlacedMisc.LAKE_POISON_FOREST);
        builder.feature(10, () -> VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        builder.feature(10, () -> VegetationPlacedFeatures.PATCH_TALL_GRASS);
        builder.feature(10, () -> PlacedVegetation.STUMP);
        builder.feature(10, () -> PlacedVegetation.WATER_PLANTS);
        builder.feature(10, () -> PlacedVegetation.AUTUMN_FOREST_MUSHROOMS);
        builder.feature(10, () -> PlacedVegetation.AUTUMN_FOREST_PLANTS);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_FLOWERS);
        builder.feature(10, () -> PlacedVegetation.AUTUMN_FOREST_VEGETATION);

    }

    static void addMushroomGorgeFeatures(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.WIDE_CANYON);
        builder.feature(2, () -> PlacedMisc.AMETHYST_EXTRA);
        builder.feature(10, () -> PlacedVegetation.STUMP);
        builder.feature(10, () -> PlacedVegetation.GORGE_PLANTS);
        builder.feature(10, () -> PlacedVegetation.GORGE_FLOWERS);
        builder.feature(10, () -> PlacedVegetation.GORGE_VEGETATION);
        builder.feature(10, () -> PlacedVegetation.CANYON_VEGETATION);
    }

    static void addDryDryDesertFeatures(GenerationSettings.Builder builder) {
        builder.feature(6, () -> PlacedMisc.QUICKSAND);
        builder.feature(10, () -> PlacedVegetation.CACTUS);
        builder.feature(10, () -> PlacedVegetation.DESERT_PLANTS);
    }

    public static void addCheepCheepReefFeatures(GenerationSettings.Builder builder) {
        builder.feature(2, () -> PlacedBlocks.JELLYBEAM);
        builder.feature(10, () -> PlacedVegetation.HORSETAIL);
        builder.feature(10, () -> PlacedVegetation.WATER_PLANTS);
        builder.feature(10, () -> PlacedVegetation.CORAL_MANY);
        builder.feature(10, () -> PlacedVegetation.GRASSLAND_PLANTS);
        builder.feature(10, () -> PlacedVegetation.REEF_FLOWERS);
        builder.feature(10, () -> PlacedVegetation.REEF_VEGETATION);
    }
}

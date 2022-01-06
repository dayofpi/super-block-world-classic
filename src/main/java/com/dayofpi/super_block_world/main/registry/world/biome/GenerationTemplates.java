package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.world.feature.placed.*;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class GenerationTemplates {
    static void addMushroomGrasslandsFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.HORSETAIL);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_MUSHROOMS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.GRASSLAND_VEGETATION);
    }

    static void addSherbetLandFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST_EXTRA);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_MUSHROOMS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.SHERBET_LAND_VEGETATION);
    }

    static void addMooMooMeadowFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.HORSETAIL);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.MEADOW_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.MEADOW_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_MUSHROOMS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.MEADOW_VEGETATION);
    }

    static void addAmanitaForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_POISON_FOREST);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.MUNCHER_MANY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.PIRANHA_LILY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.WATER_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AMANITA_FOREST_MUSHROOMS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.AMANITA_FOREST_VEGETATION);
    }

    static void addForestOfIllusionFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_POISON_FOREST);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.FUZZBUSH);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.PIRANHA_LILY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.WATER_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AMANITA_FOREST_MUSHROOMS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.FOREST_OF_ILLUSION_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.FOREST_OF_ILLUSION_VEGETATION);
    }

    static void addAutumnForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_POISON_FOREST);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.WATER_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AUTUMN_FOREST_MUSHROOMS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AUTUMN_FOREST_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GRASSLAND_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.AUTUMN_FOREST_VEGETATION);
    }

    static void addMushroomGorgeFeatures(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.WIDE_CANYON);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST_EXTRA);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.FUZZBUSH);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GORGE_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.GORGE_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.GORGE_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.CANYON_VEGETATION);
    }

    static void addDryDryDesertFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedDecoration.QUICKSAND);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.CACTUS_PATCH);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.DESERT_PLANTS);
    }

    static void addFossilFallsFeatures(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.WIDE_CANYON);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_VANILLATE_COAL_EXTRA);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CHISELED_TOADSTONE);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.FOSSIL_FALLS_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.FOSSIL_FALLS_FLOWERS);
    }

    public static void addCheepCheepReefFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.OCEAN_FLOOR_PIPE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.JELLYBEAM);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.HORSETAIL);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.WATER_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.CORAL_MANY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.KELP_WARM);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.AUTUMN_FOREST_PLANTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.REEF_FLOWERS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.REEF_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.WARM_OCEAN_VEGETATION);
    }
}

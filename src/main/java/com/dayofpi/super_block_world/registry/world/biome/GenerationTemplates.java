package com.dayofpi.super_block_world.registry.world.biome;

import com.dayofpi.super_block_world.registry.world.feature.placed.*;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static net.minecraft.world.gen.GenerationStep.Feature.*;

public class GenerationTemplates {
    static void addMushroomGrasslandsFeatures(GenerationSettings.Builder builder) {
        builder.feature(VEGETAL_DECORATION, PlacedPlants.HORSETAIL);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_GRASSLAND);
    }

    static void addSherbetLandFeatures(GenerationSettings.Builder builder) {
        builder.feature(LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST_EXTRA);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_SHERBET);
    }

    static void addMooMooMeadowFeatures(GenerationSettings.Builder builder) {
        builder.feature(VEGETAL_DECORATION, PlacedPlants.HORSETAIL);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_MEADOW);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_MEADOW);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_MEADOW);
    }

    static void addAmanitaForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(LAKES, PlacedDecoration.LAKE_POISON_FOREST);
        builder.feature(VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUNCHER_MANY);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PIRANHA_LILY);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_WATER_SURFACE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_AMANITA);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_AMANITA);
    }

    static void addForestOfIllusionFeatures(GenerationSettings.Builder builder) {
        builder.feature(LAKES, PlacedDecoration.LAKE_POISON_FOREST);
        builder.feature(VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FUZZBUSH);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PIRANHA_LILY);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_WATER_SURFACE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_AMANITA);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_ILLUSION);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_ILLUSION);
    }

    static void addAutumnForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(LAKES, PlacedDecoration.LAKE_POISON_FOREST);
        builder.feature(VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_WATER_SURFACE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_AUTUMN);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_AUTUMN);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_AUTUMN);
    }

    static void addAutumnMountainFeatures(GenerationSettings.Builder builder) {
        builder.feature(VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        builder.feature(VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.TALL_GRASS_PATCH);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_WATER_SURFACE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_AUTUMN);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_AUTUMN);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_GRASSLAND);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_AUTUMN_MOUNTAIN);
    }

    static void addMushroomGorgeFeatures(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.WIDE_CANYON);
        builder.feature(LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST_EXTRA);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FUZZBUSH);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_GORGE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_GORGE);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_GORGE);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_GORGE_CANYON);
    }

    static void addTallTallMountainFeatures(GenerationSettings.Builder builder) {
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.MUSHROOMS_MOUNTAIN);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_MOUNTAIN);
    }

    static void addDryDryDesertFeatures(GenerationSettings.Builder builder) {
        builder.feature(UNDERGROUND_ORES, PlacedDecoration.QUICKSAND);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.CACTUS_PATCH);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_DESERT);
    }

    static void addFossilFallsFeatures(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.WIDE_CANYON);
        builder.feature(UNDERGROUND_ORES, PlacedOres.ORE_VANILLATE_COAL_EXTRA);
        builder.feature(UNDERGROUND_ORES, PlacedOres.ORE_CHISELED_TOADSTONE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.STUMP);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_FOSSIL);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_FOSSIL);
    }

    public static void addCheepCheepReefFeatures(GenerationSettings.Builder builder) {
        builder.feature(LOCAL_MODIFICATIONS, PlacedPipes.OCEAN_FLOOR_PIPE);
        builder.feature(LOCAL_MODIFICATIONS, PlacedBlocks.JELLYBEAM);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.HORSETAIL);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_WATER_SURFACE);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.AMANITA_CARPET);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.CORAL_MANY);
        builder.feature(VEGETAL_DECORATION, OceanPlacedFeatures.KELP_WARM);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.PLANTS_AUTUMN);
        builder.feature(VEGETAL_DECORATION, PlacedPlants.FLOWERS_REEF);
        builder.feature(VEGETAL_DECORATION, PlacedVegetation.VEGETATION_REEF);
        builder.feature(VEGETAL_DECORATION, OceanPlacedFeatures.WARM_OCEAN_VEGETATION);
    }
}

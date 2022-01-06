package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredPlants;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OceanConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class PlacedPlants {
    public static final PlacedFeature SEAGRASS = OceanConfiguredFeatures.SEAGRASS_SHORT.withPlacement(CountPlacementModifier.of(50), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-24), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_FEW = FeatureRegistry.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_MANY = FeatureRegistry.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature AMANITA_CARPET = ConfiguredPlants.AMANITA_CARPET.withPlacement(CountPlacementModifier.of(170), HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(200)), SquarePlacementModifier.of(), BiomePlacementModifier.of());
    public static final PlacedFeature HORSETAIL = ConfiguredPlants.HORSETAIL_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature STUMP = ConfiguredPlants.STUMP_PATCH.withPlacement(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CACTUS = ConfiguredPlants.CACTUS.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlockTag(BlockTags.SAND, new Vec3i(0, -1, 0)))));
    public static final PlacedFeature CACTUS_PATCH = ConfiguredPlants.CACTUS_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature MUNCHER_FEW = ConfiguredPlants.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature FROZEN_MUNCHER = ConfiguredPlants.FROZEN_MUNCHER.withPlacement(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-7), YOffset.fixed(70)), BiomePlacementModifier.of());
    public static final PlacedFeature MUNCHER_MANY = ConfiguredPlants.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature FUZZBUSH = ConfiguredPlants.FUZZBUSH.withPlacement(RarityFilterPlacementModifier.of(30), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature PIRANHA_LILY = ConfiguredPlants.PIRANHA_LILY.withPlacement(RarityFilterPlacementModifier.of(30), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature WATER_PLANTS = ConfiguredPlants.WATER_PLANTS.withPlacement(RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERBED = ConfiguredPlants.FLOWERBED.withPlacement();
    public static final PlacedFeature GRASSLAND_PLANTS = ConfiguredPlants.GRASSLAND_PLANTS.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GRASSLAND_FLOWERS = ConfiguredPlants.GRASSLAND_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GRASSLAND_MUSHROOMS = ConfiguredPlants.GRASSLAND_MUSHROOMS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FOSSIL_FALLS_PLANTS = ConfiguredPlants.FOSSIL_FALLS_PLANTS.withPlacement(CountPlacementModifier.of(45), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FOSSIL_FALLS_FLOWERS = ConfiguredPlants.FOSSIL_FALLS_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MEADOW_PLANTS = ConfiguredPlants.GRASSLAND_PLANTS.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MEADOW_FLOWERS = ConfiguredPlants.GRASSLAND_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature REEF_FLOWERS = ConfiguredPlants.REEF_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GORGE_PLANTS = ConfiguredPlants.MUSHROOM_GORGE_PLANTS.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GORGE_FLOWERS = ConfiguredPlants.MUSHROOM_GORGE_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());

    public static final PlacedFeature DESERT_PLANTS = ConfiguredPlants.DESERT_PLANTS.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());

    public static final PlacedFeature AMANITA_FOREST_MUSHROOMS = ConfiguredPlants.AMANITA_FOREST_MUSHROOMS.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FOREST_OF_ILLUSION_FLOWERS = ConfiguredPlants.FOREST_OF_ILLUSION_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());

    public static final PlacedFeature AUTUMN_FOREST_PLANTS = ConfiguredPlants.AUTUMN_FOREST_PLANTS.withPlacement(CountPlacementModifier.of(7), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature AUTUMN_FOREST_MUSHROOMS = ConfiguredPlants.AUTUMN_FOREST_MUSHROOMS.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
}

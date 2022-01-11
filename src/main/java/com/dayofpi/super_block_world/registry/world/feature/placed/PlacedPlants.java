package com.dayofpi.super_block_world.registry.world.feature.placed;

import com.dayofpi.super_block_world.registry.world.feature.FeatureInit;
import com.dayofpi.super_block_world.registry.world.feature.configured.ConfiguredPlants;
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
    public static final PlacedFeature CORAL_FEW = FeatureInit.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_MANY = FeatureInit.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());

    public static final PlacedFeature STUMP = ConfiguredPlants.STUMP_PATCH.withPlacement(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CACTUS = ConfiguredPlants.CACTUS.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlockTag(BlockTags.SAND, new Vec3i(0, -1, 0)))));
    public static final PlacedFeature CACTUS_PATCH = ConfiguredPlants.CACTUS_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

    public static final PlacedFeature MUNCHER_FEW = ConfiguredPlants.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature MUNCHER_MANY = ConfiguredPlants.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature FROZEN_MUNCHER = ConfiguredPlants.FROZEN_MUNCHER.withPlacement(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-7), YOffset.fixed(70)), BiomePlacementModifier.of());

    public static final PlacedFeature FUZZBUSH = ConfiguredPlants.FUZZBUSH.withPlacement(RarityFilterPlacementModifier.of(30), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature PIRANHA_LILY = ConfiguredPlants.PIRANHA_LILY.withPlacement(RarityFilterPlacementModifier.of(30), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature HORSETAIL = ConfiguredPlants.HORSETAIL_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature TALL_GRASS_PATCH = ConfiguredPlants.TALL_GRASS_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature AMANITA_CARPET = ConfiguredPlants.AMANITA_CARPET.withPlacement(CountPlacementModifier.of(170), HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(200)), SquarePlacementModifier.of(), BiomePlacementModifier.of());

    public static final PlacedFeature PLANTS_GRASSLAND = ConfiguredPlants.PLANTS_GRASSLAND.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANTS_MEADOW = ConfiguredPlants.PLANTS_GRASSLAND.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANTS_DESERT = ConfiguredPlants.PLANTS_DESERT.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANTS_AUTUMN = ConfiguredPlants.PLANTS_AUTUMN.withPlacement(CountPlacementModifier.of(7), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANTS_GORGE = ConfiguredPlants.PLANTS_GORGE.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANTS_FOSSIL = ConfiguredPlants.PLANTS_FOSSIL.withPlacement(CountPlacementModifier.of(45), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANTS_WATER_SURFACE = ConfiguredPlants.PLANTS_WATER_SURFACE.withPlacement(RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());

    public static final PlacedFeature FLOWERS_GRASSLAND = ConfiguredPlants.FLOWERS_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERS_MEADOW = ConfiguredPlants.FLOWERS_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERS_REEF = ConfiguredPlants.FLOWERS_REEF.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERS_ILLUSION = ConfiguredPlants.FLOWERS_ILLUSION.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERS_GORGE = ConfiguredPlants.FLOWERS_GORGE.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERS_FOSSIL = ConfiguredPlants.FLOWERS_FOSSIL.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERBED = ConfiguredPlants.FLOWERBED.withPlacement();

    public static final PlacedFeature MUSHROOMS_GRASSLAND = ConfiguredPlants.MUSHROOMS_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MUSHROOMS_AMANITA = ConfiguredPlants.MUSHROOMS_AMANITA.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MUSHROOMS_AUTUMN = ConfiguredPlants.MUSHROOMS_AUTUMN.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MUSHROOMS_MOUNTAIN = ConfiguredPlants.MUSHROOMS_MOUNTAIN.withPlacement(RarityFilterPlacementModifier.of(12), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
}

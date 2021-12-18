package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureType;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredVegetation;
import com.dayofpi.super_block_world.main.registry.world.feature.utility.PlacementModifiers;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OceanConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

public class PlacedVegetation {
    public static final PlacedFeature SEAGRASS = OceanConfiguredFeatures.SEAGRASS_SHORT.withPlacement(CountPlacementModifier.of(50), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-24), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_FEW = FeatureType.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_MANY = FeatureType.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature HORSETAIL = ConfiguredVegetation.HORSETAIL_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature STUMP = ConfiguredVegetation.STUMP_PATCH.withPlacement(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CACTUS = ConfiguredVegetation.CACTUS_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature BEANSTALK_NOT_BUDDING = ConfiguredVegetation.BEANSTALK.withPlacement();
    public static final PlacedFeature BEANSTALK_BUDDING = ConfiguredVegetation.BEANSTALK_BUDDING.withPlacement();
    public static final PlacedFeature MUNCHER_FEW = ConfiguredVegetation.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature MUNCHER_MANY = ConfiguredVegetation.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature WATER_PLANTS = ConfiguredVegetation.WATER_PLANTS.withPlacement(RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERBED = ConfiguredVegetation.FLOWERBED.withPlacement();

    public static final PlacedFeature GRASSLAND_PLANTS = ConfiguredVegetation.GRASSLAND_PLANTS.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature AUTUMN_FOREST_PLANTS = ConfiguredVegetation.AUTUMN_FOREST_PLANTS.withPlacement(CountPlacementModifier.of(7), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GRASSLAND_FLOWERS = ConfiguredVegetation.GRASSLAND_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GRASSLAND_MUSHROOMS = ConfiguredVegetation.GRASSLAND_MUSHROOMS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GRASSLAND_VEGETATION = ConfiguredVegetation.GRASSLAND_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());

    public static final PlacedFeature REEF_FLOWERS = ConfiguredVegetation.REEF_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(14), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature REEF_VEGETATION = ConfiguredVegetation.REEF_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());

    public static final PlacedFeature GORGE_PLANTS = ConfiguredVegetation.MUSHROOM_GORGE_PLANTS.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GORGE_FLOWERS = ConfiguredVegetation.MUSHROOM_GORGE_FLOWERS.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature GORGE_VEGETATION = ConfiguredVegetation.MUSHROOM_GORGE_VEGETATION.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature CANYON_VEGETATION = ConfiguredVegetation.CANYON_VEGETATION.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(11), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());

    public static final PlacedFeature DESERT_PLANTS = ConfiguredVegetation.DESERT_PLANTS.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());

    public static final PlacedFeature AMANITA_FOREST_MUSHROOMS = ConfiguredVegetation.AMANITA_FOREST_MUSHROOMS.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature AMANITA_FOREST_VEGETATION = ConfiguredVegetation.AMANITA_FOREST_VEGETATION.withPlacement(CountPlacementModifier.of(21), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of(), PlacementModifiers.SAPLING_SURVIVES);

    public static final PlacedFeature AUTUMN_FOREST_MUSHROOMS = ConfiguredVegetation.AUTUMN_FOREST_MUSHROOMS.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature AUTUMN_FOREST_VEGETATION = ConfiguredVegetation.AUTUMN_FOREST_VEGETATION.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());

    public static final PlacedFeature CAVE_VEGETATION = ConfiguredVegetation.CAVE_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(5), CountPlacementModifier.of(100), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.fixed(70)), BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockRegistry.VANILLATE, BlockRegistry.HARDSTONE), new Vec3i(0, -1, 0)))));
}

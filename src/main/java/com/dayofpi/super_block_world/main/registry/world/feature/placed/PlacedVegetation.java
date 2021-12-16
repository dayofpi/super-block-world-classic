package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureType;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredVegetation;
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
    public static final PlacedFeature SEAGRASS = OceanConfiguredFeatures.SEAGRASS_SHORT.withPlacement(CountPlacementModifier.of(25), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_FEW = FeatureType.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_MANY = FeatureType.STRAWBERRY_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature HORSETAIL = ConfiguredVegetation.HORSETAIL_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature STUMP = ConfiguredVegetation.STUMP_PATCH.withPlacement(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CACTUS = ConfiguredVegetation.CACTUS_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature BEANSTALK_NOT_BUDDING = ConfiguredVegetation.BEANSTALK_NOT_BUDDING.withPlacement();
    public static final PlacedFeature BEANSTALK_BUDDING = ConfiguredVegetation.BEANSTALK_BUDDING.withPlacement();
    public static final PlacedFeature MUNCHER_FEW = ConfiguredVegetation.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature MUNCHER_MANY = ConfiguredVegetation.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERBED = ConfiguredVegetation.FLOWERBED.withPlacement();
    public static final PlacedFeature PLANT_GRASSLAND = ConfiguredVegetation.PLANT_GRASSLAND.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANT_GORGE = ConfiguredVegetation.PLANT_GORGE.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANT_DESERT = ConfiguredVegetation.PLANT_DESERT.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MUSHROOM_GRASSLAND = ConfiguredVegetation.MUSHROOM_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWER_GRASSLAND = ConfiguredVegetation.FLOWER_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWER_GORGE = ConfiguredVegetation.FLOWER_GORGE.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GRASSLAND = ConfiguredVegetation.VEGETATION_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GORGE = ConfiguredVegetation.VEGETATION_GORGE.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GORGE_TALL = ConfiguredVegetation.VEGETATION_GORGE_TALL.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(11), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_CAVE = ConfiguredVegetation.VEGETATION_CAVE.withPlacement(RarityFilterPlacementModifier.of(5), CountPlacementModifier.of(100), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.fixed(70)), BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockRegistry.VANILLATE, BlockRegistry.HARDSTONE), new Vec3i(0, -1, 0)))));
}

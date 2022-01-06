package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.main.BlockInit;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredVegetation;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

public class PlacedVegetation {
    public static final PlacedFeature GRASSLAND_VEGETATION = ConfiguredVegetation.GRASSLAND_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature SHERBET_LAND_VEGETATION = ConfiguredVegetation.SHERBET_LAND_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(1), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature MEADOW_VEGETATION = ConfiguredVegetation.GRASSLAND_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(8), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature GORGE_VEGETATION = ConfiguredVegetation.MUSHROOM_GORGE_VEGETATION.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature CANYON_VEGETATION = ConfiguredVegetation.CANYON_VEGETATION.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(11), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature AMANITA_FOREST_VEGETATION = ConfiguredVegetation.AMANITA_FOREST_VEGETATION.withPlacement(CountPlacementModifier.of(24), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature FOREST_OF_ILLUSION_VEGETATION = ConfiguredVegetation.FOREST_OF_ILLUSION_VEGETATION.withPlacement(CountPlacementModifier.of(45), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature AUTUMN_FOREST_VEGETATION = ConfiguredVegetation.AUTUMN_FOREST_VEGETATION.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature REEF_VEGETATION = ConfiguredVegetation.REEF_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_VEGETATION = ConfiguredVegetation.CAVE_VEGETATION.withPlacement(RarityFilterPlacementModifier.of(5), CountPlacementModifier.of(100), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.fixed(70)), BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockInit.VANILLATE, BlockInit.TOPPED_VANILLATE, BlockInit.HARDSTONE, BlockInit.FROSTY_VANILLATE, BlockInit.FROSTED_VANILLATE), new Vec3i(0, -1, 0)))));
}

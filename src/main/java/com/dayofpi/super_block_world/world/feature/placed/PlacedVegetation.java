package com.dayofpi.super_block_world.world.feature.placed;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredTrees;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredVegetation;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

public class PlacedVegetation {
    public static final PlacedFeature VEGETATION_GRASSLAND = ConfiguredVegetation.VEGETATION_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_SHERBET = ConfiguredVegetation.VEGETATION_SHERBET.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(1), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_MEADOW = ConfiguredVegetation.VEGETATION_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GORGE = ConfiguredVegetation.VEGETATION_GORGE.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GORGE_CANYON = ConfiguredVegetation.VEGETATION_GORGE_CANYON.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(11), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_MOUNTAIN = ConfiguredTrees.HUGE_RED_MUSHROOM_CANYON.withPlacement(CountPlacementModifier.of(24), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(11), HeightRangePlacementModifier.uniform(YOffset.fixed(63), YOffset.fixed(130)), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_AMANITA = ConfiguredVegetation.VEGETATION_AMANITA.withPlacement(CountPlacementModifier.of(24), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_ILLUSION = ConfiguredVegetation.VEGETATION_ILLUSION.withPlacement(CountPlacementModifier.of(45), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_AUTUMN = ConfiguredVegetation.VEGETATION_AUTUMN.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_AUTUMN_MOUNTAIN = ConfiguredVegetation.VEGETATION_AUTUMN_MOUNTAIN.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(1), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_REEF = ConfiguredVegetation.VEGETATION_REEF.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_UNDERGROUND = ConfiguredVegetation.VEGETATION_UNDERGROUND.withPlacement(RarityFilterPlacementModifier.of(5), CountPlacementModifier.of(100), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.fixed(70)), BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockInit.VANILLATE, BlockInit.TOPPED_VANILLATE, BlockInit.HARDSTONE, BlockInit.FROSTY_VANILLATE, BlockInit.FROSTED_VANILLATE), new Vec3i(0, -1, 0)))));
}

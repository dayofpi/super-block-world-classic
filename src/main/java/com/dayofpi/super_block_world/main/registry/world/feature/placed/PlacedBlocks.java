package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredBlocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

public class PlacedBlocks {
    public static final PlacedFeature JELLYBEAM = ConfiguredBlocks.JELLYBEAM.withPlacement(RarityFilterPlacementModifier.of(13), CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_SINGLE = ConfiguredBlocks.BLOCK_SINGLE.withPlacement(RarityFilterPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(85)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_SURFACE = ConfiguredBlocks.BLOCK_LINE_SURFACE.withPlacement(RarityFilterPlacementModifier.of(38), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(63), YOffset.fixed(90)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_DEEP = ConfiguredBlocks.BLOCK_LINE_DEEP.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-30), YOffset.fixed(62)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
}

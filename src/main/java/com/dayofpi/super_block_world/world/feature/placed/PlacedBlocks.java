package com.dayofpi.super_block_world.world.feature.placed;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredBlocks;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;

import java.util.List;

public class PlacedBlocks {
    public static final PlacedFeature JELLYBEAM_REEF = ConfiguredBlocks.JELLYBEAM.withPlacement(RarityFilterPlacementModifier.of(13), CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final PlacedFeature JELLYBEAM_CAVES = ConfiguredBlocks.JELLYBEAM.withPlacement(CountPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(50)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_SINGLE = ConfiguredBlocks.BLOCK_SINGLE.withPlacement(RarityFilterPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(85)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_SURFACE = ConfiguredBlocks.BLOCK_LINE_SURFACE.withPlacement(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(63), YOffset.fixed(90)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_SURFACE_CRYSTAL = ConfiguredBlocks.BLOCK_LINE_CRYSTAL.withPlacement(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(63), YOffset.fixed(90)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_DEEP = ConfiguredBlocks.BLOCK_LINE_DEEP.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(62)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_DEEP_CRYSTAL = ConfiguredBlocks.BLOCK_LINE_CRYSTAL.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(62)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_LAVA = ConfiguredBlocks.BLOCK_LINE_LAVA.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-30), YOffset.fixed(0)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_PILE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(81, 5, 1, () -> ConfiguredBlocks.BLOCK_PILE.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockInit.VANILLATE, BlockInit.FROSTY_VANILLATE, BlockInit.SNOWY_SHERBET_SOIL, BlockInit.GRASSY_TOADSTONE, BlockInit.GRITZY_SAND, BlockInit.QUICKSAND, BlockInit.CHARROCK), new Vec3i(0, -1, 0))))))).withPlacement(CountPlacementModifier.of(3), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());

}

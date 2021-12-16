package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredTrees;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.PlacedFeature;

public class PlacedTrees {
    public static final PlacedFeature AMANITA = ConfiguredTrees.AMANITA.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_FRUIT = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_LARGE = ConfiguredTrees.AMANITA_LARGE.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_MOUNTAIN = ConfiguredTrees.AMANITA_MOUNTAIN.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_OAKY = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_OAKY_FRUIT = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature DARK_AMANITA = ConfiguredTrees.DARK_AMANITA.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.DARK_AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature DARK_AMANITA_TALL = ConfiguredTrees.DARK_AMANITA_TALL.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.DARK_AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature HUGE_RED_MUSHROOM_FLAT = ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT.withPlacement();
    public static final PlacedFeature HUGE_GREEN_MUSHROOM_FLAT = ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT.withPlacement();
    public static final PlacedFeature HUGE_YELLOW_MUSHROOM = ConfiguredTrees.HUGE_YELLOW_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_GREEN_MUSHROOM = ConfiguredTrees.HUGE_GREEN_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_PINK_MUSHROOM = ConfiguredTrees.HUGE_PINK_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_PURPLE_MUSHROOM = ConfiguredTrees.HUGE_PURPLE_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_ORANGE_MUSHROOM = ConfiguredTrees.HUGE_ORANGE_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_BROWN_MUSHROOM = ConfiguredTrees.HUGE_BROWN_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_RED_MUSHROOM = ConfiguredTrees.HUGE_RED_MUSHROOM.withPlacement();
}

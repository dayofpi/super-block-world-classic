package com.dayofpi.super_block_world.world.feature.placed;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredDecoration;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.RandomBooleanFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

import java.util.List;

public class PlacedDecoration {
    private static final List<Block> PLACE_CRYSTAL_ON = List.of(BlockInit.VANILLATE, BlockInit.TOPPED_VANILLATE, BlockInit.FROSTY_VANILLATE, BlockInit.FROSTED_VANILLATE, BlockInit.HARDSTONE, BlockInit.ROYALITE);
    private static final PlacedFeature AMETHYST_CEILING = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(8, 7, 3, () -> ConfiguredDecoration.AMETHYST_CEILING.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, 1, 0))))))).withPlacement();
    private static final PlacedFeature AMETHYST_FLOOR = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(8, 7, 3, () -> ConfiguredDecoration.AMETHYST_FLOOR.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, -1, 0))))))).withPlacement();
    private static final PlacedFeature STAR_CRYSTAL_CEILING = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 7, 3, () -> ConfiguredDecoration.STAR_CRYSTAL_CEILING.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, 1, 0))))))).withPlacement();
    private static final PlacedFeature STAR_CRYSTAL_FLOOR = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 7, 3, () -> ConfiguredDecoration.STAR_CRYSTAL_FLOOR.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, -1, 0))))))).withPlacement();

    public static final PlacedFeature SPRING_HIGH = ConfiguredDecoration.SPRING_HIGH.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature SPRING_LOW = ConfiguredDecoration.SPRING_LOW.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(0)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_LAVA = ConfiguredDecoration.LAKE_LAVA.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_ICE = ConfiguredDecoration.LAKE_ICE.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_POISON = ConfiguredDecoration.LAKE_POISON.withPlacement(RarityFilterPlacementModifier.of(18), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(200)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_POISON_FOREST = ConfiguredDecoration.LAKE_POISON.withPlacement(RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(63), YOffset.fixed(100)), BiomePlacementModifier.of());
    public static final PlacedFeature ICICLE = ConfiguredDecoration.ICICLE.withPlacement(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature FREEZIE = ConfiguredDecoration.FREEZIE.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-7), YOffset.fixed(70)), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_DECORATION_COMMON = ConfiguredDecoration.CAVE_DECORATION_COMMON.withPlacement(CountPlacementModifier.of(10), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(84)), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_DECORATION_RARE = ConfiguredDecoration.CAVE_DECORATION_RARE.withPlacement(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(50)), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_DECORATION_LOW = ConfiguredDecoration.CAVE_DECORATION_LOW.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-20), YOffset.fixed(0)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING = ConfiguredDecoration.TOPPING.withPlacement(CountPlacementModifier.of(30), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(50)), BiomePlacementModifier.of());
    public static final PlacedFeature FROSTING = ConfiguredDecoration.FROSTING.withPlacement(CountPlacementModifier.of(30), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature QUICKSAND = ConfiguredDecoration.QUICKSAND.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature AMETHYST_EXTRA = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(() -> AMETHYST_CEILING, () -> AMETHYST_FLOOR)).withPlacement(CountPlacementModifier.of(ConstantIntProvider.create(8)), SquarePlacementModifier.of(), HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.fixed(40), YOffset.fixed(200))), BiomePlacementModifier.of());
    public static final PlacedFeature STAR_CRYSTAL = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(() -> STAR_CRYSTAL_CEILING, () -> STAR_CRYSTAL_FLOOR)).withPlacement(CountPlacementModifier.of(UniformIntProvider.create(3, 5)), SquarePlacementModifier.of(), HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(90))), BiomePlacementModifier.of());
}
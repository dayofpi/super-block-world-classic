package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredMisc;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

import java.util.List;

public class PlacedMisc {
    private static final List<Block> PLACE_CRYSTAL_ON = List.of(BlockRegistry.VANILLATE, BlockRegistry.FROSTY_VANILLATE, BlockRegistry.HARDSTONE, BlockRegistry.ROYALITE);
    private static final PlacedFeature AMETHYST_CEILING = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 7, 3, () -> ConfiguredMisc.AMETHYST_CEILING.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, 1, 0))))))).withPlacement();
    private static final PlacedFeature AMETHYST_FLOOR = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 7, 3, () -> ConfiguredMisc.AMETHYST_FLOOR.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, -1, 0))))))).withPlacement();
    public static final PlacedFeature SPRING = ConfiguredMisc.SPRING.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_LAVA = ConfiguredMisc.LAKE_LAVA.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_POISON = ConfiguredMisc.LAKE_POISON.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(200)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_POISON_FOREST = ConfiguredMisc.LAKE_POISON.withPlacement(RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(63), YOffset.fixed(100)), BiomePlacementModifier.of());
    public static final PlacedFeature ICICLE = ConfiguredMisc.ICICLE.withPlacement(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_DECORATION_COMMON = ConfiguredMisc.CAVE_DECORATION_COMMON.withPlacement(CountPlacementModifier.of(10), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_DECORATION_RARE = ConfiguredMisc.CAVE_DECORATION_RARE.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature PIPE = FeatureRegistry.PIPE.configure(FeatureConfig.DEFAULT).withPlacement();
    public static final PlacedFeature PIPE_PATCH = ConfiguredMisc.PIPE_PATCH.withPlacement(RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature UNDERWATER_PIPE = FeatureRegistry.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(16), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature OCEAN_FLOOR_PIPE = FeatureRegistry.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING = ConfiguredMisc.TOPPING.withPlacement(CountPlacementModifier.of(35), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature FROSTING = ConfiguredMisc.FROSTING.withPlacement(CountPlacementModifier.of(35), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature QUICKSAND = ConfiguredMisc.QUICKSAND.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature AMETHYST_EXTRA = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(() -> AMETHYST_CEILING, () -> AMETHYST_FLOOR)).withPlacement(CountPlacementModifier.of(ConstantIntProvider.create(8)), SquarePlacementModifier.of(), HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.fixed(40), YOffset.fixed(200))), BiomePlacementModifier.of());
    public static final PlacedFeature AMETHYST = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(() -> AMETHYST_CEILING, () -> AMETHYST_FLOOR)).withPlacement(CountPlacementModifier.of(UniformIntProvider.create(3, 5)), SquarePlacementModifier.of(), HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(90))), BiomePlacementModifier.of());
}
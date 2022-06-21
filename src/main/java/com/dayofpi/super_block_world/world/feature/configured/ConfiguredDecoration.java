package com.dayofpi.super_block_world.world.feature.configured;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.feature.utility.DataPools;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

@SuppressWarnings("deprecation")
public class ConfiguredDecoration {

    public static final ConfiguredFeature<?, ?> TOPPING = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_topping_to"), BlockStateProvider.of(BlockInit.TOPPED_VANILLATE.getDefaultState()), VegetationConfiguredFeatures.VINES::withPlacement, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0F, UniformIntProvider.create(5, 8), 0.3F));
    public static final ConfiguredFeature<?, ?> FROSTING = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_frosting_to"), BlockStateProvider.of(BlockInit.FROSTED_VANILLATE.getDefaultState()), VegetationConfiguredFeatures.VINES::withPlacement, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0F, UniformIntProvider.create(5, 8), 0.3F));
    public static final ConfiguredFeature<?, ?> QUICKSAND = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_quicksand_to"), BlockStateProvider.of(BlockInit.QUICKSAND.getDefaultState()), ConfiguredPlants.PIT_PLANT::withPlacement, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.8F, 1, 0.02F, UniformIntProvider.create(7, 11), 0.6F));
    public static final ConfiguredFeature<?, ?> SPRING_HIGH = Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(BlockInit.VANILLATE, BlockInit.TOADSTOOL_SOIL)));
    public static final ConfiguredFeature<?, ?> SPRING_LOW = Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(BlockInit.ROYALITE, BlockInit.CHARROCK)));
    public static final ConfiguredFeature<?, ?> LAKE_LAVA = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(BlockInit.VANILLATE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> LAKE_ICE = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(Blocks.ICE.getDefaultState()), BlockStateProvider.of(BlockInit.FROSTY_VANILLATE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> LAKE_POISON = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(BlockInit.POISON.getDefaultState()), BlockStateProvider.of(BlockInit.VANILLATE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> AMETHYST_CEILING = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.MEDIUM_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.DOWN), 2).add(Blocks.LARGE_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.DOWN), 2).add(Blocks.AMETHYST_CLUSTER.getDefaultState().with(Properties.FACING, Direction.DOWN), 1).build())));
    public static final ConfiguredFeature<?, ?> AMETHYST_FLOOR = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.MEDIUM_AMETHYST_BUD.getDefaultState(), 2).add(Blocks.LARGE_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.UP), 2).add(Blocks.AMETHYST_CLUSTER.getDefaultState().with(Properties.FACING, Direction.UP), 1).build())));
    public static final ConfiguredFeature<?, ?> STAR_CRYSTAL_CEILING = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockInit.STAR_CRYSTAL.getDefaultState().with(Properties.FACING, Direction.DOWN))));
    public static final ConfiguredFeature<?, ?> STAR_CRYSTAL_FLOOR = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockInit.STAR_CRYSTAL.getDefaultState().with(Properties.FACING, Direction.UP))));
    public static final ConfiguredFeature<?, ?> ICICLE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(60, 8, 4, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockInit.ICICLE))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlock(BlockInit.FROSTY_VANILLATE, new Vec3i(0, 1, 0)))))));
    public static final ConfiguredFeature<?, ?> FREEZIE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 12, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockInit.FREEZIE.getDefaultState()))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.matchingBlocks(List.of(Blocks.AIR, Blocks.SNOW)), BlockPredicate.matchingBlocks(List.of(BlockInit.CHARROCK, BlockInit.SNOWY_SHERBET_SOIL, BlockInit.FROSTY_VANILLATE, Blocks.ICE), new Vec3i(0, -1, 0)))))));
    public static final ConfiguredFeature<?, ?> CAVE_DECORATION_COMMON = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 12, 5, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.CAVE_DECORATION_COMMON))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockInit.TOADSTONE, BlockInit.GLOOMSTONE, BlockInit.VANILLATE, BlockInit.TOPPED_VANILLATE, BlockInit.FROSTY_VANILLATE, BlockInit.FROSTED_VANILLATE), new Vec3i(0, -1, 0)))))));
    public static final ConfiguredFeature<?, ?> CAVE_DECORATION_RARE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 12, 5, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.CAVE_DECORATION_RARE))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockInit.TOADSTONE, BlockInit.GLOOMSTONE, BlockInit.VANILLATE, BlockInit.TOPPED_VANILLATE, BlockInit.FROSTY_VANILLATE, BlockInit.FROSTED_VANILLATE, BlockInit.CHARROCK), new Vec3i(0, -1, 0)))))));
    public static final ConfiguredFeature<?, ?> CAVE_DECORATION_LOW = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 12, 5, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.CAVE_DECORATION_LOW))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(BlockInit.CHARROCK), new Vec3i(0, -1, 0)))))));
}

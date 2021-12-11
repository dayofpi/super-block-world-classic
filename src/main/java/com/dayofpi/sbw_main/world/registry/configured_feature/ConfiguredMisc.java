package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.MushroomBlocks;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ConfiguredMisc {
    private static final DataPool.Builder<BlockState> CAVE_DECORATION_POOL = DataPool.<BlockState>builder().add(PlantBlocks.CAVE_MUSHROOMS.getDefaultState(), 50).add(MushroomBlocks.GREEN_MUSHROOM.getDefaultState(), 45).add(MushroomBlocks.YELLOW_MUSHROOM.getDefaultState(), 30).add(PlantBlocks.VEGETABLE.getDefaultState(), 10).add(ModBlocks.STONE_TORCH.getDefaultState(), 9).add(ModBlocks.STONE_TORCH.getDefaultState().with(Properties.LIT, false), 10);

    public static final ConfiguredFeature<?, ?> SPRING = Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(ModBlocks.VANILLATE, ModBlocks.TOADSTOOL_SOIL)));
    public static final ConfiguredFeature<?, ?> LAKE_LAVA = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(ModBlocks.VANILLATE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> LAKE_POISON = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(ModBlocks.POISON.getDefaultState()), BlockStateProvider.of(ModBlocks.VANILLATE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> AMETHYST_CEILING = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.MEDIUM_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.DOWN), 2).add(Blocks.LARGE_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.DOWN), 2).add(Blocks.AMETHYST_CLUSTER.getDefaultState().with(Properties.FACING, Direction.DOWN), 1).build())));
    public static final ConfiguredFeature<?, ?> AMETHYST_FLOOR = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.MEDIUM_AMETHYST_BUD.getDefaultState(), 2).add(Blocks.LARGE_AMETHYST_BUD.getDefaultState().with(Properties.FACING, Direction.UP), 2).add(Blocks.AMETHYST_CLUSTER.getDefaultState().with(Properties.FACING, Direction.UP), 1).build())));
    public static final ConfiguredFeature<?, ?> CAVE_DECORATION = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 12, 5, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(CAVE_DECORATION_POOL))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(ModBlocks.VANILLATE, ModBlocks.TOPPED_VANILLATE), new Vec3i(0, -1, 0)))))));
}

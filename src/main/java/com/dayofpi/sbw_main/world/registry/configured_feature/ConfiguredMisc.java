package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

import java.util.List;

public class ConfiguredMisc {
    public static final ConfiguredFeature<?, ?> SPRING = Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(ModBlocks.VANILLATE, ModBlocks.TOADSTOOL_SOIL)));
    public static final ConfiguredFeature<?, ?> WATERFALL = Feature.SPRING_FEATURE.configure(new SpringFeatureConfig(Fluids.WATER.getDefaultState(), true, 2, 2, ImmutableSet.of(ModBlocks.TOADSTONE, Blocks.BONE_BLOCK)));
    public static final ConfiguredFeature<?, ?> LAKE_LAVA = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(ModBlocks.VANILLATE.getDefaultState())));
    public static final ConfiguredFeature<?, ?> LAKE_POISON = Feature.LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(ModBlocks.VANILLATE.getDefaultState())));

    public static final BlockColumnFeatureConfig.Layer PIPE_FIRST_LAYER = BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 4), SimpleBlockStateProvider.of(ModBlocks.WARP_PIPE_BODY.getDefaultState()));
    public static final BlockColumnFeatureConfig.Layer PIPE_SECOND_LAYER = BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), SimpleBlockStateProvider.of(ModBlocks.WARP_PIPE.getDefaultState()));
    public static final ConfiguredFeature<?, ?> PIPE_CEILING = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(8, 16, 32, () -> Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig(List.of(PIPE_FIRST_LAYER, PIPE_SECOND_LAYER), Direction.DOWN, BlockPredicate.IS_AIR, true)).withPlacement()));
    public static final ConfiguredFeature<?, ?> PIPE_FLOOR = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(8, 16, 32, () -> Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig(List.of(PIPE_FIRST_LAYER, PIPE_SECOND_LAYER), Direction.UP, BlockPredicate.IS_AIR, true)).withPlacement()));
}

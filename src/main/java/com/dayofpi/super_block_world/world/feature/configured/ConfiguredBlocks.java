package com.dayofpi.super_block_world.world.feature.configured;

import com.dayofpi.super_block_world.world.feature.utility.feature_config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.feature.FeatureInit;
import com.dayofpi.super_block_world.world.feature.utility.BlockLists;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ConfiguredBlocks {
    private static final DataPool.Builder<BlockState> BLOCK_POOL_SINGLE = DataPool.<BlockState>builder().add(BlockInit.QUESTION_BLOCK.getDefaultState(), 8).add(BlockInit.COIN_BLOCK.getDefaultState(), 8).add(BlockInit.JUMP_BLOCK.getDefaultState(), 1);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_SURFACE = DataPool.<BlockState>builder().add(BlockInit.TOADSTONE_BRICKS.getDefaultState(), 8).add(BlockInit.QUESTION_BLOCK.getDefaultState(), 4).add(BlockInit.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_UNDERGROUND = DataPool.<BlockState>builder().add(BlockInit.GLOOMSTONE_BRICKS.getDefaultState(), 8).add(BlockInit.QUESTION_BLOCK.getDefaultState(), 4).add(BlockInit.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_CRYSTAL = DataPool.<BlockState>builder().add(BlockInit.CRYSTAL_BRICKS.getDefaultState(), 8).add(BlockInit.QUESTION_BLOCK.getDefaultState(), 4).add(BlockInit.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_LAVA = DataPool.<BlockState>builder().add(BlockInit.HARDSTONE_BRICKS.getDefaultState(), 8).add(BlockInit.QUESTION_BLOCK.getDefaultState(), 4).add(BlockInit.COIN_BLOCK.getDefaultState(), 3);

    public static final ConfiguredFeature<?, ?> JELLYBEAM = FeatureInit.JELLYBEAM.configure(FeatureConfig.DEFAULT);
    public static final ConfiguredFeature<?, ?> BLOCK_SINGLE = FeatureInit.SINGLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_SINGLE)));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_SURFACE = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_SURFACE))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_DEEP = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_UNDERGROUND))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_CRYSTAL = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_CRYSTAL))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_LAVA = FeatureInit.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(16, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_LAVA))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_PILE = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BlockLists.BRICK_PILE), Direction.UP, BlockPredicate.IS_AIR, false));

}

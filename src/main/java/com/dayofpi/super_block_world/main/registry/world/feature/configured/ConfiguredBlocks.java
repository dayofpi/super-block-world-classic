package com.dayofpi.super_block_world.main.registry.world.feature.configured;

import com.dayofpi.super_block_world.main.common.world.feature.config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.utility.BlockLists;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ConfiguredBlocks {
    private static final DataPool.Builder<BlockState> BLOCK_POOL_SINGLE = DataPool.<BlockState>builder().add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 8).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 8).add(BlockRegistry.JUMP_BLOCK.getDefaultState(), 1);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_SURFACE = DataPool.<BlockState>builder().add(BlockRegistry.TOADSTONE_BRICKS.getDefaultState(), 8).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 4).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_UNDERGROUND = DataPool.<BlockState>builder().add(BlockRegistry.GLOOMSTONE_BRICKS.getDefaultState(), 8).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 4).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_CRYSTAL = DataPool.<BlockState>builder().add(BlockRegistry.CRYSTAL_BRICKS.getDefaultState(), 8).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 4).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_REEF = DataPool.<BlockState>builder().add(BlockRegistry.SEASTONE_BRICKS.getDefaultState(), 8).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 4).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 3);

    public static final ConfiguredFeature<?, ?> JELLYBEAM = FeatureRegistry.JELLYBEAM.configure(FeatureConfig.DEFAULT);
    public static final ConfiguredFeature<?, ?> BLOCK_SINGLE = FeatureRegistry.SINGLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_SINGLE)));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_SURFACE = FeatureRegistry.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_SURFACE))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_DEEP = FeatureRegistry.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_UNDERGROUND))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_CRYSTAL = FeatureRegistry.EXTRA_RANDOM_PATCH.configure(new ExtraRandomPatchFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_CRYSTAL))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_PILE = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BlockLists.BRICK_PILE), Direction.UP, BlockPredicate.IS_AIR, false));

}

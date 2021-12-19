package com.dayofpi.super_block_world.main.registry.world.feature.configured;

import com.dayofpi.super_block_world.main.common.world.feature.config.BlockLineFeatureConfig;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ConfiguredBlocks {
    private static final DataPool.Builder<BlockState> BLOCK_POOL_SINGLE = DataPool.<BlockState>builder().add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 8).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 8).add(BlockRegistry.JUMP_BLOCK.getDefaultState(), 1);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_SURFACE = DataPool.<BlockState>builder().add(BlockRegistry.TOADSTONE_BRICKS.getDefaultState(), 8).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 4).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> BLOCK_POOL_UNDERGROUND = DataPool.<BlockState>builder().add(BlockRegistry.GLOOMSTONE_BRICKS.getDefaultState(), 8).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 4).add(BlockRegistry.COIN_BLOCK.getDefaultState(), 3);

    public static final ConfiguredFeature<?, ?> JELLYBEAM = FeatureRegistry.JELLYBEAM.configure(FeatureConfig.DEFAULT);
    public static final ConfiguredFeature<?, ?> BLOCK_SINGLE = FeatureRegistry.SINGLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_SINGLE)));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_SURFACE = FeatureRegistry.BLOCK_LINE.configure(new BlockLineFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_SURFACE))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_DEEP = FeatureRegistry.BLOCK_LINE.configure(new BlockLineFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_POOL_UNDERGROUND))).withPlacement()));

}

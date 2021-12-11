package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.type.CoinBlock;
import com.dayofpi.sbw_main.world.feature_config.BlockLineFeatureConfig;
import com.dayofpi.sbw_main.world.registry.ModFeature;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ConfiguredBlocks {
    private static final DataPool.Builder<BlockState> BLOCK_SINGLE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 16)
            .add(ModBlocks.JUMP_BLOCK.getDefaultState(), 1)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 5), 8)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 3).with(CoinBlock.COIN_COUNT, 15), 1);

    private static final DataPool.Builder<BlockState> BLOCK_LINE_SURFACE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.TOADSTONE_BRICKS.getDefaultState(), 8)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 4)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 1).with(CoinBlock.COIN_COUNT, 5), 3)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 3);

    private static final DataPool.Builder<BlockState> BLOCK_LINE_DEEP_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.GLOOMSTONE_BRICKS.getDefaultState(), 8)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 4)
            //.add(ModBlocks.POW_BLOCK.getDefaultState(), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 2).with(CoinBlock.COIN_COUNT, 5), 3)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 3);

    private static final DataPool.Builder<BlockState> TOADSTONE_PILE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.TOADSTONE_BRICKS.getDefaultState(), 8)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 2)
            .add(ModBlocks.FAKE_BLOCK.getDefaultState(), 1)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 1).with(CoinBlock.COIN_COUNT, 5), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 2);

    private static final DataPool.Builder<BlockState> CRYSTAL_PILE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.CRYSTAL_BRICKS.getDefaultState(), 8)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 4).with(CoinBlock.COIN_COUNT, 5), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.COIN_COUNT, 1), 2);


    public static final ConfiguredFeature<?, ?> JELLYBEAM = ModFeature.JELLYBEAM.configure(FeatureConfig.DEFAULT);
    public static final ConfiguredFeature<?, ?> BLOCK_SINGLE = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_SINGLE_POOL)));

    public static final ConfiguredFeature<?, ?> BLOCK_LINE_SURFACE = ModFeature.BLOCK_LINE.configure(new BlockLineFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_LINE_SURFACE_POOL))).withPlacement()));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_DEEP = ModFeature.BLOCK_LINE.configure(new BlockLineFeatureConfig(8, 4, 1, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_LINE_DEEP_POOL))).withPlacement()));

}

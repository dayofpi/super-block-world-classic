package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.VariantBlocks;
import com.dayofpi.sbw_main.block.type.CoinBlock;
import com.dayofpi.sbw_main.world.feature_config.BlockLineFeatureConfig;
import com.dayofpi.sbw_main.world.registry.ModFeature;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ConfiguredBlocks {
    private static final DataPool.Builder<BlockState> BLOCK_SINGLE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 16)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 5), 8)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 3).with(CoinBlock.COIN_COUNT, 15), 1);

    private static final DataPool.Builder<BlockState> TOADSTONE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.TOADSTONE_BRICKS.getDefaultState(), 9)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 2)
            .add(ModBlocks.JUMP_BLOCK.getDefaultState(), 1)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 1).with(CoinBlock.COIN_COUNT, 5), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 2);

    private static final DataPool.Builder<BlockState> GLOOMSTONE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.GLOOMSTONE_BRICKS.getDefaultState(), 9)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 2)
            //.add(ModBlocks.POW_BLOCK.getDefaultState(), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 2).with(CoinBlock.COIN_COUNT, 5), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 2);

    private static final DataPool.Builder<BlockState> TOADSTONE_PILE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.TOADSTONE_BRICKS.getDefaultState(), 8)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 2)
            .add(ModBlocks.FAKE_BLOCK.getDefaultState(), 1)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 1).with(CoinBlock.COIN_COUNT, 5), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 2);

    private static final DataPool.Builder<BlockState> CRYSTAL_PILE_POOL = DataPool.<BlockState>builder()
            .add(ModBlocks.CRYSTAL_BRICKS.getDefaultState(), 8)
            .add(ModBlocks.QUESTION_BLOCK.getDefaultState(), 2)
            .add(VariantBlocks.CRYSTAL_BRICK_STAIRS.getDefaultState(), 1)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 3).with(CoinBlock.COIN_COUNT, 5), 2)
            .add(ModBlocks.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 0).with(CoinBlock.COIN_COUNT, 1), 2);


    public static final ConfiguredFeature<?, ?> JELLYBEAM = ModFeature.JELLYBEAM.configure(FeatureConfig.DEFAULT);
    public static final ConfiguredFeature<?, ?> BLOCK_SINGLE = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(BLOCK_SINGLE_POOL)));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_TOADSTONE = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(TOADSTONE_POOL)));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_GLOOMSTONE = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(GLOOMSTONE_POOL)));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE = ModFeature.BLOCK_LINE.configure(new BlockLineFeatureConfig(8, 4, 1, 1, BLOCK_LINE_TOADSTONE::withPlacement));
    public static final ConfiguredFeature<?, ?> BLOCK_LINE_DEEP = ModFeature.BLOCK_LINE.configure(new BlockLineFeatureConfig(8, 4, 1, 1, BLOCK_LINE_GLOOMSTONE::withPlacement));
    public static final ConfiguredFeature<?, ?> BLOCK_PILE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(170, 4, 2, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(TOADSTONE_PILE_POOL))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(ImmutableList.of(ModBlocks.VANILLATE, ModBlocks.TOPPED_VANILLATE, ModBlocks.SEASTONE, ModBlocks.TOADSTOOL_SOIL, ModBlocks.TOADSTONE, ModBlocks.GRASSY_TOADSTONE, ModBlocks.GRITZY_SAND, ModBlocks.GRITZY_SANDSTONE, ModBlocks.QUICKSAND), new Vec3i(0, -1, 0))))));
    public static final ConfiguredFeature<?, ?> BLOCK_PILE_RARE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(170, 4, 2, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(CRYSTAL_PILE_POOL))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(ImmutableList.of(ModBlocks.VANILLATE, ModBlocks.TOPPED_VANILLATE, ModBlocks.ROYALITE), new Vec3i(0, -1, 0))))));







}

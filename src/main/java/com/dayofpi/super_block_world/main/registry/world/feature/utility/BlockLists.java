package com.dayofpi.super_block_world.main.registry.world.feature.utility;

import com.dayofpi.super_block_world.main.common.block.plant.HorsetailBlock;
import com.dayofpi.super_block_world.main.common.block.reactive.CoinBlock;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.BlockColumnFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class BlockLists {
    private static final DataPool.Builder<BlockState> BLOCK_POOL = DataPool.<BlockState>builder().add(BlockRegistry.TOADSTONE_BRICKS.getDefaultState(), 5).add(BlockRegistry.QUESTION_BLOCK.getDefaultState(), 1).add(BlockRegistry.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 1), 2).add(BlockRegistry.FAKE_BLOCK.getDefaultState(), 1);

    public static final List<BlockColumnFeatureConfig.Layer> BLOCK_PILE_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 3), new WeightedBlockStateProvider(BLOCK_POOL)));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_NOT_BUDDING_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_BUDDING_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BUDDING_BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> HORSETAIL_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 0))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 1))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 2))));
}

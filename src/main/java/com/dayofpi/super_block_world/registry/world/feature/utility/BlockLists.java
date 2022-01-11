package com.dayofpi.super_block_world.registry.world.feature.utility;

import com.dayofpi.super_block_world.common.block.plant.HorsetailBlock;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.BlockColumnFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class BlockLists {
    public static final List<BlockColumnFeatureConfig.Layer> BRICK_PILE = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 3), new WeightedBlockStateProvider(DataPools.BRICK_PILE)));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_BUDDING = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BUDDING_BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_UNDERGROUND = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 55), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_BUDDING_UNDERGROUND = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 55), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BUDDING_BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> HORSETAIL = List.of(BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 0))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 1))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 2))));
}

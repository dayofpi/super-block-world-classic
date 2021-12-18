package com.dayofpi.super_block_world.main.registry.world.feature.utility;

import com.dayofpi.super_block_world.main.common.block.plant.HorsetailBlock;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.BlockColumnFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class BlockLists {
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_NOT_BUDDING_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_BUDDING_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), BlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.BUDDING_BEANSTALK.getDefaultState())));
    public static final List<BlockColumnFeatureConfig.Layer> HORSETAIL_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 0))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 1))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), BlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 2))));
}

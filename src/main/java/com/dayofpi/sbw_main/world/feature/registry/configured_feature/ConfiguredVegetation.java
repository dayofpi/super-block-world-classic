package com.dayofpi.sbw_main.world.feature.registry.configured_feature;

import com.dayofpi.sbw_main.registry.block.ModBlocks;
import com.dayofpi.sbw_main.registry.block.MushroomBlocks;
import com.dayofpi.sbw_main.registry.block.PlantBlocks;
import com.dayofpi.sbw_main.common.block.type.BushBlock;
import com.dayofpi.sbw_main.common.block.type.HorsetailBlock;
import com.dayofpi.sbw_main.world.feature.registry.ModPlacedFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ConfiguredVegetation {
    private static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_NOT_BUDDING_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), SimpleBlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), SimpleBlockStateProvider.of(PlantBlocks.BEANSTALK.getDefaultState())));
    private static final List<BlockColumnFeatureConfig.Layer> BEANSTALK_BUDDING_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 15), SimpleBlockStateProvider.of(PlantBlocks.BEANSTALK_PLANT.getDefaultState())), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), SimpleBlockStateProvider.of(PlantBlocks.BUDDING_BEANSTALK.getDefaultState())));
    private static final List<BlockColumnFeatureConfig.Layer> HORSETAIL_LAYERS = List.of(BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), SimpleBlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 0))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), SimpleBlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 1))), BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), SimpleBlockStateProvider.of(PlantBlocks.HORSETAIL.getDefaultState().with(HorsetailBlock.PART, 2))));

    private static final PlacementModifier canyonMushrooms = BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR_OR_WATER, BlockPredicate.matchingBlocks(List.of(ModBlocks.VANILLATE, ModBlocks.HARDSTONE), new Vec3i(0, -1, 0))));

    private static final DataPool.Builder<BlockState> FLOWERBED_FLOWERS = DataPool.<BlockState>builder().add(Blocks.DANDELION.getDefaultState(), 1).add(PlantBlocks.YELLOW_FLOWERBED.getDefaultState(), 10).add(PlantBlocks.WHITE_FLOWERBED.getDefaultState(), 2);

    private static final DataPool.Builder<BlockState> GRASSLAND_PLANTS = DataPool.<BlockState>builder().add(Blocks.GRASS.getDefaultState(), 90).add(Blocks.TALL_GRASS.getDefaultState(), 30).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 0), 30).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 1), 15).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 2), 5).add(PlantBlocks.VEGETABLE.getDefaultState(), 3);
    private static final DataPool.Builder<BlockState> GRASSLAND_MUSHROOMS = DataPool.<BlockState>builder().add(MushroomBlocks.YELLOW_MUSHROOM.getDefaultState(), 2).add(Blocks.BROWN_MUSHROOM.getDefaultState(), 2).add(Blocks.RED_MUSHROOM.getDefaultState(), 1);
    private static final DataPool.Builder<BlockState> GRASSLAND_FLOWERS = DataPool.<BlockState>builder().add(PlantBlocks.BLUE_SONGFLOWER.getDefaultState(), 5).add(PlantBlocks.YELLOW_SONGFLOWER.getDefaultState(), 2).add(PlantBlocks.PINK_SONGFLOWER.getDefaultState(), 2);

    private static final DataPool.Builder<BlockState> GORGE_PLANTS = DataPool.<BlockState>builder().add(Blocks.GRASS.getDefaultState(), 90).add(Blocks.TALL_GRASS.getDefaultState(), 30).add(Blocks.FERN.getDefaultState(), 30).add(Blocks.LARGE_FERN.getDefaultState(), 30).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 0), 30);

    private static final DataPool.Builder<BlockState> DESERT_PLANTS = DataPool.<BlockState>builder().add(Blocks.DEAD_BUSH.getDefaultState(), 90).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 0), 60).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 1), 15).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 2), 5);


    private static final DataPool.Builder<BlockState> AUTUMN_MUSHROOMS = DataPool.<BlockState>builder().add(MushroomBlocks.ORANGE_MUSHROOM.getDefaultState(), 2).add(Blocks.RED_MUSHROOM.getDefaultState(), 1);
    private static final DataPool.Builder<BlockState> FOREST_MUSHROOMS = DataPool.<BlockState>builder().add(Blocks.RED_MUSHROOM.getDefaultState(), 4).add(MushroomBlocks.PURPLE_MUSHROOM.getDefaultState(), 2).add(Blocks.BROWN_MUSHROOM.getDefaultState(), 2);

    public static final ConfiguredFeature<?, ?> BEANSTALK_NOT_BUDDING = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BEANSTALK_NOT_BUDDING_LAYERS), Direction.UP, BlockPredicate.IS_AIR, true));
    public static final ConfiguredFeature<?, ?> BEANSTALK_BUDDING = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BEANSTALK_BUDDING_LAYERS), Direction.UP, BlockPredicate.IS_AIR, true));
    private static final ConfiguredFeature<?, ?> HORSETAIL = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((HORSETAIL_LAYERS), Direction.UP, BlockPredicate.IS_AIR, true));
    public static final ConfiguredFeature<?, ?> HORSETAIL_PATCH = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> HORSETAIL.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlockTag(BlockTags.DIRT, new Vec3i(0, -1, 0)))))));
    private static final ConfiguredFeature<?, ?> STUMP = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig(List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 4), SimpleBlockStateProvider.of(ModBlocks.AMANITA_LOG))), Direction.UP, BlockPredicate.IS_AIR, false));
    private static final ConfiguredFeature<?, ?> CACTUS = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig(List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 2), SimpleBlockStateProvider.of(Blocks.CACTUS))), Direction.UP, BlockPredicate.IS_AIR, false));
    public static final ConfiguredFeature<?, ?> STUMP_PATCH = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 14, 3, () -> STUMP.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.hasSturdyFace(new Vec3i(0, -1, 0), Direction.UP))))));
    public static final ConfiguredFeature<?, ?> CACTUS_PATCH = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> CACTUS.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.hasSturdyFace(new Vec3i(0, -1, 0), Direction.UP))))));
    public static final ConfiguredFeature<?, ?> MUNCHER = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(32, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(SimpleBlockStateProvider.of(PlantBlocks.MUNCHER.getDefaultState()))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> FLOWERBED = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_flowerbed_to"), SimpleBlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState()), () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(FLOWERBED_FLOWERS))).withPlacement(), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0, 5, 0.7F, UniformIntProvider.create(4, 7), 0.3F));

    public static final ConfiguredFeature<?, ?> PLANT_GRASSLAND = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(40, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(GRASSLAND_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> PLANT_GORGE = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(32, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(GORGE_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> PLANT_DESERT = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(40, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DESERT_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> MUSHROOM_GRASSLAND = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(GRASSLAND_MUSHROOMS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> FLOWER_GRASSLAND = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(GRASSLAND_FLOWERS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> FLOWER_GORGE = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(SimpleBlockStateProvider.of(PlantBlocks.PINK_SONGFLOWER))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));

    public static final ConfiguredFeature<?, ?> VEGETATION_GRASSLAND = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(FLOWERBED.withPlacement(), 0.42F), new RandomFeatureEntry(BEANSTALK_BUDDING.withPlacement(),0.05F), new RandomFeatureEntry(ConfiguredTrees.HUGE_BROWN_MUSHROOM.withPlacement(), 0.15F), new RandomFeatureEntry(ModPlacedFeature.AMANITA_FRUIT, 0.4F)), ModPlacedFeature.AMANITA));
    public static final ConfiguredFeature<?, ?> VEGETATION_GORGE = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT.withPlacement(), 0.5F), new RandomFeatureEntry(ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT.withPlacement(), 0.5F)), ModPlacedFeature.AMANITA_MOUNTAIN));
    public static final ConfiguredFeature<?, ?> VEGETATION_GORGE_TALL = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredTrees.HUGE_GREEN_MUSHROOM_TALL.withPlacement(canyonMushrooms), 0.5F)), ConfiguredTrees.HUGE_RED_MUSHROOM_TALL.withPlacement(canyonMushrooms)));

    public static final ConfiguredFeature<?, ?> VEGETATION_CAVE = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(BEANSTALK_BUDDING.withPlacement(), 0.5F), new RandomFeatureEntry(BEANSTALK_NOT_BUDDING.withPlacement(), 0.5F), new RandomFeatureEntry(ModPlacedFeature.HUGE_GREEN_MUSHROOM_FLAT, 0.5F), new RandomFeatureEntry(ConfiguredTrees.HUGE_YELLOW_MUSHROOM.withPlacement(), 0.5F)), ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT.withPlacement()));
}



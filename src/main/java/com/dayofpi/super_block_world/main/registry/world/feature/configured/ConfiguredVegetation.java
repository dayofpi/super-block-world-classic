package com.dayofpi.super_block_world.main.registry.world.feature.configured;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedTrees;
import com.dayofpi.super_block_world.main.registry.world.feature.utility.BlockLists;
import com.dayofpi.super_block_world.main.registry.world.feature.utility.DataPools;
import com.dayofpi.super_block_world.main.registry.world.feature.utility.PlacementModifiers;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ConfiguredVegetation {
    public static final ConfiguredFeature<?, ?> AMANITA_CARPET = FeatureRegistry.AMANITA_CARPET.configure(new GlowLichenFeatureConfig(50, true, true, true, 1.0f, List.of(BlockRegistry.TOADSTOOL_GRASS, BlockRegistry.AMANITA_LEAVES, BlockRegistry.AMANITA_LOG, BlockRegistry.SHOREGRASS, BlockRegistry.GRASSY_TOADSTONE, BlockRegistry.FRUITING_AMANITA_LEAVES)));
    public static final ConfiguredFeature<?, ?> BEANSTALK = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BlockLists.BEANSTALK_NOT_BUDDING_LAYERS), Direction.UP, BlockPredicate.IS_AIR, true));
    public static final ConfiguredFeature<?, ?> BEANSTALK_BUDDING = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BlockLists.BEANSTALK_BUDDING_LAYERS), Direction.UP, BlockPredicate.IS_AIR, true));

    private static final ConfiguredFeature<?, ?> HORSETAIL = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig((BlockLists.HORSETAIL_LAYERS), Direction.UP, BlockPredicate.IS_AIR, true));
    public static final ConfiguredFeature<?, ?> HORSETAIL_PATCH = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> HORSETAIL.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlockTag(BlockTags.DIRT, new Vec3i(0, -1, 0)))))));

    private static final ConfiguredFeature<?, ?> STUMP = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig(List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 4), BlockStateProvider.of(BlockRegistry.AMANITA_LOG))), Direction.UP, BlockPredicate.IS_AIR, false));
    public static final ConfiguredFeature<?, ?> STUMP_PATCH = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 14, 3, () -> STUMP.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.hasSturdyFace(new Vec3i(0, -1, 0), Direction.UP))))));

    private static final ConfiguredFeature<?, ?> CACTUS = Feature.BLOCK_COLUMN.configure(new BlockColumnFeatureConfig(List.of(BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(1, 2), BlockStateProvider.of(Blocks.CACTUS))), Direction.UP, BlockPredicate.IS_AIR, false));
    public static final ConfiguredFeature<?, ?> CACTUS_PATCH = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> CACTUS.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.hasSturdyFace(new Vec3i(0, -1, 0), Direction.UP))))));

    public static final ConfiguredFeature<?, ?> MUNCHER = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(32, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(PlantBlocks.MUNCHER.getDefaultState()))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> FLOWERBED = Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(new Identifier("super_block_world:apply_flowerbed_to"), BlockStateProvider.of(BlockRegistry.TOADSTOOL_GRASS.getDefaultState()), () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.FLOWERBED))).withPlacement(), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0, 5, 0.7F, UniformIntProvider.create(4, 7), 0.3F));
    public static final ConfiguredFeature<?, ?> WATER_PLANTS = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.WATER_FLOWERS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.LILY_PAD.getDefaultState(), Vec3i.ZERO)))));

    public static final ConfiguredFeature<?, ?> GRASSLAND_PLANTS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(40, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.GRASSLAND_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> GRASSLAND_FLOWERS = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.GRASSLAND_FLOWERS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> GRASSLAND_MUSHROOMS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.GRASSLAND_MUSHROOMS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> GRASSLAND_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(FLOWERBED.withPlacement(), 0.42F), new RandomFeatureEntry(BEANSTALK_BUDDING.withPlacement(),0.05F), new RandomFeatureEntry(ConfiguredTrees.HUGE_BROWN_MUSHROOM.withPlacement(), 0.15F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.AMANITA));

    public static final ConfiguredFeature<?, ?> FOSSIL_FALLS_PLANTS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(40, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.FOSSIL_FALLS_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> FOSSIL_FALLS_FLOWERS = Feature.FLOWER.configure(new RandomPatchFeatureConfig(90, 5, 1, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.CORNFLOWER))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));

    public static final ConfiguredFeature<?, ?> MUSHROOM_GORGE_PLANTS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(32, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.GORGE_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> MUSHROOM_GORGE_FLOWERS = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(PlantBlocks.PINK_SONGFLOWER))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> MUSHROOM_GORGE_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT.withPlacement(), 0.5F), new RandomFeatureEntry(ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT.withPlacement(), 0.5F)), PlacedTrees.AMANITA_MOUNTAIN));
    public static final ConfiguredFeature<?, ?> CANYON_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredTrees.HUGE_GREEN_MUSHROOM_TALL.withPlacement(PlacementModifiers.IN_CANYON), 0.5F)), ConfiguredTrees.HUGE_RED_MUSHROOM_TALL.withPlacement(PlacementModifiers.IN_CANYON)));

    public static final ConfiguredFeature<?, ?> REEF_FLOWERS = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.REEF_FLOWERS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> REEF_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(FLOWERBED.withPlacement(), 0.42F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.42F)), PlacedTrees.AMANITA));

    public static final ConfiguredFeature<?, ?> AMANITA_FOREST_MUSHROOMS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.AMANITA_FOREST_MUSHROOMS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> AMANITA_FOREST_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(BEANSTALK_BUDDING.withPlacement(PlacementModifiers.SAPLING_SURVIVES), 0.03F), new RandomFeatureEntry(BEANSTALK.withPlacement(PlacementModifiers.SAPLING_SURVIVES),0.025F), new RandomFeatureEntry(PlacedTrees.AMANITA_OAKY_FRUIT, 0.5F), new RandomFeatureEntry(PlacedTrees.AMANITA, 0.2F)), PlacedTrees.AMANITA_OAKY));

    public static final ConfiguredFeature<?, ?> FOREST_OF_ILLUSION_FLOWERS = Feature.FLOWER.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.FOREST_OF_ILLUSION_FLOWERS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> FOREST_OF_ILLUSION_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(BEANSTALK_BUDDING.withPlacement(PlacementModifiers.SAPLING_SURVIVES), 0.03F), new RandomFeatureEntry(ConfiguredTrees.HUGE_PURPLE_MUSHROOM.withPlacement(),0.025F), new RandomFeatureEntry(PlacedTrees.DARK_AMANITA, 0.3F)), PlacedTrees.DARK_AMANITA_TALL));

    public static final ConfiguredFeature<?, ?> AUTUMN_FOREST_PLANTS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(30, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.GRASSLAND_PLANTS))).withPlacement(PlacementModifiers.ON_SOIL_AND_LEAVES)));
    public static final ConfiguredFeature<?, ?> AUTUMN_FOREST_MUSHROOMS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.AUTUMN_FOREST_MUSHROOMS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));
    public static final ConfiguredFeature<?, ?> AUTUMN_FOREST_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.AMANITA_LARGE, 0.4F), new RandomFeatureEntry(PlacedTrees.AMANITA_OAKY, 0.4F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.AMANITA));

    public static final ConfiguredFeature<?, ?> DESERT_PLANTS = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(40, 7, 3, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPools.DESERT_PLANTS))).withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR))));

    public static final ConfiguredFeature<?, ?> CAVE_VEGETATION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(BEANSTALK_BUDDING.withPlacement(), 0.25F), new RandomFeatureEntry(BEANSTALK.withPlacement(), 0.25F), new RandomFeatureEntry(PlacedTrees.HUGE_GREEN_MUSHROOM_FLAT, 0.5F), new RandomFeatureEntry(PlacedTrees.HUGE_YELLOW_MUSHROOM, 0.5F),  new RandomFeatureEntry(PlacedTrees.HUGE_ORANGE_MUSHROOM, 0.5F)), PlacedTrees.HUGE_RED_MUSHROOM_FLAT));
}



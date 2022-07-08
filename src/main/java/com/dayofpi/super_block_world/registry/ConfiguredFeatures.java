package com.dayofpi.super_block_world.registry;

import com.dayofpi.super_block_world.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

public class ConfiguredFeatures {
    public static final ConfiguredFeature<HugeMushroomFeatureConfig, ?> HUGE_GREEN_MUSHROOM = new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM, new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.GREEN_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)), BlockStateProvider.of(Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)), 2));
    public static final ConfiguredFeature<HugeMushroomFeatureConfig, ?> HUGE_YELLOW_MUSHROOM = new ConfiguredFeature<>(Feature.HUGE_BROWN_MUSHROOM, new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.YELLOW_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)), BlockStateProvider.of(Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)), 3));
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), BlockStateProvider.of(ModBlocks.AMANITA_LEAVES), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)).decorators(List.of(new AlterGroundTreeDecorator(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)))).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_FRUITING = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.AMANITA_LEAVES.getDefaultState(), 5).add(ModBlocks.FRUITING_AMANITA_LEAVES.getDefaultState(), 2)), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)).decorators(List.of(new AlterGroundTreeDecorator(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)))).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_CHERRY = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.AMANITA_LEAVES.getDefaultState(), 7).add(ModBlocks.FRUITING_AMANITA_LEAVES.getDefaultState(), 1)), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)).decorators(List.of(new AlterGroundTreeDecorator(BlockStateProvider.of(ModBlocks.CHERRY_GRASS)))).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_WITH_VINES = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.AMANITA_LOG), new StraightTrunkPlacer(2, 4, 1), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.AMANITA_LEAVES.getDefaultState(), 5).add(ModBlocks.FRUITING_AMANITA_LEAVES.getDefaultState(), 1)), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)).decorators(List.of(new AlterGroundTreeDecorator(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)), new LeavesVineTreeDecorator(0.5F))).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_LARGE = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.AMANITA_LOG), new MegaJungleTrunkPlacer(6, 5, 2), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.AMANITA_LEAVES.getDefaultState(), 10).add(ModBlocks.FRUITING_AMANITA_LEAVES.getDefaultState(), 2).build()), new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_SOIL.getDefaultState())).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> DARK_AMANITA = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.DARK_AMANITA_LOG), new BendingTrunkPlacer(4, 4, 0, 5, UniformIntProvider.create(1, 2)), BlockStateProvider.of(ModBlocks.DARK_AMANITA_LEAVES), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), ConstantIntProvider.create(3), 45), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS)).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> DARK_AMANITA_BIG = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.DARK_AMANITA_LOG), new MegaJungleTrunkPlacer(8, 2, 5), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.DARK_AMANITA_LEAVES.getDefaultState(), 8).add(ModBlocks.FRUITING_DARK_AMANITA_LEAVES.getDefaultState(), 1)), new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.DARK_AMANITA_LOG)).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> DARK_AMANITA_HUGE = new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.DARK_AMANITA_LOG), new MegaJungleTrunkPlacer(10, 2, 19), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.DARK_AMANITA_LEAVES.getDefaultState(), 8).add(ModBlocks.FRUITING_DARK_AMANITA_LEAVES.getDefaultState(), 1)), new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2), new TwoLayersFeatureSize(1, 0, 1)).dirtProvider(BlockStateProvider.of(ModBlocks.DARK_AMANITA_LOG)).build());
    public static final ConfiguredFeature<DefaultFeatureConfig, ?> BELL = new ConfiguredFeature<>(Features.BELL_TREE, new DefaultFeatureConfig());

    public static void register() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "huge_green_mushroom"), HUGE_GREEN_MUSHROOM);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "huge_yellow_mushroom"), HUGE_YELLOW_MUSHROOM);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "amanita"), AMANITA);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "amanita_fruiting"), AMANITA_FRUITING);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "amanita_cherry"), AMANITA_CHERRY);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "amanita_with_vines"), AMANITA_WITH_VINES);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "amanita_large"), AMANITA_LARGE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "dark_amanita"), DARK_AMANITA);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "dark_amanita_big"), DARK_AMANITA_BIG);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "dark_amanita_huge"), DARK_AMANITA_HUGE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, "bell"), BELL);
    }
}

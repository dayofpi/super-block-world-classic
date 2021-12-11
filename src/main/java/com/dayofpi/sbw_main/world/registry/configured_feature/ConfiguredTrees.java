package com.dayofpi.sbw_main.world.registry.configured_feature;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.MushroomBlocks;
import com.dayofpi.sbw_main.block.type.MushroomCapBlock;
import com.dayofpi.sbw_main.world.feature_config.GiantMushroomFeatureConfig;
import com.dayofpi.sbw_main.world.registry.ModFeature;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.OptionalInt;

public class ConfiguredTrees {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), BlockStateProvider.of(States.AMANITA_LEAVES), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_FRUIT = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(States.AMANITA_LEAVES, 3).add(States.FRUITING_AMANITA_LEAVES, 1).build()), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());

    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_LARGE = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new MegaJungleTrunkPlacer(6, 5, 2), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(States.AMANITA_LEAVES, 10).add(States.FRUITING_AMANITA_LEAVES, 2).build()), new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());

    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_MOUNTAIN = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(6, 5, 2), BlockStateProvider.of(States.AMANITA_LEAVES), new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(1, 2)), new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build()));

    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_OAKY = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(4, 3, 4), BlockStateProvider.of(States.AMANITA_LEAVES), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_OAKY_FRUIT = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(4, 3, 4), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(States.AMANITA_LEAVES, 3).add(States.FRUITING_AMANITA_LEAVES, 1).build()), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());

    public static final ConfiguredFeature<TreeFeatureConfig, ?> DARK_AMANITA = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.DARK_AMANITA_LOG), new DarkOakTrunkPlacer(4, 3, 1), BlockStateProvider.of(States.DARK_AMANITA_LEAVES), new LargeOakFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(1), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> DARK_AMANITA_TALL = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.DARK_AMANITA_LOG), new DarkOakTrunkPlacer(10, 9, 7), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(States.DARK_AMANITA_LEAVES, 10).add(States.FRUITING_DARK_AMANITA_LEAVES, 1)), new LargeOakFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(1), 3), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());

    public static final ConfiguredFeature<?, ?> HUGE_RED_MUSHROOM_FLAT = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.RED_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(6, 10), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_GREEN_MUSHROOM_FLAT = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.GREEN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(6, 10), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_RED_MUSHROOM_TALL = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.RED_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(15, 20), ConstantIntProvider.create(2), true));
    public static final ConfiguredFeature<?, ?> HUGE_GREEN_MUSHROOM_TALL = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.GREEN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(15, 20), ConstantIntProvider.create(2), true));
    public static final ConfiguredFeature<?, ?> HUGE_YELLOW_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.YELLOW_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(3, 6), ConstantIntProvider.create(2), false));
    public static final ConfiguredFeature<?, ?> HUGE_GREEN_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.GREEN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(3, 6), ConstantIntProvider.create(2), false));
    public static final ConfiguredFeature<?, ?> HUGE_PINK_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.PINK_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_PURPLE_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.PURPLE_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_ORANGE_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.ORANGE_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(0, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_BROWN_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.BROWN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_RED_MUSHROOM = ModFeature.HUGE_MUSHROOM.configure(new GiantMushroomFeatureConfig(BlockStateProvider.of(States.RED_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8),ConstantIntProvider.create(2), false));

    public static final class States {
        private static final BlockState AMANITA_LEAVES = ModBlocks.AMANITA_LEAVES.getDefaultState();
        private static final BlockState FRUITING_AMANITA_LEAVES = ModBlocks.FRUITING_AMANITA_LEAVES.getDefaultState();
        private static final BlockState DARK_AMANITA_LEAVES = ModBlocks.DARK_AMANITA_LEAVES.getDefaultState();
        private static final BlockState FRUITING_DARK_AMANITA_LEAVES = ModBlocks.FRUITING_DARK_AMANITA_LEAVES.getDefaultState();
        private static final BlockState AMANITA_LOG = ModBlocks.AMANITA_LOG.getDefaultState();
        private static final BlockState DARK_AMANITA_LOG = ModBlocks.DARK_AMANITA_LOG.getDefaultState();
        private static final BlockState BROWN_MUSHROOM_CAP = MushroomBlocks.BROWN_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState RED_MUSHROOM_CAP = MushroomBlocks.RED_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState YELLOW_MUSHROOM_CAP = MushroomBlocks.YELLOW_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState GREEN_MUSHROOM_CAP = MushroomBlocks.GREEN_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState PINK_MUSHROOM_CAP = MushroomBlocks.PINK_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState PURPLE_MUSHROOM_CAP = MushroomBlocks.PURPLE_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState ORANGE_MUSHROOM_CAP = MushroomBlocks.ORANGE_MUSHROOM_CAP.getDefaultState().with(MushroomCapBlock.DOWN, false);
        private static final BlockState MUSHROOM_STEM = MushroomBlocks.MUSHROOM_STEM.getDefaultState();
    }
}

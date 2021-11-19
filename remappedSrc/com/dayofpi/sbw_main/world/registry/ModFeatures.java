package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.MushroomBlocks;
import com.dayofpi.sbw_main.block.types.MushroomBlock;
import com.dayofpi.sbw_main.world.feature.types.CustomHugeMushroomFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class ModFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), BlockStateProvider.of(States.AMANITA_LEAVES), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> AMANITA_FRUIT = Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(States.AMANITA_LOG), new StraightTrunkPlacer(3, 2, 1), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(States.AMANITA_LEAVES, 3).add(States.FRUITING_AMANITA_LEAVES, 1).build()), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirtProvider(BlockStateProvider.of(ModBlocks.TOADSTOOL_GRASS.getDefaultState())).build());

    public static final ConfiguredFeature<?, ?> HUGE_RED_MUSHROOM_FLAT = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.RED_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(6, 10),UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_GREEN_MUSHROOM_FLAT = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.GREEN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(6, 10),UniformIntProvider.create(2, 3), true));

    public static final ConfiguredFeature<?, ?> HUGE_YELLOW_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.YELLOW_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_GREEN_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.GREEN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(3, 6), ConstantIntProvider.create(2), false));
    public static final ConfiguredFeature<?, ?> HUGE_PINK_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.PINK_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_PURPLE_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.PURPLE_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_ORANGE_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.ORANGE_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(0, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_BROWN_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.BROWN_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8), UniformIntProvider.create(2, 3), true));
    public static final ConfiguredFeature<?, ?> HUGE_RED_MUSHROOM = ModFeatureTypes.HUGE_MUSHROOM.configure(new CustomHugeMushroomFeatureConfig(BlockStateProvider.of(States.RED_MUSHROOM_CAP), BlockStateProvider.of(States.MUSHROOM_STEM), UniformIntProvider.create(4, 8),ConstantIntProvider.create(2), false));

    private static void registerConfiguredFeature(String id, ConfiguredFeature<?, ?> type) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, id), type);
    }

    public static void registerFeatures() {
        registerConfiguredFeature("amanita", AMANITA);
        registerConfiguredFeature("amanita_fruit", AMANITA_FRUIT);
        registerConfiguredFeature("huge_red_mushroom_flat", HUGE_RED_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_green_mushroom_flat", HUGE_GREEN_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_yellow_mushroom", HUGE_YELLOW_MUSHROOM);
        registerConfiguredFeature("huge_green_mushroom", HUGE_GREEN_MUSHROOM);
        registerConfiguredFeature("huge_pink_mushroom", HUGE_PINK_MUSHROOM);
        registerConfiguredFeature("huge_purple_mushroom", HUGE_PURPLE_MUSHROOM);
        registerConfiguredFeature("huge_orange_mushroom", HUGE_ORANGE_MUSHROOM);
        registerConfiguredFeature("huge_brown_mushroom", HUGE_BROWN_MUSHROOM);
        registerConfiguredFeature("huge_red_mushroom", HUGE_RED_MUSHROOM);
        }

    public static final class States {
        private static final BlockState BROWN_MUSHROOM_CAP = MushroomBlocks.BROWN_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState RED_MUSHROOM_CAP = MushroomBlocks.RED_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState YELLOW_MUSHROOM_CAP = MushroomBlocks.YELLOW_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState GREEN_MUSHROOM_CAP = MushroomBlocks.GREEN_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState PINK_MUSHROOM_CAP = MushroomBlocks.PINK_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState PURPLE_MUSHROOM_CAP = MushroomBlocks.PURPLE_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState ORANGE_MUSHROOM_CAP = MushroomBlocks.ORANGE_MUSHROOM_CAP.getDefaultState().with(MushroomBlock.DOWN, false);
        private static final BlockState MUSHROOM_STEM = MushroomBlocks.MUSHROOM_STEM.getDefaultState();
        private static final BlockState AMANITA_LEAVES = ModBlocks.AMANITA_LEAVES.getDefaultState();
        private static final BlockState FRUITING_AMANITA_LEAVES = ModBlocks.FRUITING_AMANITA_LEAVES.getDefaultState();
        private static final BlockState AMANITA_LOG = ModBlocks.AMANITA_LOG.getDefaultState();
    }
}

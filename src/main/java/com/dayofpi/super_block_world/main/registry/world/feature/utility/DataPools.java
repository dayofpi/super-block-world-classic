package com.dayofpi.super_block_world.main.registry.world.feature.utility;

import com.dayofpi.super_block_world.main.common.block.plant.BushBlock;
import com.dayofpi.super_block_world.main.common.block.plant.PiranhaLilyBlock;
import com.dayofpi.super_block_world.main.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;

public class DataPools {
    public static final DataPool.Builder<BlockState> FLOWERBED = DataPool.<BlockState>builder().add(Blocks.DANDELION.getDefaultState(), 1).add(PlantBlocks.YELLOW_FLOWERBED.getDefaultState(), 10).add(PlantBlocks.WHITE_FLOWERBED.getDefaultState(), 2);
    public static final DataPool.Builder<BlockState> WATER_FLOWERS = DataPool.<BlockState>builder().add(Blocks.LILY_PAD.getDefaultState(), 2).add(PlantBlocks.PIRANHA_LILY.getDefaultState().with(PiranhaLilyBlock.FLOATING, true), 2);

    public static final DataPool.Builder<BlockState> GRASSLAND_PLANTS = DataPool.<BlockState>builder().add(Blocks.GRASS.getDefaultState(), 90).add(Blocks.TALL_GRASS.getDefaultState(), 30).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 0), 30).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 1), 15).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 2), 5).add(PlantBlocks.VEGETABLE.getDefaultState(), 3);
    public static final DataPool.Builder<BlockState> GRASSLAND_MUSHROOMS = DataPool.<BlockState>builder().add(MushroomBlocks.YELLOW_MUSHROOM.getDefaultState(), 2).add(Blocks.BROWN_MUSHROOM.getDefaultState(), 2).add(Blocks.RED_MUSHROOM.getDefaultState(), 1);
    public static final DataPool.Builder<BlockState> GRASSLAND_FLOWERS = DataPool.<BlockState>builder().add(PlantBlocks.BLUE_SONGFLOWER.getDefaultState(), 5).add(PlantBlocks.YELLOW_SONGFLOWER.getDefaultState(), 2).add(PlantBlocks.PINK_SONGFLOWER.getDefaultState(), 2);

    public static final DataPool.Builder<BlockState> REEF_FLOWERS = DataPool.<BlockState>builder().add(PlantBlocks.BLUE_SONGFLOWER.getDefaultState(), 1).add(PlantBlocks.YELLOW_SONGFLOWER.getDefaultState(), 1).add(PlantBlocks.PINK_SONGFLOWER.getDefaultState(), 1).add(PlantBlocks.PAWFLOWER.getDefaultState(), 3);

    public static final DataPool.Builder<BlockState> GORGE_PLANTS = DataPool.<BlockState>builder().add(Blocks.GRASS.getDefaultState(), 90).add(Blocks.TALL_GRASS.getDefaultState(), 30).add(Blocks.FERN.getDefaultState(), 30).add(Blocks.LARGE_FERN.getDefaultState(), 30).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 0), 30);
    public static final DataPool.Builder<BlockState> DESERT_PLANTS = DataPool.<BlockState>builder().add(Blocks.DEAD_BUSH.getDefaultState(), 90).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 0), 60).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 1), 15).add(PlantBlocks.BUSH.getDefaultState().with(BushBlock.FRUITS, 2), 5);
    public static final DataPool.Builder<BlockState> AMANITA_FOREST_MUSHROOMS = DataPool.<BlockState>builder().add(Blocks.RED_MUSHROOM.getDefaultState(), 4).add(MushroomBlocks.PURPLE_MUSHROOM.getDefaultState(), 2).add(Blocks.BROWN_MUSHROOM.getDefaultState(), 2);

    public static final DataPool.Builder<BlockState> FOREST_OF_ILLUSION_FLOWERS = DataPool.<BlockState>builder().add(PlantBlocks.BLUE_SONGFLOWER.getDefaultState(), 10).add(PlantBlocks.YELLOW_SONGFLOWER.getDefaultState(), 10).add(PlantBlocks.PINK_SONGFLOWER.getDefaultState(), 10).add(PlantBlocks.FIRE_TULIP.getDefaultState(), 5);

    public static final DataPool.Builder<BlockState> AUTUMN_FOREST_MUSHROOMS = DataPool.<BlockState>builder().add(MushroomBlocks.ORANGE_MUSHROOM.getDefaultState(), 2).add(Blocks.RED_MUSHROOM.getDefaultState(), 1);
}

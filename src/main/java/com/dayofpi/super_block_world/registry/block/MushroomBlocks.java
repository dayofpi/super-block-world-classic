package com.dayofpi.super_block_world.registry.block;

import com.dayofpi.super_block_world.common.blocks.MushroomCapBlock;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredTrees;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class MushroomBlocks {
    public static final Block MUSHROOM_STEM = new PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OFF_WHITE).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM));
    public static final Block BROWN_MUSHROOM_CAP = new MushroomCapBlock(false, FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.DIRT_BROWN).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
    public static final Block ORANGE_MUSHROOM_CAP = new MushroomCapBlock(true, FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.ORANGE));
    public static final Block PURPLE_MUSHROOM_CAP = new MushroomCapBlock(false, FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.PURPLE));
    public static final Block PINK_MUSHROOM_CAP = new MushroomCapBlock(true, FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.PINK));
    public static final Block GREEN_MUSHROOM_CAP = new MushroomCapBlock(false, FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.EMERALD_GREEN));
    public static final Block YELLOW_MUSHROOM_CAP = new MushroomCapBlock(false, FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.YELLOW));
    public static final Block RED_MUSHROOM_CAP = new MushroomCapBlock(true, FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.RED));
    public static final Block YELLOW_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.of(Material.PLANT, MapColor.YELLOW).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS), () -> ConfiguredTrees.HUGE_YELLOW_MUSHROOM);
    public static final Block GREEN_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.EMERALD_GREEN).luminance(6), () -> ConfiguredTrees.HUGE_GREEN_MUSHROOM);
    public static final Block PINK_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.PINK), () -> ConfiguredTrees.HUGE_PINK_MUSHROOM);
    public static final Block PURPLE_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.PURPLE), () -> ConfiguredTrees.HUGE_PURPLE_MUSHROOM);
    public static final Block ORANGE_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.ORANGE), () -> ConfiguredTrees.HUGE_ORANGE_MUSHROOM);
    public static final Block BROWN_TOAD_STOOL = new Block(FabricBlockSettings.of(Material.DECORATION, MapColor.DIRT_BROWN).strength(2.0F).sounds(BlockSoundGroup.WART_BLOCK).nonOpaque());
    public static final Block ORANGE_TOAD_STOOL = new Block(FabricBlockSettings.copyOf(BROWN_TOAD_STOOL).mapColor(MapColor.ORANGE));
    public static final Block PURPLE_TOAD_STOOL = new Block( FabricBlockSettings.copyOf(BROWN_TOAD_STOOL).mapColor(MapColor.PURPLE));
    public static final Block PINK_TOAD_STOOL = new Block( FabricBlockSettings.copyOf(BROWN_TOAD_STOOL).mapColor(MapColor.PINK));
    public static final Block GREEN_TOAD_STOOL = new Block(FabricBlockSettings.copyOf(BROWN_TOAD_STOOL).mapColor(MapColor.EMERALD_GREEN));
    public static final Block YELLOW_TOAD_STOOL = new Block(FabricBlockSettings.copyOf(BROWN_TOAD_STOOL).mapColor(MapColor.YELLOW));
    public static final Block RED_TOAD_STOOL = new Block(FabricBlockSettings.copyOf(BROWN_TOAD_STOOL).mapColor(MapColor.RED));
}

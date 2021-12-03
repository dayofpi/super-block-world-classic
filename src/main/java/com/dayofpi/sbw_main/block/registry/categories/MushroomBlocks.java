package com.dayofpi.sbw_main.block.registry.categories;

import com.dayofpi.sbw_main.block.type.BouncyMushroomBlock;
import com.dayofpi.sbw_main.block.type.MushroomBlock;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredTrees;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class MushroomBlocks {
    public static final Block MUSHROOM_STEM = new PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OFF_WHITE).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM));
    public static final Block BROWN_MUSHROOM_CAP = new MushroomBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.DIRT_BROWN).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK));
    public static final Block ORANGE_MUSHROOM_CAP = new BouncyMushroomBlock(FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.ORANGE));
    public static final Block PURPLE_MUSHROOM_CAP = new MushroomBlock(FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.PURPLE));
    public static final Block PINK_MUSHROOM_CAP = new BouncyMushroomBlock(FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.PINK));
    public static final Block GREEN_MUSHROOM_CAP = new MushroomBlock(FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.EMERALD_GREEN));
    public static final Block YELLOW_MUSHROOM_CAP = new MushroomBlock(FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.YELLOW));
    public static final Block RED_MUSHROOM_CAP = new BouncyMushroomBlock(FabricBlockSettings.copyOf(BROWN_MUSHROOM_CAP).mapColor(MapColor.RED));
    public static final Block YELLOW_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.of(Material.PLANT, MapColor.YELLOW).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS), () -> ConfiguredTrees.HUGE_YELLOW_MUSHROOM);
    public static final Block GREEN_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.EMERALD_GREEN).luminance(6), () -> ConfiguredTrees.HUGE_GREEN_MUSHROOM);
    public static final Block PINK_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.PINK), () -> ConfiguredTrees.HUGE_PINK_MUSHROOM);
    public static final Block PURPLE_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.PURPLE), () -> ConfiguredTrees.HUGE_PURPLE_MUSHROOM);
    public static final Block ORANGE_MUSHROOM = new MushroomPlantBlock(FabricBlockSettings.copyOf(YELLOW_MUSHROOM).mapColor(MapColor.ORANGE), () -> ConfiguredTrees.HUGE_ORANGE_MUSHROOM);
}

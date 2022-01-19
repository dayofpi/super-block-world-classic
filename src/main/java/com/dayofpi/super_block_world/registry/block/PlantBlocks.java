package com.dayofpi.super_block_world.registry.block;

import com.dayofpi.super_block_world.common.blocks.*;
import com.dayofpi.super_block_world.common.blocks.plant.*;
import com.dayofpi.super_block_world.world.feature.utility.generator.AmanitaSaplingGenerator;
import com.dayofpi.super_block_world.world.feature.utility.generator.BellSaplingGenerator;
import com.dayofpi.super_block_world.world.feature.utility.generator.DarkAmanitaSaplingGenerator;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;

public class PlantBlocks {
    public static final Block AMANITA_SAPLING = new SaplingBlock(new AmanitaSaplingGenerator(), FabricBlockSettings.of(Material.PLANT, MapColor.GREEN).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS)){};
    public static final Block DARK_AMANITA_SAPLING = new SaplingBlock(new DarkAmanitaSaplingGenerator(), FabricBlockSettings.of(Material.PLANT, MapColor.DARK_GREEN).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS)){};
    public static final Block BELL_SAPLING = new SaplingBlock(new BellSaplingGenerator(), FabricBlockSettings.of(Material.PLANT, MapColor.YELLOW).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS)){};
    public static final Block HORSETAIL = new HorsetailBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block BUSH = new BushBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block VEGETABLE = new VegetableBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block BEANSTALK = new BeanstalkStemBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block BEANSTALK_PLANT = new BeanstalkPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block BUDDING_BEANSTALK = new BuddingBeanstalkBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PALE_GREEN).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS).ticksRandomly());
    public static final Block FUZZBALL = new FuzzballBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BLACK).ticksRandomly().nonOpaque().noCollision().sounds(BlockSoundGroup.SLIME));
    public static final Block FUZZBUSH = new FuzzbushBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BRIGHT_TEAL).ticksRandomly().nonOpaque().noCollision().sounds(BlockSoundGroup.AZALEA));
    public static final Block PIRANHA_LILY = new PiranhaLilyBlock(FabricBlockSettings.of(Material.PLANT, MapColor.RED).nonOpaque().noCollision().sounds(BlockSoundGroup.WET_GRASS));
    public static final Block PIT_PLANT = new MuncherBlock(FabricBlockSettings.of(Material.PLANT, MapColor.DARK_GREEN).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block MUNCHER = new MuncherBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BLACK).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block FROZEN_MUNCHER = new FrozenMuncherBlock(FabricBlockSettings.of(Material.ICE, MapColor.LIGHT_BLUE_GRAY).nonOpaque().sounds(BlockSoundGroup.GLASS).slipperiness(0.98f).strength(0.5f).ticksRandomly());
    public static final Block CAVE_MUSHROOMS = new CaveMushroomsBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.TERRACOTTA_BLUE).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block AMANITA_CARPET = new AmanitaCarpetBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block YELLOW_FLOWERBED = new FlowerbedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.YELLOW).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block WHITE_FLOWERBED = new FlowerbedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.WHITE).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block HYBRID_FLOWERBED = new FlowerbedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.DULL_PINK).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block BLUE_SONGFLOWER = new SongflowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.of(Material.PLANT, MapColor.DIAMOND_BLUE).ticksRandomly().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block PINK_SONGFLOWER = new SongflowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.copyOf(BLUE_SONGFLOWER).mapColor(MapColor.PINK));
    public static final Block YELLOW_SONGFLOWER = new SongflowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.copyOf(BLUE_SONGFLOWER).mapColor(MapColor.YELLOW));
    public static final Block PURPLE_SONGFLOWER = new SongflowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.copyOf(BLUE_SONGFLOWER).mapColor(MapColor.PALE_PURPLE));
    public static final Block PAWFLOWER = new FlowerBlock(StatusEffects.NIGHT_VISION, 6, FabricBlockSettings.of(Material.PLANT, MapColor.OFF_WHITE).noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block ROCKET_FLOWER = new RocketFlowerBlock(StatusEffects.SPEED, 6, FabricBlockSettings.of(Material.PLANT, MapColor.OFF_WHITE).noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block FIRE_TULIP = new FireTulipBlock(StatusEffects.FIRE_RESISTANCE, 6, FabricBlockSettings.of(Material.PLANT, MapColor.ORANGE).noCollision().sounds(BlockSoundGroup.GRASS).emissiveLighting(BlockInit::always));
    public static final Block STRAWBERRY_CORAL = new StrawberryCoralBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PINK).strength(0.2F).sounds(BlockSoundGroup.CORAL).nonOpaque());
}

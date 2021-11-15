package com.dayofpi.sbw_main.block.registry.categories;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.types.*;
import com.dayofpi.sbw_main.world.feature.generators.AmanitaSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;

public class PlantBlocks {
    public static final Block AMANITA_SAPLING = new SaplingBlock(new AmanitaSaplingGenerator(), FabricBlockSettings.of(Material.PLANT, MapColor.YELLOW).noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS)){};
    public static final Block HORSETAIL = new HorsetailBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block BUSH = new BushBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block VEGETABLE = new VegetableBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS)){};
    public static final Block BEANSTALK = new BeanstalkBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS).ticksRandomly());
    public static final Block BEANSTALK_PLANT = new BeanstalkPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block BUDDING_BEANSTALK = new BuddingBeanstalkBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS).ticksRandomly());
    public static final Block PIRANHA_LILY = new PiranhaLilyBlock(FabricBlockSettings.of(Material.PLANT, MapColor.RED).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block MUNCHER = new MuncherBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BLACK).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block CAVE_MUSHROOMS = new CaveMushroomBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.TERRACOTTA_BLUE).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block AMANITA_CARPET = new CarpetBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block YELLOW_FLOWERBED = new FlowerbedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.YELLOW).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block WHITE_FLOWERBED = new FlowerbedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.WHITE).noCollision().sounds(BlockSoundGroup.MOSS_CARPET));
    public static final Block BLUE_SONGFLOWER = new FlowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.of(Material.PLANT, MapColor.DIAMOND_BLUE).noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block PINK_SONGFLOWER = new FlowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.of(Material.PLANT, MapColor.PINK).noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block YELLOW_SONGFLOWER = new FlowerBlock(StatusEffects.JUMP_BOOST, 6, FabricBlockSettings.of(Material.PLANT, MapColor.YELLOW).noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block PAWFLOWER = new FlowerBlock(StatusEffects.SPEED, 6, FabricBlockSettings.of(Material.PLANT, MapColor.OFF_WHITE).noCollision().sounds(BlockSoundGroup.GRASS));
    public static final Block FIRE_TULIP = new FireTulipBlock(StatusEffects.FIRE_RESISTANCE, 6, FabricBlockSettings.of(Material.PLANT, MapColor.ORANGE).noCollision().sounds(BlockSoundGroup.GRASS).emissiveLighting(ModBlocks::always));
}

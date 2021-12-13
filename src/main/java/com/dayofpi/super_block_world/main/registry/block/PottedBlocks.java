package com.dayofpi.super_block_world.main.registry.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;

public class PottedBlocks {
    public static final Block POTTED_AMANITA_SAPLING = new FlowerPotBlock(PlantBlocks.AMANITA_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_DARK_AMANITA_SAPLING = new FlowerPotBlock(PlantBlocks.DARK_AMANITA_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_FIRE_TULIP = new FlowerPotBlock(PlantBlocks.FIRE_TULIP, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque().emissiveLighting(ModBlocks::always));
    public static final Block POTTED_MUNCHER = new FlowerPotBlock(PlantBlocks.MUNCHER, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_CAVE_MUSHROOMS = new FlowerPotBlock(PlantBlocks.CAVE_MUSHROOMS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_PIT_PLANT = new FlowerPotBlock(PlantBlocks.PIT_PLANT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_PIRANHA_LILY = new FlowerPotBlock(PlantBlocks.PIRANHA_LILY, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_PAWFLOWER = new FlowerPotBlock(PlantBlocks.PAWFLOWER, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_BLUE_SONGFLOWER = new FlowerPotBlock(PlantBlocks.BLUE_SONGFLOWER, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_PINK_SONGFLOWER = new FlowerPotBlock(PlantBlocks.PINK_SONGFLOWER, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_YELLOW_SONGFLOWER = new FlowerPotBlock(PlantBlocks.YELLOW_SONGFLOWER, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_YELLOW_MUSHROOM = new FlowerPotBlock(MushroomBlocks.YELLOW_MUSHROOM, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_GREEN_MUSHROOM = new FlowerPotBlock(MushroomBlocks.GREEN_MUSHROOM, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_PINK_MUSHROOM = new FlowerPotBlock(MushroomBlocks.PINK_MUSHROOM, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_PURPLE_MUSHROOM = new FlowerPotBlock(MushroomBlocks.PURPLE_MUSHROOM, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_ORANGE_MUSHROOM = new FlowerPotBlock(MushroomBlocks.ORANGE_MUSHROOM, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_HORSETAIL = new FlowerPotBlock(PlantBlocks.HORSETAIL, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_BUSH = new FlowerPotBlock(PlantBlocks.BUSH, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_BEANSTALK = new FlowerPotBlock(PlantBlocks.BEANSTALK, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_BUDDING_BEANSTALK = new FlowerPotBlock(PlantBlocks.BUDDING_BEANSTALK, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
}

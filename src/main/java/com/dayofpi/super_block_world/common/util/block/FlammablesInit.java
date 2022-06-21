package com.dayofpi.super_block_world.common.util.block;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.block.VariantBlocks;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class FlammablesInit {
    public static void register() {
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.AMANITA_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.AMANITA_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.AMANITA_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.AMANITA_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_TRAPDOOR, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.DARK_AMANITA_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.DARK_AMANITA_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.DARK_AMANITA_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.DARK_AMANITA_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_TRAPDOOR, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.BELL_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.BELL_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.BELL_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(VariantBlocks.BELL_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_TRAPDOOR, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(TagInit.AMANITA_LOGS, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(TagInit.DARK_AMANITA_LOGS, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(TagInit.BELL_LOGS, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.AMANITA_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.FRUITING_AMANITA_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARK_AMANITA_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.FRUITING_DARK_AMANITA_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.BELL_CAP, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockInit.DARKENED_BELL_CAP, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.AMANITA_CARPET, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.BLUE_SONGFLOWER, 30, 100);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.PINK_SONGFLOWER, 30, 100);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.YELLOW_SONGFLOWER, 30, 100);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.PURPLE_SONGFLOWER, 30, 100);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.PAWFLOWER, 30, 100);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.ROCKET_FLOWER, 30, 100);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.BEANSTALK, 15, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.BEANSTALK_PLANT, 15, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.BUDDING_BEANSTALK, 15, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.HORSETAIL, 15, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PlantBlocks.BUSH, 15, 60);
    }
}
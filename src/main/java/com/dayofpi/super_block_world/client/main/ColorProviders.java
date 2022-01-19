package com.dayofpi.super_block_world.client.main;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.item.DyeableItem;

public class ColorProviders {
    private static void setBlockColor(boolean foliage, int defaultColor,  Block... blocks) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) ->
                world != null && pos != null ? ( foliage ? BiomeColors.getFoliageColor(world, pos) : BiomeColors.getGrassColor(world, pos)) : defaultColor), blocks);
    }

    public static void setColors() {
        setBlockColor(false, 6879535, BlockInit.TOADSTOOL_GRASS, BlockInit.TOADSTOOL_TURF, BlockInit.GRASSY_TOADSTONE, BlockInit.GRASSY_HARDSTONE, BlockInit.SHOREGRASS, PlantBlocks.BUSH);
        setBlockColor(true, 6408218, BlockInit.AMANITA_LEAVES, BlockInit.DARK_AMANITA_LEAVES, BlockInit.FRUITING_AMANITA_LEAVES, BlockInit.FRUITING_DARK_AMANITA_LEAVES, PlantBlocks.AMANITA_CARPET);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 6879535, BlockInit.TOADSTOOL_GRASS, BlockInit.GRASSY_TOADSTONE, BlockInit.GRASSY_HARDSTONE, BlockInit.SHOREGRASS, BlockInit.TOADSTOOL_TURF, PlantBlocks.BUSH);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 6408218, BlockInit.AMANITA_LEAVES, BlockInit.FRUITING_AMANITA_LEAVES, BlockInit.DARK_AMANITA_LEAVES, BlockInit.FRUITING_DARK_AMANITA_LEAVES, PlantBlocks.AMANITA_CARPET);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), ItemInit.PLUMBER_CAP);
    }
}

package com.dayofpi.super_block_world.main.client;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.main.registry.block.PottedBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
@Environment(EnvType.CLIENT)
public class BlockRendering {
    private static void setBlockColor(boolean foliage, int defaultColor,  Block... blocks) {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) ->
                world != null && pos != null ? ( foliage ? BiomeColors.getFoliageColor(world, pos) : BiomeColors.getGrassColor(world, pos)) : defaultColor), blocks);
    }

    public static void setBlockColors() {
        setBlockColor(false, 6879535, BlockRegistry.TOADSTOOL_GRASS, BlockRegistry.TOADSTOOL_TURF, BlockRegistry.GRASSY_TOADSTONE, BlockRegistry.SHOREGRASS, PlantBlocks.BUSH);
        setBlockColor(true, 6408218, BlockRegistry.AMANITA_LEAVES, BlockRegistry.DARK_AMANITA_LEAVES, BlockRegistry.FRUITING_AMANITA_LEAVES, BlockRegistry.FRUITING_DARK_AMANITA_LEAVES, PlantBlocks.AMANITA_CARPET);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 6879535, BlockRegistry.TOADSTOOL_GRASS, BlockRegistry.GRASSY_TOADSTONE, BlockRegistry.SHOREGRASS, BlockRegistry.TOADSTOOL_TURF, PlantBlocks.BUSH);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 6408218, BlockRegistry.AMANITA_LEAVES, BlockRegistry.FRUITING_AMANITA_LEAVES, BlockRegistry.DARK_AMANITA_LEAVES, BlockRegistry.FRUITING_DARK_AMANITA_LEAVES, PlantBlocks.AMANITA_CARPET);
    }

    private static void setRenderLayer(Block block, RenderLayer layer) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
    }

    private static void setCutout(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static void setRenderLayers() {
        setRenderLayer(BlockRegistry.POISON, RenderLayer.getTranslucent());
        setRenderLayer(BlockRegistry.HIDDEN_BLOCK, RenderLayer.getTranslucent());
        setRenderLayer(PlantBlocks.FROZEN_MUNCHER, RenderLayer.getTranslucent());

        setRenderLayer(BlockRegistry.ICICLE, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.TRAMPOLINE, RenderLayer.getCutoutMipped());

        setRenderLayer(BlockRegistry.TOADSTOOL_GRASS, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.SHOREGRASS, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.GRASSY_TOADSTONE, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.FRUITING_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.DARK_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockRegistry.FRUITING_DARK_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(PlantBlocks.AMANITA_CARPET, RenderLayer.getCutoutMipped());

        setCutout(PlantBlocks.HORSETAIL);
        setCutout(PlantBlocks.BUSH);
        setCutout(PlantBlocks.VEGETABLE);
        setCutout(PlantBlocks.BEANSTALK);
        setCutout(PlantBlocks.BEANSTALK_PLANT);
        setCutout(PlantBlocks.BUDDING_BEANSTALK);
        setCutout(PlantBlocks.PIRANHA_LILY);
        setCutout(PlantBlocks.MUNCHER);
        setCutout(PlantBlocks.PIT_PLANT);

        setCutout(BlockRegistry.SPIKE_TRAP);
        setCutout(BlockRegistry.GIRDER);
        setCutout(BlockRegistry.STONE_TORCH);
        setCutout(BlockRegistry.BOO_LANTERN);

        setCutout(BlockRegistry.STRAWBERRY_CORAL);

        setCutout(PlantBlocks.YELLOW_FLOWERBED);
        setCutout(PlantBlocks.WHITE_FLOWERBED);
        setCutout(PlantBlocks.BLUE_SONGFLOWER);
        setCutout(PlantBlocks.PINK_SONGFLOWER);
        setCutout(PlantBlocks.YELLOW_SONGFLOWER);
        setCutout(PlantBlocks.PAWFLOWER);
        setCutout(PlantBlocks.FIRE_TULIP);

        setCutout(PlantBlocks.CAVE_MUSHROOMS);
        setCutout(MushroomBlocks.YELLOW_MUSHROOM);
        setCutout(MushroomBlocks.GREEN_MUSHROOM);
        setCutout(MushroomBlocks.PINK_MUSHROOM);
        setCutout(MushroomBlocks.PURPLE_MUSHROOM);
        setCutout(MushroomBlocks.ORANGE_MUSHROOM);
        setCutout(PlantBlocks.AMANITA_SAPLING);
        setCutout(PlantBlocks.DARK_AMANITA_SAPLING);

        setCutout(PottedBlocks.POTTED_AMANITA_SAPLING);
        setCutout(PottedBlocks.POTTED_DARK_AMANITA_SAPLING);
        setCutout(PottedBlocks.POTTED_HORSETAIL);
        setCutout(PottedBlocks.POTTED_BUSH);
        setCutout(PottedBlocks.POTTED_BEANSTALK);
        setCutout(PottedBlocks.POTTED_BUDDING_BEANSTALK);
        setCutout(PottedBlocks.POTTED_FIRE_TULIP);
        setCutout(PottedBlocks.POTTED_MUNCHER);
        setCutout(PottedBlocks.POTTED_PIT_PLANT);
        setCutout(PottedBlocks.POTTED_CAVE_MUSHROOMS);
        setCutout(PottedBlocks.POTTED_PIRANHA_LILY);
        setCutout(PottedBlocks.POTTED_BLUE_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_PINK_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_YELLOW_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_PAWFLOWER);
        setCutout(PottedBlocks.POTTED_GREEN_MUSHROOM);
        setCutout(PottedBlocks.POTTED_YELLOW_MUSHROOM);
        setCutout(PottedBlocks.POTTED_PINK_MUSHROOM);
        setCutout(PottedBlocks.POTTED_PURPLE_MUSHROOM);
        setCutout(PottedBlocks.POTTED_ORANGE_MUSHROOM);
    }
}
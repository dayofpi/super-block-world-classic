package com.dayofpi.super_block_world.client.main;

import com.dayofpi.super_block_world.client.renderers.other.PlacedItemBERenderer;
import com.dayofpi.super_block_world.registry.main.BlockEntityInit;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.block.PottedBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BlockRendering {

    private static void setRenderLayer(Block block, RenderLayer layer) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
    }

    private static void setCutout(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static void RenderBlocks() {
        BlockEntityRendererRegistry.register(BlockEntityInit.PLACED_ITEM, PlacedItemBERenderer::new);
        setRenderLayer(BlockInit.POISON, RenderLayer.getTranslucent());
        setRenderLayer(BlockInit.HIDDEN_BLOCK, RenderLayer.getTranslucent());
        setRenderLayer(PlantBlocks.FROZEN_MUNCHER, RenderLayer.getTranslucent());

        setRenderLayer(BlockInit.STAR_CRYSTAL, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.ICICLE, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.FREEZIE, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.TRAMPOLINE, RenderLayer.getCutoutMipped());

        setRenderLayer(BlockInit.TOADSTOOL_GRASS, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.SHOREGRASS, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.GRASSY_TOADSTONE, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.GRASSY_HARDSTONE, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.FRUITING_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.DARK_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        setRenderLayer(BlockInit.FRUITING_DARK_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
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
        setCutout(PlantBlocks.FUZZBALL);
        setCutout(PlantBlocks.FUZZBUSH);

        setRenderLayer(BlockInit.SPIKE_TRAP, RenderLayer.getCutoutMipped());
        setCutout(BlockInit.GIRDER);
        setCutout(BlockInit.STONE_TORCH);
        setCutout(BlockInit.BOO_LANTERN);

        setCutout(PlantBlocks.STRAWBERRY_CORAL);

        setCutout(PlantBlocks.YELLOW_FLOWERBED);
        setCutout(PlantBlocks.WHITE_FLOWERBED);
        setCutout(PlantBlocks.HYBRID_FLOWERBED);
        setCutout(PlantBlocks.BLUE_SONGFLOWER);
        setCutout(PlantBlocks.PINK_SONGFLOWER);
        setCutout(PlantBlocks.YELLOW_SONGFLOWER);
        setCutout(PlantBlocks.PURPLE_SONGFLOWER);
        setCutout(PlantBlocks.ROCKET_FLOWER);
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
        setCutout(PlantBlocks.BELL_SAPLING);

        setCutout(PottedBlocks.POTTED_AMANITA_SAPLING);
        setCutout(PottedBlocks.POTTED_DARK_AMANITA_SAPLING);
        setCutout(PottedBlocks.POTTED_BELL_SAPLING);
        setCutout(PottedBlocks.POTTED_HORSETAIL);
        setCutout(PottedBlocks.POTTED_BUSH);
        setCutout(PottedBlocks.POTTED_BEANSTALK);
        setCutout(PottedBlocks.POTTED_BUDDING_BEANSTALK);
        setCutout(PottedBlocks.POTTED_FIRE_TULIP);
        setCutout(PottedBlocks.POTTED_MUNCHER);
        setCutout(PottedBlocks.POTTED_PIT_PLANT);
        setCutout(PottedBlocks.POTTED_CAVE_MUSHROOMS);
        setCutout(PottedBlocks.POTTED_PIRANHA_LILY);
        setCutout(PottedBlocks.POTTED_ROCKET_FLOWER);
        setCutout(PottedBlocks.POTTED_BLUE_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_PINK_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_YELLOW_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_PURPLE_SONGFLOWER);
        setCutout(PottedBlocks.POTTED_PAWFLOWER);
        setCutout(PottedBlocks.POTTED_GREEN_MUSHROOM);
        setCutout(PottedBlocks.POTTED_YELLOW_MUSHROOM);
        setCutout(PottedBlocks.POTTED_PINK_MUSHROOM);
        setCutout(PottedBlocks.POTTED_PURPLE_MUSHROOM);
        setCutout(PottedBlocks.POTTED_ORANGE_MUSHROOM);
    }
}
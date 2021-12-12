package com.dayofpi.sbw_main.client;

import com.dayofpi.sbw_main.client.renderer.other.CustomSignRenderer;
import com.dayofpi.sbw_main.registry.ModBlockEntities;
import com.dayofpi.sbw_main.registry.block.ModBlocks;
import com.dayofpi.sbw_main.registry.block.MushroomBlocks;
import com.dayofpi.sbw_main.registry.block.PlantBlocks;
import com.dayofpi.sbw_main.registry.block.PottedBlocks;
import com.dayofpi.sbw_main.world.biome.ModBiomeBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
@Environment(EnvType.CLIENT)
public class BlockClient {
    public static void setRenderLayers() {
        BlockEntityRendererRegistry.register(ModBlockEntities.SIGN, CustomSignRenderer::new);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D),
                ModBlocks.TOADSTOOL_GRASS, ModBlocks.GRASSY_TOADSTONE, ModBlocks.TOADSTOOL_TURF, PlantBlocks.BUSH, PottedBlocks.POTTED_BUSH);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getColor(0.5D, 1.0D),
                ModBlocks.AMANITA_LEAVES, ModBlocks.FRUITING_AMANITA_LEAVES, ModBlocks.DARK_AMANITA_LEAVES, ModBlocks.FRUITING_DARK_AMANITA_LEAVES, PlantBlocks.AMANITA_CARPET);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ModBiomeBuilder.DEFAULT_GRASS_COLOR,
                ModBlocks.TOADSTOOL_GRASS,
                ModBlocks.GRASSY_TOADSTONE,
                ModBlocks.TOADSTOOL_TURF,
                PlantBlocks.BUSH);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ModBiomeBuilder.DEFAULT_FOLIAGE_COLOR,
                ModBlocks.AMANITA_LEAVES,
                ModBlocks.FRUITING_AMANITA_LEAVES,
                ModBlocks.DARK_AMANITA_LEAVES,
                ModBlocks.FRUITING_DARK_AMANITA_LEAVES,
                PlantBlocks.AMANITA_CARPET);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POISON, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOADSTOOL_GRASS, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASSY_TOADSTONE, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRUITING_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DARK_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRUITING_DARK_AMANITA_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.AMANITA_CARPET, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICICLE, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPIKE_TRAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GIRDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STONE_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOO_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TRAMPOLINE, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.HORSETAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.VEGETABLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.BEANSTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_CORAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.BEANSTALK_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.BUDDING_BEANSTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.PIRANHA_LILY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.YELLOW_FLOWERBED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.WHITE_FLOWERBED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.BLUE_SONGFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.PINK_SONGFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.YELLOW_SONGFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.PAWFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.FIRE_TULIP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.MUNCHER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.FROZEN_MUNCHER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.PIT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.CAVE_MUSHROOMS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlocks.YELLOW_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlocks.GREEN_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlocks.PINK_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlocks.PURPLE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MushroomBlocks.ORANGE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.AMANITA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PlantBlocks.DARK_AMANITA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_AMANITA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_DARK_AMANITA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_HORSETAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_BEANSTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_BUDDING_BEANSTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_FIRE_TULIP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_MUNCHER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_PIT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_CAVE_MUSHROOMS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_PIRANHA_LILY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_BLUE_SONGFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_PINK_SONGFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_YELLOW_SONGFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_PAWFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_GREEN_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_YELLOW_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_PINK_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_PURPLE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PottedBlocks.POTTED_ORANGE_MUSHROOM, RenderLayer.getCutout());

    }
}
package com.dayofpi.super_block_world.main.registry.block;

import com.dayofpi.super_block_world.main.common.block.pipe.PipeBodyBlock;
import com.dayofpi.super_block_world.main.common.block.pipe.WarpPipeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class ColoredBlocks {
    public static final Block WHITE_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.WHITE));
    public static final Block ORANGE_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.ORANGE));
    public static final Block MAGENTA_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.MAGENTA));
    public static final Block LIGHT_BLUE_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.LIGHT_BLUE));
    public static final Block YELLOW_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.YELLOW));
    public static final Block LIME_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.LIME));
    public static final Block PINK_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.PINK));
    public static final Block GRAY_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.GRAY));
    public static final Block LIGHT_GRAY_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.LIGHT_GRAY));
    public static final Block CYAN_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.CYAN));
    public static final Block PURPLE_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.PURPLE));
    public static final Block BLUE_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.BLUE));
    public static final Block BROWN_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.BROWN));
    public static final Block GREEN_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.GREEN));
    public static final Block RED_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.RED));
    public static final Block BLACK_BRONZE = new Block(FabricBlockSettings.copyOf(BlockRegistry.BRONZE_BLOCK).mapColor(MapColor.BLACK));

    public static final Block WHITE_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_ORANGE).requiresTool().strength(3.0F, 20.0F).nonOpaque().sounds(BlockSoundGroup.COPPER));
    public static final Block ORANGE_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.ORANGE));
    public static final Block MAGENTA_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.MAGENTA));
    public static final Block LIGHT_BLUE_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.LIGHT_BLUE));
    public static final Block YELLOW_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.YELLOW));
    public static final Block LIME_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.LIME));
    public static final Block PINK_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.PINK));
    public static final Block GRAY_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.GRAY));
    public static final Block LIGHT_GRAY_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.LIGHT_GRAY));
    public static final Block CYAN_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.CYAN));
    public static final Block PURPLE_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.PURPLE));
    public static final Block BLUE_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.BLUE));
    public static final Block BROWN_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.TERRACOTTA_ORANGE));
    public static final Block GREEN_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.EMERALD_GREEN));
    public static final Block RED_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.BRIGHT_RED));
    public static final Block BLACK_WARP_PIPE = new WarpPipeBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE).mapColor(MapColor.BLACK));

    public static final Block WHITE_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(WHITE_WARP_PIPE));
    public static final Block ORANGE_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(ORANGE_WARP_PIPE));
    public static final Block MAGENTA_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(MAGENTA_WARP_PIPE));
    public static final Block LIGHT_BLUE_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(LIGHT_BLUE_WARP_PIPE));
    public static final Block YELLOW_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(YELLOW_WARP_PIPE));
    public static final Block LIME_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(LIME_WARP_PIPE));
    public static final Block PINK_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(PINK_WARP_PIPE));
    public static final Block GRAY_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(GRAY_WARP_PIPE));
    public static final Block LIGHT_GRAY_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(LIGHT_GRAY_WARP_PIPE));
    public static final Block CYAN_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(CYAN_WARP_PIPE));
    public static final Block PURPLE_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(PURPLE_WARP_PIPE));
    public static final Block BLUE_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(BLUE_WARP_PIPE));
    public static final Block BROWN_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(BROWN_WARP_PIPE));
    public static final Block GREEN_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(GREEN_WARP_PIPE));
    public static final Block RED_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(RED_WARP_PIPE));
    public static final Block BLACK_PIPE_BODY = new PipeBodyBlock(FabricBlockSettings.copyOf(RED_WARP_PIPE));
}

package com.dayofpi.super_block_world.registry.main;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.blocks.block_entities.*;
import com.dayofpi.super_block_world.registry.block.ColoredBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityInit {

    public static BlockEntityType<PlacedItemBE> PLACED_ITEM;
    public static BlockEntityType<QuestionBlockBE> QUESTION_BLOCK;
    public static BlockEntityType<QuestionBoxBE> QUESTION_BOX;
    public static BlockEntityType<CoinBlockBE> COIN_BLOCK;
    public static BlockEntityType<BooLanternBE> BOO_LANTERN;
    public static BlockEntityType<WarpPipeBE> WARP_PIPE;
    public static BlockEntityType<DryBonesPileBE> DRY_BONES_PILE;

    public static void registerBlockEntities() {
        PLACED_ITEM = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "placed_item"), FabricBlockEntityTypeBuilder.create(PlacedItemBE::new, BlockInit.COIN, BlockInit.STAR_COIN, BlockInit.POWER_STAR).build());
        QUESTION_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_block"), FabricBlockEntityTypeBuilder.create(QuestionBlockBE::new, BlockInit.QUESTION_BLOCK).build());
        QUESTION_BOX = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_box"), FabricBlockEntityTypeBuilder.create(QuestionBoxBE::new, BlockInit.QUESTION_BOX).build());
        COIN_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "coin_block"), FabricBlockEntityTypeBuilder.create(CoinBlockBE::new, BlockInit.COIN_BLOCK).build());
        BOO_LANTERN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "boo_lantern"), FabricBlockEntityTypeBuilder.create(BooLanternBE::new, BlockInit.BOO_LANTERN).build());
        WARP_PIPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "warp_pipe"), FabricBlockEntityTypeBuilder.create(WarpPipeBE::new, ColoredBlocks.WHITE_WARP_PIPE, ColoredBlocks.ORANGE_WARP_PIPE, ColoredBlocks.MAGENTA_WARP_PIPE, ColoredBlocks.LIGHT_BLUE_WARP_PIPE, ColoredBlocks.YELLOW_WARP_PIPE, ColoredBlocks.LIME_WARP_PIPE, ColoredBlocks.PINK_WARP_PIPE, ColoredBlocks.GRAY_WARP_PIPE, ColoredBlocks.LIGHT_GRAY_WARP_PIPE, ColoredBlocks.CYAN_WARP_PIPE, ColoredBlocks.PURPLE_WARP_PIPE, ColoredBlocks.BLUE_WARP_PIPE, ColoredBlocks.BROWN_WARP_PIPE, ColoredBlocks.GREEN_WARP_PIPE, ColoredBlocks.RED_WARP_PIPE, ColoredBlocks.BLACK_WARP_PIPE).build());
        DRY_BONES_PILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "dry_bones_pile"), FabricBlockEntityTypeBuilder.create(DryBonesPileBE::new, BlockInit.DRY_BONES_PILE).build());
    }
}

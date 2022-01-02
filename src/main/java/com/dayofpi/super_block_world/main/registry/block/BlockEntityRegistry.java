package com.dayofpi.super_block_world.main.registry.block;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.block_entity.BooLanternBE;
import com.dayofpi.super_block_world.main.common.block_entity.CoinBlockBE;
import com.dayofpi.super_block_world.main.common.block_entity.QuestionBlockBE;
import com.dayofpi.super_block_world.main.common.block_entity.WarpPipeBE;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistry {

    public static BlockEntityType<QuestionBlockBE> QUESTION_BLOCK;
    public static BlockEntityType<CoinBlockBE> COIN_BLOCK;
    public static BlockEntityType<BooLanternBE> BOO_LANTERN;
    public static BlockEntityType<WarpPipeBE> WARP_PIPE;

    public static void registerBlockEntities() {
        QUESTION_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_block"), FabricBlockEntityTypeBuilder.create(QuestionBlockBE::new, BlockRegistry.QUESTION_BLOCK).build());
        COIN_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "coin_block"), FabricBlockEntityTypeBuilder.create(CoinBlockBE::new, BlockRegistry.COIN_BLOCK).build());
        BOO_LANTERN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "boo_lantern"), FabricBlockEntityTypeBuilder.create(BooLanternBE::new, BlockRegistry.BOO_LANTERN).build());
        WARP_PIPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "warp_pipe"), FabricBlockEntityTypeBuilder.create(WarpPipeBE::new, ColoredBlocks.WHITE_WARP_PIPE, ColoredBlocks.ORANGE_WARP_PIPE, ColoredBlocks.MAGENTA_WARP_PIPE, ColoredBlocks.LIGHT_BLUE_WARP_PIPE, ColoredBlocks.YELLOW_WARP_PIPE, ColoredBlocks.LIME_WARP_PIPE, ColoredBlocks.PINK_WARP_PIPE, ColoredBlocks.GRAY_WARP_PIPE, ColoredBlocks.LIGHT_GRAY_WARP_PIPE, ColoredBlocks.CYAN_WARP_PIPE, ColoredBlocks.PURPLE_WARP_PIPE, ColoredBlocks.BLUE_WARP_PIPE, ColoredBlocks.BROWN_WARP_PIPE, ColoredBlocks.GREEN_WARP_PIPE, ColoredBlocks.RED_WARP_PIPE, ColoredBlocks.BLACK_WARP_PIPE).build());
    }
}

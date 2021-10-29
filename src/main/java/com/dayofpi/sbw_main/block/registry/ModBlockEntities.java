package com.dayofpi.sbw_main.block.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.block_entity.BooLanternBE;
import com.dayofpi.sbw_main.block.block_entity.ModSignBE;
import com.dayofpi.sbw_main.block.block_entity.QuestionBlockBE;
import com.dayofpi.sbw_main.block.block_entity.WarpPipeBE;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<ModSignBE> MOD_SIGN;
    public static BlockEntityType<QuestionBlockBE> QUESTION_BLOCK;
    public static BlockEntityType<BooLanternBE> BOO_LANTERN;
    public static BlockEntityType<WarpPipeBE> WARP_PIPE;

    public static void registerBlockEntities() {
        MOD_SIGN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "mod_sign"), FabricBlockEntityTypeBuilder.create(ModSignBE::new, ModBlocks.AMANITA_SIGN, ModBlocks.AMANITA_WALL_SIGN).build());
        QUESTION_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_block"), FabricBlockEntityTypeBuilder.create(QuestionBlockBE::new, ModBlocks.QUESTION_BLOCK).build());
        BOO_LANTERN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "boo_lantern"), FabricBlockEntityTypeBuilder.create(BooLanternBE::new, ModBlocks.BOO_LANTERN).build());
        WARP_PIPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "warp_pipe"), FabricBlockEntityTypeBuilder.create(WarpPipeBE::new, ModBlocks.WARP_PIPE).build());
    }
}

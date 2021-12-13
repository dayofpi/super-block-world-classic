package com.dayofpi.super_block_world.main.registry.block;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.block_entity.type.BooLanternBE;
import com.dayofpi.super_block_world.main.common.block_entity.type.QuestionBlockBE;
import com.dayofpi.super_block_world.main.common.block_entity.type.WarpPipeBE;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<QuestionBlockBE> QUESTION_BLOCK;
    public static BlockEntityType<BooLanternBE> BOO_LANTERN;
    public static BlockEntityType<WarpPipeBE> WARP_PIPE;
    public static BlockEntityType<SignBlockEntity> SIGN;

    public static void registerBlockEntities() {
        QUESTION_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_block"), FabricBlockEntityTypeBuilder.create(QuestionBlockBE::new, ModBlocks.QUESTION_BLOCK).build());
        BOO_LANTERN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "boo_lantern"), FabricBlockEntityTypeBuilder.create(BooLanternBE::new, ModBlocks.BOO_LANTERN).build());
        WARP_PIPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "warp_pipe"), FabricBlockEntityTypeBuilder.create(WarpPipeBE::new, ModBlocks.WARP_PIPE).build());
        SIGN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "sign"), FabricBlockEntityTypeBuilder.create(SignBlockEntity::new, ModBlocks.AMANITA_SIGN, ModBlocks.AMANITA_WALL_SIGN, ModBlocks.DARK_AMANITA_SIGN, ModBlocks.DARK_AMANITA_WALL_SIGN).build());
    }
}

package com.dayofpi.sbw_main.block.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.block_entity.BooLanternBE;
import com.dayofpi.sbw_main.block.block_entity.QuestionBlockBE;
import com.dayofpi.sbw_main.block.block_entity.WarpPipeBE;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class ModBlockEntities {

    public static BlockEntityType<QuestionBlockBE> QUESTION_BLOCK;
    public static BlockEntityType<BooLanternBE> BOO_LANTERN;
    public static BlockEntityType<WarpPipeBE> WARP_PIPE;
    public static BlockEntityType<SignBlockEntity> SIGN;
    public static RegistryKey<BlockEntityType<?>> SIGN_BE;

    public static void registerBlockEntities() {
        SIGN_BE = RegistryKey.of(Registry.BLOCK_ENTITY_TYPE_KEY, new Identifier("sign"));
        QUESTION_BLOCK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_block"), FabricBlockEntityTypeBuilder.create(QuestionBlockBE::new, ModBlocks.QUESTION_BLOCK).build());
        BOO_LANTERN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "boo_lantern"), FabricBlockEntityTypeBuilder.create(BooLanternBE::new, ModBlocks.BOO_LANTERN).build());
        WARP_PIPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "warp_pipe"), FabricBlockEntityTypeBuilder.create(WarpPipeBE::new, ModBlocks.WARP_PIPE).build());
        SIGN = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "sign"), FabricBlockEntityTypeBuilder.create(SignBlockEntity::new, ModBlocks.AMANITA_SIGN, ModBlocks.AMANITA_WALL_SIGN, Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN).build());
    }
}

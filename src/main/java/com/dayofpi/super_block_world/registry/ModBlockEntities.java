package com.dayofpi.super_block_world.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.block_entities.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "boo_lantern"), BOO_LANTERN);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "chincho_torch"), CHINCHO_TORCH);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "flag"), FLAG);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "placed_item"), PLACED_ITEM);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_block"), QUESTION_BLOCK);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "question_box"), QUESTION_BOX);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "warp_pipe"), WARP_PIPE);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, "yoshi_egg"), YOSHI_EGG);
    }

    public static final BlockEntityType<BooLanternBE> BOO_LANTERN = FabricBlockEntityTypeBuilder.create(BooLanternBE::new, ModBlocks.BOO_LANTERN).build();
    public static final BlockEntityType<ChinchoTorchBE> CHINCHO_TORCH = FabricBlockEntityTypeBuilder.create(ChinchoTorchBE::new, ModBlocks.CHINCHO_TORCH).build();
    public static final BlockEntityType<FlagBE> FLAG = FabricBlockEntityTypeBuilder.create(FlagBE::new, ModBlocks.WHITE_FLAG, ModBlocks.ORANGE_FLAG, ModBlocks.MAGENTA_FLAG, ModBlocks.LIGHT_BLUE_FLAG, ModBlocks.YELLOW_FLAG, ModBlocks.LIME_FLAG, ModBlocks.PINK_FLAG, ModBlocks.GRAY_FLAG, ModBlocks.LIGHT_GRAY_FLAG, ModBlocks.CYAN_FLAG, ModBlocks.PURPLE_FLAG, ModBlocks.BLUE_FLAG, ModBlocks.BROWN_FLAG, ModBlocks.GREEN_FLAG, ModBlocks.RED_FLAG, ModBlocks.BLACK_FLAG, ModBlocks.RAINBOW_FLAG).build();
    public static final BlockEntityType<PlacedItemBE> PLACED_ITEM = FabricBlockEntityTypeBuilder.create(PlacedItemBE::new, ModBlocks.COIN, ModBlocks.STAR_COIN, ModBlocks.POWER_STAR, ModBlocks.ZTAR).build();
    public static final BlockEntityType<QuestionBlockBE> QUESTION_BLOCK = FabricBlockEntityTypeBuilder.create(QuestionBlockBE::new, ModBlocks.QUESTION_BLOCK).build();
    public static final BlockEntityType<QuestionBoxBE> QUESTION_BOX = FabricBlockEntityTypeBuilder.create(QuestionBoxBE::new, ModBlocks.QUESTION_BOX).build();
    public static final BlockEntityType<WarpPipeBE> WARP_PIPE = FabricBlockEntityTypeBuilder.create(WarpPipeBE::new, ModBlocks.WHITE_WARP_PIPE, ModBlocks.ORANGE_WARP_PIPE, ModBlocks.MAGENTA_WARP_PIPE, ModBlocks.LIGHT_BLUE_WARP_PIPE, ModBlocks.YELLOW_WARP_PIPE, ModBlocks.LIME_WARP_PIPE, ModBlocks.PINK_WARP_PIPE, ModBlocks.GRAY_WARP_PIPE, ModBlocks.LIGHT_GRAY_WARP_PIPE, ModBlocks.CYAN_WARP_PIPE, ModBlocks.PURPLE_WARP_PIPE, ModBlocks.BLUE_WARP_PIPE, ModBlocks.BROWN_WARP_PIPE, ModBlocks.GREEN_WARP_PIPE, ModBlocks.RED_WARP_PIPE, ModBlocks.BLACK_WARP_PIPE).build();
    public static final BlockEntityType<YoshiEggBE> YOSHI_EGG = FabricBlockEntityTypeBuilder.create(YoshiEggBE::new, ModBlocks.YOSHI_EGG).build();
}

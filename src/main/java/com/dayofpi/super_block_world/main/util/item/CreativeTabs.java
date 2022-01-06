package com.dayofpi.super_block_world.main.util.item;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.main.ItemInit;
import com.dayofpi.super_block_world.main.registry.main.BlockInit;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CreativeTabs {
    public static final ItemGroup BLOCK_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "blocks"), () -> new ItemStack(BlockInit.TOADSTOOL_GRASS));
    public static final ItemGroup DECORATION_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "decorations"), () -> new ItemStack(BlockInit.QUESTION_BLOCK));
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "items"), () -> new ItemStack(ItemInit.SUPER_MUSHROOM));
}

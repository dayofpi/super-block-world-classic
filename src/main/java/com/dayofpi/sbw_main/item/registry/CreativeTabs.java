package com.dayofpi.sbw_main.item.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CreativeTabs {
    public static final ItemGroup BLOCK_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "block_group"), () -> new ItemStack(ModBlocks.QUESTION_BLOCK));
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "item_group"), () -> new ItemStack(ModItems.SUPER_MUSHROOM));
}

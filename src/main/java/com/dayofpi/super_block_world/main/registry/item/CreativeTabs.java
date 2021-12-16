package com.dayofpi.super_block_world.main.registry.item;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CreativeTabs {
    public static final ItemGroup BLOCK_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "block_group"), () -> new ItemStack(BlockRegistry.QUESTION_BLOCK));
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "item_group"), () -> new ItemStack(ItemRegistry.SUPER_MUSHROOM));
}

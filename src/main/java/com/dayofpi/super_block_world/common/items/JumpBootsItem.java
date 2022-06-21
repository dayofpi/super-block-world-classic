package com.dayofpi.super_block_world.common.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JumpBootsItem extends ArmorItem {
    public JumpBootsItem(ArmorMaterial material, Settings settings) {
        super(material, EquipmentSlot.FEET, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.super_block_world.super_jump").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("tooltip.super_block_world.stomping").formatted(Formatting.GOLD));
    }
}

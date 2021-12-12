package com.dayofpi.sbw_main.common.item.types;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class JumpBootsItem extends ArmorItem {

    public JumpBootsItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add( new TranslatableText("tooltip.super_block_world.jump").formatted(Formatting.GOLD));
        tooltip.add( new TranslatableText("tooltip.super_block_world.stomp").formatted(Formatting.GOLD));
    }
}

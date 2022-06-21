package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.TooltipUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShellItem extends ArmorItem {
    public static final StatusEffectInstance redShellEffect = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0);

    public ShellItem(ArmorMaterial material, Settings settings) {
        super(material, EquipmentSlot.HEAD, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.super_block_world.brick_breaking").formatted(Formatting.GOLD));
        if (stack.isOf(ModItems.BUZZY_SHELL))
            tooltip.add(Text.translatable("tooltip.super_block_world.projectile_deflection").formatted(Formatting.GOLD));
        if (stack.isOf(ModItems.RED_SHELL))
            TooltipUtil.tooltipFromEffect(tooltip, redShellEffect, 1);
    }
}

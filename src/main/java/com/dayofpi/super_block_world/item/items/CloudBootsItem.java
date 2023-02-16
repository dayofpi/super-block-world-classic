package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.util.TooltipUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CloudBootsItem extends ArmorItem {
    public CloudBootsItem(ArmorMaterial material, Settings settings) {
        super(material, EquipmentSlot.FEET, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        TooltipUtil.tooltipFromEffect(tooltip, new StatusEffectInstance(StatusEffects.SLOW_FALLING), 1);
    }
}

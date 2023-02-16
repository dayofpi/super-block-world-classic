package com.dayofpi.super_block_world.item.materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class BlueShellArmorMaterial implements ArmorMaterial {
    @Override
    public int getDurability(EquipmentSlot slot) {
        return 40;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_TURTLE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.SCUTE);
    }

    @Override
    public String getName() {
        return "blue_shell";
    }

    @Override
    public float getToughness() {
        return 0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0F;
    }
}

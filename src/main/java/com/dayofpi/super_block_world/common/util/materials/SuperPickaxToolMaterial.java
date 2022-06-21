package com.dayofpi.super_block_world.common.util.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class SuperPickaxToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 100;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 24.0F;
    }

    @Override
    public float getAttackDamage() {
        return 5.0F;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}

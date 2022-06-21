package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.registry.more.Materials;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.tag.BlockTags;

public class SuperPickaxItem extends MiningToolItem {
    public SuperPickaxItem(Settings settings) {
        super(4, 6, Materials.SUPER_PICKAX, BlockTags.PICKAXE_MINEABLE, settings);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return this.miningSpeed;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(5, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }
}

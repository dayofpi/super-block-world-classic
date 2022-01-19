package com.dayofpi.super_block_world.common.items.armor;

import com.dayofpi.super_block_world.common.entities.abst.AbstractShell;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
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


public class ShellItem extends ArmorItem {
    private final EntityType<? extends AbstractShell> shellEntity;
    public ShellItem(EntityType<? extends AbstractShell> shellEntity, ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.shellEntity = shellEntity;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add( new TranslatableText("tooltip.super_block_world.brick_break").formatted(Formatting.GOLD));
        if (stack.isOf(ItemInit.BUZZY_SHELL)) {
            tooltip.add(new TranslatableText("tooltip.super_block_world.projectile_deflect").formatted(Formatting.GOLD));
        } else if (stack.isOf(ItemInit.RED_SHELL)) {
            tooltip.add(new TranslatableText("tooltip.super_block_world.fire_resist").formatted(Formatting.BLUE));
        }
    }

    private EntityType<? extends AbstractShell> getShellEntity() {
        return this.shellEntity;
    }
}

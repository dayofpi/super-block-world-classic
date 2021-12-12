package com.dayofpi.sbw_main.common.entity.type;

import com.dayofpi.sbw_main.common.entity.type.bases.AbstractShell;
import com.dayofpi.sbw_main.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BuzzyShellEntity extends AbstractShell {

    public BuzzyShellEntity(EntityType<? extends AbstractShell> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return ModItems.BUZZY_SHELL.getDefaultStack();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.LEFT;
    }
}

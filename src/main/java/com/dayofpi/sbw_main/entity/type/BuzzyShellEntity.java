package com.dayofpi.sbw_main.entity.type;

import com.dayofpi.sbw_main.entity.type.bases.AbstractShell;
import com.dayofpi.sbw_main.item.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class BuzzyShellEntity extends AbstractShell {
    public BuzzyShellEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    public Item asItem() {
        return ModItems.BUZZY_SHELL;
    }
}

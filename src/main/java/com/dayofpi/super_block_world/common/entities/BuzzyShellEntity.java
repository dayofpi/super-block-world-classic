package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.common.entities.abst.AbstractShell;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class BuzzyShellEntity extends AbstractShell {
    public BuzzyShellEntity(EntityType<? extends AbstractShell> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void leaveShell() {
        this.convertTo(EntityInit.BUZZY_BEETLE, true);
    }

    protected Item getShellItem() {
        return ItemInit.BUZZY_SHELL;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasMob", this.hasMob());
        nbt.putBoolean("Shaking", this.isShaking());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setHasMob(nbt.getBoolean("HasMob"));
        this.setShaking(nbt.getBoolean("Shaking"));
        super.readCustomDataFromNbt(nbt);
    }
}

package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractShell;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class BuzzyShellEntity extends AbstractShell {
    public BuzzyShellEntity(EntityType<? extends AbstractShell> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source instanceof ProjectileDamageSource) {
            this.playSound(SoundInit.ENTITY_BUZZY_BLOCK, this.getSoundVolume(), this.getSoundPitch());
            return false;
        } else {
            return super.damage(source, amount);
        }
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

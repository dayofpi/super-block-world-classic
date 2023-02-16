package com.dayofpi.super_block_world.entity.entities.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class BoomBoomEntity extends HostileEntity {
    public BoomBoomEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 16;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }
}

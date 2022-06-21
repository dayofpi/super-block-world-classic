package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.registry.main.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class IceBroIceballEntity extends IceballEntity {
    public IceBroIceballEntity(World world, LivingEntity owner) {
        super(EntityInit.ICE_BRO_ICEBALL, owner, world);
    }

    public IceBroIceballEntity(EntityType<IceBroIceballEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected int slownessDuration() {
        return 30;
    }
}

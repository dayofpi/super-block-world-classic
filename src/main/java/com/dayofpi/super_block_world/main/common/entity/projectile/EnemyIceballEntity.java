package com.dayofpi.super_block_world.main.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EnemyIceballEntity extends IceballEntity{
    public EnemyIceballEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public EnemyIceballEntity(EntityType<EnemyIceballEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected int slownessDuration() {
        return 30;
    }
}

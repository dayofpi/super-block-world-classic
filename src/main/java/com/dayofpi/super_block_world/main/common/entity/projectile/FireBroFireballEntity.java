package com.dayofpi.super_block_world.main.common.entity.projectile;

import com.dayofpi.super_block_world.main.registry.main.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class FireBroFireballEntity extends FireballEntity {
    public FireBroFireballEntity(World world, LivingEntity owner) {
        super(EntityInit.FIRE_BRO_FIREBALL, owner, world);
    }

    public FireBroFireballEntity(EntityType<FireBroFireballEntity> fireBroFireballEntity, World world) {
        super(fireBroFireballEntity, world);
    }
}

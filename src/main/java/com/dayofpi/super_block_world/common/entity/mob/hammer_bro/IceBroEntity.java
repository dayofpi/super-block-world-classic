package com.dayofpi.super_block_world.common.entity.mob.hammer_bro;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entity.mob.EnemyEntity;
import com.dayofpi.super_block_world.common.entity.projectile.IceBroIceballEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class IceBroEntity extends AbstractBro {
    public IceBroEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        IceBroIceballEntity iceballEntity = new IceBroIceballEntity(world, this);
        iceballEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 1.0F, 0.0F);
        this.playSound(SoundInit.ITEM_ICE_FLOWER, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(iceballEntity);
        this.swingHand(Hand.MAIN_HAND);
    }
}

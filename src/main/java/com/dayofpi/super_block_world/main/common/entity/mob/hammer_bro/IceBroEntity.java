package com.dayofpi.super_block_world.main.common.entity.mob.hammer_bro;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.EnemyEntity;
import com.dayofpi.super_block_world.main.common.entity.projectile.EnemyIceballEntity;
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
        EnemyIceballEntity iceballEntity = new EnemyIceballEntity(world, this);
        iceballEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 1.0F, 0.0F);
        this.playSound(ModSounds.ITEM_ICE_FLOWER_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(iceballEntity);
        this.swingHand(Hand.MAIN_HAND);
    }
}

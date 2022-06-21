package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBro;
import com.dayofpi.super_block_world.common.entities.IceBroIceballEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class IceBroEntity extends AbstractBro {
    public IceBroEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
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

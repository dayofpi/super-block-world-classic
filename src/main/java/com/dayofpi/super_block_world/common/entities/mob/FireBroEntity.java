package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBro;
import com.dayofpi.super_block_world.common.entities.FireBroFireballEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireBroEntity extends AbstractBro {
    public FireBroEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        FireBroFireballEntity fireballEntity = new FireBroFireballEntity(world, this);
        fireballEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 1.0F, 0.0F);
        this.playSound(SoundInit.ITEM_FIRE_FLOWER, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(fireballEntity);
        this.swingHand(Hand.MAIN_HAND);
    }
}

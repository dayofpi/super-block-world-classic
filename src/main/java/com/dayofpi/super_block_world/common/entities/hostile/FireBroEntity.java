package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.projectile.ModFireballEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireBroEntity extends AbstractBro {
    public FireBroEntity(EntityType<? extends AbstractBro> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ModFireballEntity fireballEntity = new ModFireballEntity(ModEntities.FIREBALL, this, world);
        fireballEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 1.0F, 0.0F);
        this.playSound(Sounds.ITEM_FIRE_FLOWER, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(fireballEntity);
        this.swingHand(Hand.MAIN_HAND);
    }
}

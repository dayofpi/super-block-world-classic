package com.dayofpi.super_block_world.main.common.entity.type.mobs;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.type.bases.AbstractBro;
import com.dayofpi.super_block_world.main.common.entity.type.bases.EnemyEntity;
import com.dayofpi.super_block_world.main.common.entity.type.projectiles.EnemyFireballEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireBroEntity extends AbstractBro {
    public FireBroEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        EnemyFireballEntity fireballEntity = new EnemyFireballEntity(world, this);
        fireballEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 1.0F, 0.0F);
        this.playSound(ModSounds.ITEM_FIRE_FLOWER_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(fireballEntity);
        this.swingHand(Hand.MAIN_HAND);
    }
}

package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class TurnipEntity extends ThrownItemEntity {
    public TurnipEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public TurnipEntity(World world, LivingEntity owner) {
        super(ModEntities.TURNIP, owner, world);
    }

    public TurnipEntity(World world, double x, double y, double z) {
        super(ModEntities.TURNIP, x, y, z, world);
    }

    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack());
    }

    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status != 3)
            return;
        ParticleEffect particleEffect = this.getParticleParameters();
        for (int i = 0; i < 4; ++i) {
            this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);
        this.playSound(Sounds.ENTITY_PROJECTILE_HIT, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.TURNIP;
    }

    protected float getGravity() {
        return 0.06F;
    }
}

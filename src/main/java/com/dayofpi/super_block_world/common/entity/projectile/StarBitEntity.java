package com.dayofpi.super_block_world.common.entity.projectile;

import com.dayofpi.super_block_world.Client;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.other.ParticleInit;
import com.dayofpi.super_block_world.common.util.entity.CustomSpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class StarBitEntity extends ThrownItemEntity {
    public StarBitEntity(EntityType<? extends StarBitEntity> entityType, World world) {
        super(entityType, world);
    }

    public StarBitEntity(World world, LivingEntity owner) {
        super(EntityInit.STAR_BIT, owner, world);
    }

    public StarBitEntity(World world, double x, double y, double z) {
        super(EntityInit.STAR_BIT, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ItemInit.PURPLE_STAR_BIT;
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; ++i) {
                world.addParticle(ParticleInit.STAR_BIT, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public void tick() {
        super.tick();
        if (this.random.nextFloat() > 0.5F) {
            world.addParticle(ParticleInit.STAR_BIT, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
        }
    }

    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack());
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT   , SoundCategory.NEUTRAL, 1.0F, 1.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 1);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return CustomSpawnPacket.create(this, Client.PacketID);
    }

    protected float getGravity() {
        return 0.01f;
    }
}

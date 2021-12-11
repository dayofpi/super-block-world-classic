package com.dayofpi.sbw_main.entity.type.projectiles;

import com.dayofpi.sbw_main.Client;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.ModSpawnPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class BombEntity extends ThrownItemEntity {
    public BombEntity(EntityType<? extends BombEntity> entityType, World world) {
        super(entityType, world);
    }

    public BombEntity(World world, double x, double y, double z) {
        super(ModEntities.BOMB, x, y, z, world);
    }

    public BombEntity(World world, LivingEntity owner) {
        super(ModEntities.BOMB, owner, world);
    }

    protected Item getDefaultItem() {
        return ModItems.BOMB;
    }

    public void tick() {
        super.tick();
        if (this.world.isClient) {
            world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack());
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if (hitResult.getType() == HitResult.Type.BLOCK || hitResult.getType() == HitResult.Type.ENTITY) {
                world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0F, Explosion.DestructionType.BREAK);
                this.world.sendEntityStatus(this, (byte) 3);
                this.discard();
            }
        }

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return ModSpawnPacket.create(this, Client.PacketID);
    }

    protected float getGravity() {
        return 0.06F;
    }
}

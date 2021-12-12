package com.dayofpi.sbw_main.common.entity.type.projectiles;

import com.dayofpi.sbw_main.Client;
import com.dayofpi.sbw_main.registry.entity.ModEntities;
import com.dayofpi.sbw_main.registry.ModItems;
import com.dayofpi.sbw_main.util.entity.CustomSpawnPacket;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TurnipEntity extends ThrownItemEntity {
    public TurnipEntity(EntityType<? extends TurnipEntity> entityType, World world) {
        super(entityType, world);
    }

    public TurnipEntity(World world, LivingEntity owner) {
        super(ModEntities.TURNIP, owner, world);
    }

    public TurnipEntity(World world, double x, double y, double z) {
        super(ModEntities.TURNIP, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ModItems.TURNIP;
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
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                if (world.getBlockState(blockPos.up()).getBlock().getHardness() == 0.0F) {
                    world.breakBlock(blockPos.up(), true);
                }
            }
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return CustomSpawnPacket.create(this, Client.PacketID);
    }
}

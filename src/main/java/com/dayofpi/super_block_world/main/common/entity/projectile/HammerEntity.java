package com.dayofpi.super_block_world.main.common.entity.projectile;

import com.dayofpi.super_block_world.main.Client;
import com.dayofpi.super_block_world.main.registry.general.TagRegistry;
import com.dayofpi.super_block_world.main.registry.general.EntityRegistry;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import com.dayofpi.super_block_world.main.util.entity.CustomSpawnPacket;
import net.minecraft.block.BlockState;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HammerEntity extends ThrownItemEntity {
    public HammerEntity(EntityType<? extends HammerEntity> entityType, World world) {
        super(entityType, world);
    }

    public HammerEntity(World world, LivingEntity owner) {
        super(EntityRegistry.HAMMER, owner, world);
    }

    public HammerEntity(World world, double x, double y, double z) {
        super(EntityRegistry.HAMMER, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.HAMMER;
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
                this.playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0F, 0.8F);
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isIn(TagRegistry.BRICKS)) {
                    world.breakBlock(blockPos, true);
                }
            }
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        this.playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0F, 0.8F);
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 6);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return CustomSpawnPacket.create(this, Client.PacketID);
    }

    protected float getGravity() {
        return 0.1F;
    }
}

package com.dayofpi.sbw_main.entity.type.projectiles;

import com.dayofpi.sbw_main.Client;
import com.dayofpi.sbw_main.ModTags;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.ModSpawnPacket;
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
        super(ModEntities.HAMMER, owner, world);
    }

    public HammerEntity(World world, double x, double y, double z) {
        super(ModEntities.HAMMER, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ModItems.HAMMER;
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
                if (blockState.isIn(ModTags.BRICKS)) {
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
        return ModSpawnPacket.create(this, Client.PacketID);
    }

    protected float getGravity() {
        return 0.1F;
    }
}

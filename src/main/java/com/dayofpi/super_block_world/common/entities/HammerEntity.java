package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.common.util.entity.CustomSpawnPacket;
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
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class HammerEntity extends ThrownItemEntity {
    public HammerEntity(EntityType<? extends HammerEntity> entityType, World world) {
        super(entityType, world);
    }

    public HammerEntity(World world, LivingEntity owner) {
        super(EntityInit.HAMMER, owner, world);
    }

    public HammerEntity(World world, double x, double y, double z) {
        super(EntityInit.HAMMER, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ItemInit.HAMMER;
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
            this.playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0F, 0.8F);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isIn(TagInit.BRICKS)) {
                    if (this.getOwner() == null || this.getOwner() != null && this.getOwner().isPlayer() || this.getOwner() != null && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING))
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
        return CustomSpawnPacket.create(this, Main.PacketID);
    }

    protected float getGravity() {
        return 0.1F;
    }
}

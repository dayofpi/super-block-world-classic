package com.dayofpi.super_block_world.entity.entities.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class FlowerProjectileEntity extends PersistentProjectileEntity {
    protected int hops = 5;

    protected FlowerProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected FlowerProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
        this(type, world);
        this.setPosition(x, y, z);
        this.setShotFromCrossbow(false);
        this.setCritical(false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient) {
            if (this.random.nextFloat() > 0.5F) {
                world.addParticle(this.getParticle(), this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
            }
        } else if (this.inGround && random.nextInt(100) == 0) {
            this.discard();
        }
    }

    public boolean isFromEnemy() {
        return this.getOwner() instanceof HostileEntity;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    protected abstract ParticleEffect getParticle();

    protected abstract SoundEvent getBounceSound();

    protected void discardProjectile() {
        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState hitState = this.world.getBlockState(blockHitResult.getBlockPos());
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        Vec3d normalized = vec3d.normalize().multiply(0.05D);

        hitState.onProjectileHit(this.world, hitState, blockHitResult, this);
        this.setVelocity(vec3d.multiply(1.4));
        this.setPos(this.getX() - normalized.x, this.getY() - normalized.y, this.getZ() - normalized.z);
        this.playSound(this.getBounceSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.hops -= 1;

        if (blockHitResult.getSide() == Direction.UP) {
            this.setVelocity(this.getVelocity().add(0, 0.3, 0));
        } else if (blockHitResult.getSide() == Direction.DOWN) {
            this.setVelocity(this.getVelocity().subtract(0, 0.3, 0));
        } else {
            this.setVelocity(this.getVelocity().multiply(-1.0D));
        }
        this.setYaw(this.getYaw() + 180.0F);
        this.prevYaw += 180.0F;
        if (hops <= 0) {
            this.discardProjectile();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.discard();
        this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }
}

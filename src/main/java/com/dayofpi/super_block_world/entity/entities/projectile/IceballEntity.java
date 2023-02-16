package com.dayofpi.super_block_world.entity.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class IceballEntity extends FlowerProjectileEntity {
    public IceballEntity(EntityType<? extends IceballEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceballEntity(EntityType<? extends IceballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.1D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
    }

    public IceballEntity(World world, double x, double y, double z) {
        super(ModEntities.ICEBALL, x, y, z, world);
    }

    public IceballEntity(EntityType<? extends IceballEntity> entityType, double x, double v, double z, World world) {
        super(entityType, x, v, z, world);
        this.setPosition(x, v, z);
    }

    @Override
    protected SoundEvent getHitSound() {
        return Sounds.ENTITY_ICEBALL_HIT;
    }

    @Override
    protected void discardProjectile() {
        super.discardProjectile();
        this.playSound(Sounds.ENTITY_ICEBALL_FREEZE, 0.5F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    public void tick() {
        super.tick();
        BlockState blockState = world.getBlockState(this.getBlockPos());
        if (blockState.isOf(Blocks.WATER) && world.getBlockState(this.getBlockPos().up()).isAir()) {
            this.freezeAndDiscard(Blocks.FROSTED_ICE);
            return;
        }

        if (blockState.isOf(Blocks.LAVA) && world.getBlockState(this.getBlockPos().up()).isAir()) {
            this.freezeAndDiscard(Blocks.MAGMA_BLOCK);
            return;
        }

        if (blockState.isOf(ModBlocks.MUNCHER)) {
            this.freezeAndDiscard(ModBlocks.FROZEN_MUNCHER);
        }
    }

    private void freezeAndDiscard(Block frozenMuncher) {
        this.playSound(Sounds.ENTITY_ICEBALL_FREEZE, 1.0F, 1.0F + this.random.nextFloat());
        world.setBlockState(this.getBlockPos(), frozenMuncher.getDefaultState());
        this.discard();
    }

    @Override
    protected ParticleEffect getParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected SoundEvent getBounceSound() {
        return Sounds.ENTITY_ICEBALL_BOUNCE;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10);
        } else entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 5);

        if (entity instanceof LivingEntity && entity.canFreeze()) {
            entity.world.getProfiler().push("freezing");
            entity.setFrozenTicks(400);
            entity.world.getProfiler().pop();
        }
        entity.extinguish();
    }
}

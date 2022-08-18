package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class GoldFireballEntity extends FlowerProjectileEntity {
    public GoldFireballEntity(EntityType<? extends GoldFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public GoldFireballEntity(EntityType<? extends GoldFireballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.1D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
    }

    public GoldFireballEntity(EntityType<? extends GoldFireballEntity> entityType, double x, double v, double z, World world) {
        super(entityType, x, v, z, world);
    }

    public GoldFireballEntity(World world, double x, double y, double z) {
        super(ModEntities.GOLD_FIREBALL, x, y, z, world);
    }

    @Override
    protected SoundEvent getHitSound() {
        return Sounds.ENTITY_FIREBALL_HIT;
    }

    @Override
    protected void discardProjectile() {
        super.discardProjectile();
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, Box.from(Vec3d.ofCenter(this.getBlockPos())), livingEntity -> this.getOwner() != livingEntity);
        for (LivingEntity livingEntity : list) {
            livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10);
        }
        this.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockState hitState = this.world.getBlockState(blockHitResult.getBlockPos());
        if (hitState.isOf(Blocks.OBSIDIAN)) {
            if (world.getBlockState(blockHitResult.getBlockPos().up()).isAir()) {
                world.setBlockState(blockHitResult.getBlockPos().up(), Blocks.FIRE.getDefaultState());
                this.discard();
            }
        } else if (hitState.isIn(BlockTags.ICE)) {
            world.setBlockState(blockHitResult.getBlockPos(), Blocks.WATER.getDefaultState());
            world.updateNeighbor(blockHitResult.getBlockPos(), Blocks.WATER, blockHitResult.getBlockPos());
        }
    }

    @Override
    protected ParticleEffect getParticle() {
        return ParticleTypes.WAX_ON;
    }

    @Override
    protected SoundEvent getBounceSound() {
        return Sounds.ENTITY_FIREBALL_BOUNCE;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10);
        if (entity.isFrozen()) entity.setFrozenTicks(0);
        if (entity instanceof LivingEntity && ((LivingEntity) entity).isDead())
            entity.dropItem(ModItems.COIN);
        for (int i = 0; i < 4; i++) {
            double rand = this.random.nextBoolean() ? 0.02 : -0.02;
            world.addParticle(ParticleTypes.WAX_ON, this.getX(), this.getY(), this.getZ(), i * rand, 0.02D, i * rand);
        }
    }
}

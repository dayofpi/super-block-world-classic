package com.dayofpi.super_block_world.entity.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ModFireballEntity extends FlowerProjectileEntity {
    public ModFireballEntity(EntityType<? extends ModFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public ModFireballEntity(EntityType<? extends ModFireballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.1D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
    }

    public ModFireballEntity(EntityType<? extends ModFireballEntity> entityType, double x, double v, double z, World world) {
        super(entityType, x, v, z, world);
    }

    public ModFireballEntity(World world, double x, double y, double z) {
        super(ModEntities.FIREBALL, x, y, z, world);
    }

    @Override
    protected SoundEvent getHitSound() {
        return Sounds.ENTITY_FIREBALL_HIT;
    }

    @Override
    protected void discardProjectile() {
        super.discardProjectile();
        BlockState blockState = world.getBlockState(this.getBlockPos());
        BlockPos floor = this.getBlockPos().down();
        if (blockState.isAir() && world.getBlockState(floor).isSideSolidFullSquare(world, this.getBlockPos(), Direction.UP)) {
            world.setBlockState(this.getBlockPos(), Blocks.FIRE.getDefaultState(), Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
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
        return ParticleTypes.FLAME;
    }

    @Override
    protected SoundEvent getBounceSound() {
        return Sounds.ENTITY_FIREBALL_BOUNCE;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!entity.isFireImmune()) {
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 5);
            if (!entity.isOnFire()) entity.setOnFireFor(4);
            if (entity.isFrozen()) entity.setFrozenTicks(0);
            for (int i = 0; i < 4; i++) {
                double rand = this.random.nextBoolean() ? 0.02 : -0.02;
                world.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), i * rand, 0.02D, i * rand);
            }
        }
    }
}

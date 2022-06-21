package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceballEntity extends PersistentProjectileEntity {
    private int hops = 5;

    public IceballEntity(EntityType<? extends IceballEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceballEntity(EntityType<? extends IceballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.1D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
        this.setShotFromCrossbow(false);
        this.setCritical(false);
    }

    public IceballEntity(World world, double x, double y, double z) {
        super(ModEntities.ICEBALL, x, y, z, world);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    public IceballEntity(EntityType<? extends IceballEntity> entityType, double x, double v, double z, World world) {
        super(entityType, x, v, z, world);
        this.setPosition(x, v, z);
    }

    @Override
    protected SoundEvent getHitSound() {
        return Sounds.ENTITY_ICEBALL_BOUNCE;
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState hitState = this.world.getBlockState(blockHitResult.getBlockPos());
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05D);

        hitState.onProjectileHit(this.world, hitState, blockHitResult, this);
        this.setVelocity(vec3d.multiply(1.4));
        this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.shake = 7;
        this.hops -= 1;

        if (blockHitResult.getSide() == Direction.UP) {
            this.setVelocity(this.getVelocity().add(0, 0.3 ,0));
        } else if (blockHitResult.getSide() == Direction.DOWN) {
            this.setVelocity(this.getVelocity().subtract(0, 0.3 ,0));
        } else {
            this.setVelocity(this.getVelocity().multiply(-1.0D));
        }
        this.setYaw(this.getYaw() + 180.0F);
        this.prevYaw += 180.0F;

        if (hops <= 0) {
            this.playSound(Sounds.ENTITY_ICEBALL_FREEZE, 0.5F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        }
    }

    public void tick() {
        super.tick();
        if (this.inGround && random.nextInt(100) == 0) {
            this.discard();
        }

        if ((world.getBlockState(this.getBlockPos()).isOf(Blocks.WATER))) {
            this.playSound(Sounds.ENTITY_ICEBALL_FREEZE, 1.0F, 1.0F + this.random.nextFloat());
            world.setBlockState(this.getBlockPos(), Blocks.FROSTED_ICE.getDefaultState());
            this.discard();
        }

        if ((world.getBlockState(this.getBlockPos()).isOf(ModBlocks.MUNCHER))) {
            this.playSound(Sounds.ENTITY_ICEBALL_FREEZE, 1.0F, 1.0F + this.random.nextFloat());
            world.setBlockState(this.getBlockPos(), ModBlocks.FROZEN_MUNCHER.getDefaultState());
            this.discard();
        }

        if (this.random.nextFloat() > 0.5F) {
            world.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        this.discard();
        if (entity.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10);
        } else
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);

        if (entity instanceof LivingEntity && entity.canFreeze()) {
            this.playSound(Sounds.ENTITY_ICEBALL_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            entity.world.getProfiler().push("freezing");
            entity.setFrozenTicks(400);
            entity.world.getProfiler().pop();
        }
        entity.extinguish();
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}

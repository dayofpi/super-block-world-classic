package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.block.Block;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModFireballEntity extends PersistentProjectileEntity {
    private int hops = 5;

    public ModFireballEntity(EntityType<? extends ModFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public ModFireballEntity(EntityType<? extends ModFireballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.1D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
        this.setShotFromCrossbow(false);
        this.setCritical(false);
    }

    public ModFireballEntity(EntityType<? extends ModFireballEntity> entityType, double x, double v, double z, World world) {
        super(entityType, x, v, z, world);
        this.setPosition(x, v, z);
    }

    public ModFireballEntity(World world, double x, double y, double z) {
        super(ModEntities.FIREBALL, x, y, z, world);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    @Override
    protected SoundEvent getHitSound() {
        return Sounds.ENTITY_FIREBALL_BOUNCE;
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
            this.setVelocity(this.getVelocity().add(0, 0.3, 0));
        } else if (blockHitResult.getSide() == Direction.DOWN) {
            this.setVelocity(this.getVelocity().subtract(0, 0.3, 0));
        } else {
            this.setVelocity(this.getVelocity().multiply(-1.0D));
        }
        this.setYaw(this.getYaw() + 180.0F);
        this.prevYaw += 180.0F;

        BlockState blockState = world.getBlockState(this.getBlockPos());
        BlockPos floor = this.getBlockPos().down();

        if (hitState.isOf(Blocks.OBSIDIAN)) {
            if (world.getBlockState(blockHitResult.getBlockPos().up()).isAir()) {
                world.setBlockState(blockHitResult.getBlockPos().up(), Blocks.FIRE.getDefaultState());
                this.discard();
            }
        } else if (hitState.isIn(BlockTags.ICE)) {
            world.setBlockState(blockHitResult.getBlockPos(), Blocks.WATER.getDefaultState());
            world.updateNeighbor(blockHitResult.getBlockPos(), Blocks.WATER, blockHitResult.getBlockPos());
        }

        if (hops <= 0) {
            this.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
            if (blockState.isAir() && world.getBlockState(floor).isSideSolidFullSquare(world, this.getBlockPos(), Direction.UP)) {
                world.setBlockState(this.getBlockPos(), Blocks.FIRE.getDefaultState(), Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
            }
        }
    }

    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            if (this.inGround && random.nextInt(100) == 0) {
                this.discard();
            }
        } else {
            if (this.random.nextFloat() > 0.5F) {
                world.addParticle(ParticleTypes.FLAME, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        this.discard();
        if (!entity.isFireImmune()) {
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);
            if (random.nextInt(5) == 0 && !entity.isOnFire())
                entity.setOnFireFor(1);
            if (entity.isFrozen())
                entity.setFrozenTicks(0);
            for (int i = 0; i < 4; i++) {
                double rand = this.random.nextBoolean() ? 0.02 : -0.02;
                world.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), i * rand, 0.02D, i * rand);
            }
        }
        this.playSound(Sounds.ENTITY_FIREBALL_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}

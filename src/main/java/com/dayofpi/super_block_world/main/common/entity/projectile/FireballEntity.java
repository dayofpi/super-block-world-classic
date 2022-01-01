package com.dayofpi.super_block_world.main.common.entity.projectile;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.Client;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.misc.EntityRegistry;
import com.dayofpi.super_block_world.main.util.entity.CustomSpawnPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireballEntity extends PersistentProjectileEntity {
    private int hops = 5;
    public FireballEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public FireballEntity(EntityType<? extends FireballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.10000000149011612D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
        this.setShotFromCrossbow(false);
        this.setCritical(false);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    protected SoundEvent getHitSound() {
        return ModSounds.ENTITY_FIREBALL_BOUNCE;
    }

    public FireballEntity(EntityType<? extends FireballEntity> entityType, double x, double v, double z, World world) {
        super(entityType, x, v, z, world);
        this.setPosition(x, v, z);
    }

    public FireballEntity(World world, double x, double y, double z) {
        super(EntityRegistry.FIREBALL, x, y, z, world);
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState hitState = this.world.getBlockState(blockHitResult.getBlockPos());
        hitState.onProjectileHit(this.world, hitState, blockHitResult, this);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d.multiply(1.4));
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806D);
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

        BlockState blockState = world.getBlockState(this.getBlockPos());
        BlockPos floor = this.getBlockPos().down();

        if (blockState.isAir() && (world.getBlockState(floor).isOf(BlockRegistry.WARP_FRAME) || world.getBlockState(floor).isOf(Blocks.OBSIDIAN))) {
            this.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            world.setBlockState(this.getBlockPos(), Blocks.FIRE.getDefaultState(), Block.NOTIFY_LISTENERS);
            this.discard();
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
            for (int i = 0; i < 4; i++) {
                double rand = this.random.nextBoolean() ? 0.02 : -0.02;
                world.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), i * rand, 0.02D, i * rand);
            }        }
        if (entity instanceof LivingEntity livingEntity) {
            this.playSound(ModSounds.ENTITY_FIREBALL_HIT, 1.0F, livingEntity.getSoundPitch());
        } else
            this.playSound(ModSounds.ENTITY_FIREBALL_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return CustomSpawnPacket.create(this, Client.PacketID);
    }
}

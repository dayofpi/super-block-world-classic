package com.dayofpi.sbw_main.entity.type.projectiles;

import com.dayofpi.sbw_main.Client;
import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.misc.SpawnPacket;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnemyFireballEntity extends PersistentProjectileEntity {
    private int hops = 5;
    public EnemyFireballEntity(EntityType<? extends EnemyFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public EnemyFireballEntity(World world, LivingEntity owner) {
        super(ModEntities.ENEMY_FIREBALL, owner, world);
    }

    public EnemyFireballEntity(EntityType<? extends EnemyFireballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.10000000149011612D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    protected SoundEvent getHitSound() {
        return ModSounds.ENTITY_FIREBALL_BOUNCE;
    }

    public EnemyFireballEntity(EntityType<? extends EnemyFireballEntity> entityType, double x, double v, double z, World world) {
        super(ModEntities.ENEMY_FIREBALL, x, v, z, world);
        this.setPosition(x, v, z);
    }

    public EnemyFireballEntity(World world, double x, double y, double z) {
        super(ModEntities.FIREBALL, x, y, z, world);
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState blockState = this.world.getBlockState(blockHitResult.getBlockPos());
        blockState.onProjectileHit(this.world, blockState, blockHitResult, this);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d.multiply(1.4));
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806D);
        this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.shake = 7;
        this.hops -= 1;
        this.setCritical(false);
        this.setPierceLevel((byte)0);
        this.setShotFromCrossbow(false);
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
            this.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        }

        if (blockState.isOf(ModBlocks.WARP_FRAME)) {
            this.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
            if (world.getBlockState(this.getBlockPos()).isAir() && world.getBlockState(this.getBlockPos().down()).isSideSolidFullSquare(world, this.getBlockPos(), Direction.UP)) {
                world.setBlockState(this.getBlockPos(), Blocks.FIRE.getDefaultState());
            }
        }
    }

    public void tick() {
        super.tick();
        if (this.inGround && random.nextInt(100) == 0) {
            this.discard();
        }
        if (this.random.nextFloat() > 0.5F) {
            world.addParticle(ParticleTypes.FLAME, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        this.discard();
        if (!entity.isFireImmune()) {
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);
        }
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
        return SpawnPacket.create(this, Client.PacketID);
    }
}

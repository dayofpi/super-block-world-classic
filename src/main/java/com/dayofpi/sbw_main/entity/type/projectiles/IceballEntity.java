package com.dayofpi.sbw_main.entity.type.projectiles;

import com.dayofpi.sbw_main.Client;
import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.misc.SpawnPacket;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class IceballEntity extends PersistentProjectileEntity {
    private int hops = 5;
    public IceballEntity(EntityType<? extends IceballEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceballEntity(EntityType<? extends IceballEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getX(), livingEntity.getEyeY() - 0.10000000149011612D, livingEntity.getZ(), world);
        this.setOwner(livingEntity);
        if (livingEntity instanceof PlayerEntity) {
            this.pickupType = PickupPermission.ALLOWED;
        }
    }

    public IceballEntity(World world, double x, double y, double z) {
        super(ModEntities.ICEBALL, x, y, z, world);
    }

    protected SoundEvent getHitSound() {
        return ModSounds.ENTITY_ICEBALL_BOUNCE;
    }

    public IceballEntity(EntityType<? extends IceballEntity> entityType, double x, double v, double z, World world) {
        super(ModEntities.ICEBALL, x, v, z, world);
        this.setPosition(x, v, z);
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
            this.playSound(ModSounds.ENTITY_ICEBALL_FREEZE, 0.5F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        }
    }

    public void tick() {
        super.tick();
        if (this.inGround && random.nextInt(100) == 0) {
            this.discard();
        }

        if ((world.getBlockState(this.getBlockPos()).isOf(Blocks.WATER))) {
            this.playSound(ModSounds.ENTITY_ICEBALL_FREEZE, 1.0F, 1.0F + this.random.nextFloat());
            world.setBlockState(this.getBlockPos(), Blocks.FROSTED_ICE.getDefaultState());
            this.discard();
        }

        if ((world.getBlockState(this.getBlockPos()).isOf(PlantBlocks.MUNCHER))) {
            this.playSound(ModSounds.ENTITY_ICEBALL_FREEZE, 1.0F, 1.0F + this.random.nextFloat());
            world.setBlockState(this.getBlockPos(), PlantBlocks.FROZEN_MUNCHER.getDefaultState());
            this.discard();
        }

        if (this.random.nextFloat() > 0.5F) {
            world.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        this.discard();
        if (entity instanceof MagmaCubeEntity || entity instanceof BlazeEntity || entity instanceof StriderEntity) {
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10);
        } else
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);

        if (entity instanceof LivingEntity && !(entity instanceof SnowGolemEntity) && !(entity instanceof StrayEntity)) {
            this.playSound(ModSounds.ENTITY_ICEBALL_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 6));
            ParticleUtil.spawnParticle(world, getBlockPos(), ParticleTypes.FLAME, UniformIntProvider.create(2,3));
        }
        entity.extinguish();
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

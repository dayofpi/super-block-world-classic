package com.dayofpi.super_block_world.common.entities.abst;

import com.dayofpi.super_block_world.common.entities.mob.BooEntity;
import com.dayofpi.super_block_world.common.entities.mob.SpiritEntity;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.more.DamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("unused")
public abstract class AbstractBoo extends AnimalEntity {
    public final static int lightLimit = 10;

    public AbstractBoo(EntityType<? extends AbstractBoo> entityType, World world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<BooEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark((ServerWorldAccess) world, pos, random) && world.getBlockState(pos.down()).isIn(BlockTags.DIRT) || (spawnReason == SpawnReason.STRUCTURE);
    }

    public static boolean isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int i = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
            return i <= random.nextInt(8);
        }
    }

    public void tickMovement(){
        super.tickMovement();
        if (this.isAlive()) {
            final float lightLevel = world.getLightLevel(LightType.BLOCK, this.getBlockPos());
            if (lightLevel > lightLimit) {
                if (this instanceof BooEntity booEntity) {
                    if (!booEntity.isTamed()) {
                        this.damage(DamageSource.light(), 3.0F);
                    }
                } else this.damage(DamageSource.light(), 3.0F);
            }
        }
    }

    public void onDeath(net.minecraft.entity.damage.DamageSource source) {
        if (!world.isClient) {
            SpiritEntity spiritEntity = new SpiritEntity(EntityInit.SPIRIT, world, this.getX(), this.getY(), this.getZ());
            world.spawnEntity(spiritEntity);
        }
        super.onDeath(source);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public boolean damage(net.minecraft.entity.damage.DamageSource source, float amount) {
        if (source instanceof DamageSource modDamageSource) {
            if (modDamageSource.isLight()) {
                if (this.world.isClient) {
                    world.addParticle(ParticleTypes.GLOW, this.getX() + random.nextFloat(), this.getY() +  random.nextFloat(), this.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
                this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.0F, 1.0F);
                return super.damage(source, amount);
            } else return false;
        } else if (source.isMagic() || source.isOutOfWorld()) {
            return super.damage(source, amount);
        } else return false;
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    public boolean isInAir() {
        return !this.onGround;
    }

    protected int getNextAirUnderwater(int air) {
        return air;
    }

    protected void swimUpward(Tag<Fluid> fluid) {
        this.setVelocity(this.getVelocity().add(0.0D, 0.01D, 0.0D));
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, net.minecraft.entity.damage.DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }
}

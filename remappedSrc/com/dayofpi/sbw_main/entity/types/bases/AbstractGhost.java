package com.dayofpi.sbw_main.entity.types.bases;

import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.entity.types.SpiritEntity;
import com.dayofpi.sbw_main.entity.types.mobs.BooEntity;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
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

public abstract class AbstractGhost extends AnimalEntity {
    public final static int lightLimit = 10;

    public AbstractGhost(EntityType<? extends AbstractGhost> entityType, World world) {
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
            final float f = world.getLightLevel(LightType.BLOCK, this.getBlockPos());
            if (f > lightLimit) {
                if (this instanceof BooEntity booEntity) {
                    if (!booEntity.isTamed()) {
                        this.damage(ModDamageSource.light(), 3.0F);
                    }
                } else this.damage(ModDamageSource.light(), 3.0F);
            }
        }
    }

    public void onDeath(DamageSource source) {
        if (!world.isClient) {
            SpiritEntity spiritEntity = new SpiritEntity(ModEntities.SPIRIT, world, this.getX(), this.getY(), this.getZ());
            world.spawnEntity(spiritEntity);
        }
        super.onDeath(source);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public boolean damage(DamageSource source, float amount) {
        if (source instanceof ModDamageSource modDamageSource) {
            if (modDamageSource.isLight()) {
                if (this.world.isClient) {
                    world.addParticle(ParticleTypes.GLOW, this.getX() + random.nextFloat(), this.getY() +  random.nextFloat(), this.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
                this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.0F, 1.0F);
                return super.damage(source, amount);
            } else return false;
        } else if (source.isMagic()) {
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

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }
}
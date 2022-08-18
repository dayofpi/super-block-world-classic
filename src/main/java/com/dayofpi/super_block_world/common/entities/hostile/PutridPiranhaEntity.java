package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PutridPiranhaEntity extends PiranhaPlantEntity {
    private static final TrackedData<Integer> POISON_TIME;
    public final AnimationState poisoningAnimationState = new AnimationState();

    static {
        POISON_TIME = DataTracker.registerData(PutridPiranhaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public PutridPiranhaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends PutridPiranhaEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return !world.isSkyVisible(blockPos) && !(world.getLightLevel(LightType.BLOCK, blockPos) > 0);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAttacking() ? Sounds.ENTITY_PIRANHA_PLANT_BITE : Sounds.ENTITY_PUTRID_PIRANHA_SLEEP;
    }

    @Override
    protected void tickAnimation() {
        if (this.isAttacking()) {
            if (this.getPoisonTime() > 200) {
                this.poisoningAnimationState.startIfNotRunning(this.age);
                this.bitingAnimationState.stop();
            }
            else {
                this.bitingAnimationState.startIfNotRunning(this.age);
            }
        }
        else {
            this.bitingAnimationState.stop();
            this.poisoningAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAlive())
            return;
        LivingEntity target = this.getTarget();
        if (this.getPoisonTime() > 0)
            this.setPoisonTime(this.getPoisonTime() - 1);
        if (target != null && this.distanceTo(target) < 3 && this.getPoisonTime() == 0 && !this.world.isClient) {
            this.setPoisonTime(500);
            this.playSound(Sounds.ENTITY_PIRANHA_PLANT_POISON, 1.0F, this.getSoundPitch());
            AreaEffectCloudEntity effectCloud = new AreaEffectCloudEntity(world, target.getX(), this.getY(), target.getZ());
            int i = 1;
            if (this.world.getDifficulty() == Difficulty.NORMAL) {
                i = 7;
            } else if (this.world.getDifficulty() == Difficulty.HARD) {
                i = 15;
            }
            effectCloud.setParticleType(ParticleTypes.DRAGON_BREATH);
            effectCloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, i * 10, 1));
            effectCloud.setOwner(this);
            effectCloud.setRadius(2.5F);
            effectCloud.setRadiusOnUse(-0.5F);
            effectCloud.setWaitTime(10);
            effectCloud.setDuration(effectCloud.getDuration() / 2);
            effectCloud.setRadiusGrowth(-effectCloud.getRadius() / (float) effectCloud.getDuration());
            this.world.spawnEntity(effectCloud);
        }
    }

    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity) {
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 0), this);
                }
            }
            return this.getPoisonTime() > 0;
        } else {
            return false;
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(POISON_TIME, 0);
    }

    public int getPoisonTime() {
        return this.dataTracker.get(POISON_TIME);
    }

    public void setPoisonTime(int poisonTime) {
        this.dataTracker.set(POISON_TIME, poisonTime);
    }

    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() != StatusEffects.POISON && super.canHaveStatusEffect(effect);
    }
}

package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class UnagiEntity extends WaterCreatureEntity {
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState attackingAnimationState = new AnimationState();
    public final AnimationState sufferingAnimationState = new AnimationState();

    public UnagiEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, false);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    @SuppressWarnings("unused")
    public static boolean canUnagiSpawn(EntityType<? extends UnagiEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return pos.getY() <= world.getSeaLevel() - 33 && world.getBaseLightLevel(pos, 0) == 0 && world.getBlockState(pos).isOf(Blocks.WATER);
    }

    public static DefaultAttributeContainer.Builder createUnagiAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 20);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0D, 40));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, FishEntity.class, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, CheepCheepEntity.class, false));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65F;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
        }

    }

    @Override
    public void tickMovement() {
        if (!this.isTouchingWater() && this.onGround && this.verticalCollision) {
            this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.onGround = false;
            this.velocityDirty = true;
            this.playSound(Sounds.ENTITY_UNAGI_FLOP, this.getSoundVolume(), this.getSoundPitch() * 0.8F);
        }

        super.tickMovement();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient) {
            if (!this.isTouchingWater()) {
                this.swimmingAnimationState.stop();
                this.attackingAnimationState.stop();
                this.sufferingAnimationState.startIfNotRunning(this.age);
            }
            else {
                this.sufferingAnimationState.stop();
                if (this.isAttacking()) {
                    this.swimmingAnimationState.stop();
                    this.attackingAnimationState.startIfNotRunning(this.age);
                }
                else {
                    this.attackingAnimationState.stop();
                    this.swimmingAnimationState.startIfNotRunning(this.age);
                }
            }
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void playSwimSound(float volume) {
        this.playSound(this.getSwimSound(), volume, 0.8F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_UNAGI_DEATH;
    }
}

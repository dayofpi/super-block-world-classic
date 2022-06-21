package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.common.entities.hostile.BobOmbEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BobOmbBuddyEntity extends PathAwareEntity implements IAnimatable {
    private static final TrackedData<Boolean> PARACHUTED;

    static {
        PARACHUTED = DataTracker.registerData(BobOmbBuddyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    private final AnimationFactory FACTORY = new AnimationFactory(this);
    public int windUp = 0;

    public BobOmbBuddyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    public static DefaultAttributeContainer.Builder createBobOmbBuddyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new FleeEntityGoal<>(this, KingBobOmbEntity.class, 12.0f, 1.0, 1.2));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, BobOmbEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        this.playSound(Sounds.ENTITY_BOB_OMB_STEP, 0.15F, 1.0F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PARACHUTED, false);
    }

    @Override
    public void onLanding() {
        super.onLanding();
        this.setParachuted(false);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return !this.hasParachute() && super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public void tick() {
        if (this.isAlive()) {
            if (!this.hasParachute() && this.fallDistance > this.getSafeFallDistance()) {
                this.setParachuted(true);
            }

            Vec3d vec3d = this.getVelocity();
            if (this.hasParachute() && !this.onGround && vec3d.y < 0.0D) {
                this.setVelocity(vec3d.multiply(1.0D, 0.5D, 1.0D));
            }

            if (world.isClient && vec3d.horizontalLengthSquared() > 0)
                ++windUp;
        }

        super.tick();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Parachuted", this.hasParachute());
    }

    public boolean hasParachute() {
        return this.dataTracker.get(PARACHUTED);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setParachuted(nbt.getBoolean("Parachuted"));
    }

    public void setParachuted(boolean parachuted) {
        this.dataTracker.set(PARACHUTED, parachuted);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, animationEvent -> PlayState.STOP));
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 0.3F, 1.0F);
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 250;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_BOB_OMB_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_BOB_OMB_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_BOB_OMB_DEATH;
    }
}

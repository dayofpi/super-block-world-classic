package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class GladParagoombaEntity extends GladGoombaEntity {
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private float flapSpeed = 1.0f;
    private float flapEffectTime = 1.0f;
    public GladParagoombaEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createGladParagoombaAttributes() {
        return GladGoombaEntity.createGladGoombaAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.flapWings();
        if (!this.isOnGround() && random.nextInt(5) == 0) {
            world.addParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0xFFFFFF)), 1.0F), this.getX() + 0.5D, this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            world.addParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0xFFFFFF)), 1.0F), this.getX() - 0.5D, this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Nullable
    public ItemStack getPickBlockStack() {
        return new ItemStack(SpawnEggItem.forEntity(ModEntities.GLAD_GOOMBA));
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected boolean hasWings() {
        return this.speed > this.flapEffectTime;
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float)(this.onGround || this.hasVehicle() ? -1 : 4) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.onGround && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.8, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    protected void addFlapEffects() {
        this.playSound(Sounds.ENTITY_GENERIC_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
        this.flapEffectTime = this.speed + this.maxWingDeviation / 2.0F;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isInSittingPose()) {
            if (sittingTimer != 0)
                event.getController().setAnimation(new AnimationBuilder().addAnimation("sit", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("sitting", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly", true));
            return PlayState.CONTINUE;
        }

        if (this.getHealth() <= 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("squish", false));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
}

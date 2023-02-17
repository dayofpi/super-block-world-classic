package com.dayofpi.super_block_world.entity.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class GladParagoombaEntity extends GladGoombaEntity implements Flutterer {
    public float maxWingDeviation;
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
            world.addParticle(new DustParticleEffect(new Vector3f(Vec3d.unpackRgb(0xFFFFFF).toVector3f()), 1.0F), this.getX() + 0.5D, this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            world.addParticle(new DustParticleEffect(new Vector3f(Vec3d.unpackRgb(0xFFFFFF).toVector3f()), 1.0F), this.getX() - 0.5D, this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected boolean hasWings() {
        return this.speed > this.flapEffectTime;
    }

    private void flapWings() {
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
    }

    protected void addFlapEffects() {
        this.playSound(Sounds.ENTITY_GENERIC_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
        this.flapEffectTime = this.speed + this.maxWingDeviation / 2.0F;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }
}

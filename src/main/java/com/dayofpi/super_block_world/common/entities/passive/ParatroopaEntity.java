package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ParatroopaEntity extends AbstractKoopa implements Flutterer {
    public float flapProgress;
    public float maxWingDeviation;
    private float flapSpeed = 1.0f;
    private float flapEffectTime = 1.0f;
    public ParatroopaEntity(EntityType<? extends AbstractKoopa> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createParatroopaAttributes() {
        return KoopaTroopaEntity.createKoopaAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @SuppressWarnings("unused")
    public static boolean canParatroopaSpawn(EntityType<? extends AbstractKoopa> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0) && world.isSkyVisible(pos);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (source == DamageSource.LAVA)
            this.convertTo(ModEntities.DRY_BONES, false);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.airStrafingSpeed = this.getSaddledSpeed();
        this.flapWings();
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
        this.flapProgress += this.flapSpeed * 2.0f;
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

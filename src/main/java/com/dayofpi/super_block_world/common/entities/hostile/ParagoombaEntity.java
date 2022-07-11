package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import java.util.List;

public class ParagoombaEntity extends GoombaEntity implements Flutterer {
    public float flapProgress;
    public float maxWingDeviation;
    private float flapSpeed = 1.0f;
    private float flapEffectTime = 1.0f;
    private static final TrackedData<Boolean> MOTHER;

    static {
        MOTHER = DataTracker.registerData(ParagoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public ParagoombaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createParagoombaAttributes() {
        return GoombaEntity.createGoombaAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @SuppressWarnings("unused")
    public static boolean canParagoombaSpawn(EntityType<? extends GoombaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0) && world.isSkyVisible(pos);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public boolean isMother() {
        return this.dataTracker.get(MOTHER);
    }

    private void setMother(boolean mother) {
        this.dataTracker.set(MOTHER, mother);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOTHER, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Mother", this.isMother());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setMother(nbt.getBoolean("Mother"));
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    protected void initializeGoomba(ServerWorldAccess world, LocalDifficulty difficulty) {
        super.initializeGoomba(world, difficulty);
        if (random.nextInt(3) == 0)
            this.setMother(true);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.flapWings();
        if (this.isMother() && this.isAlive() && !this.isOnGround() && this.isAttacking()) {
            LivingEntity target = this.getTarget();
            if (target != null && this.distanceTo(target) > 2) {
                List<Entity> list = world.getOtherEntities(this, this.getBoundingBox().expand(16, 16, 16), entity -> entity instanceof GoombaEntity);
                if (list.size() < 3 && random.nextFloat() > 0.98F) {
                    GoombaEntity miniGoomba = ModEntities.GOOMBA.create(world);
                    if (miniGoomba != null) {
                        miniGoomba.setSize(0);
                        miniGoomba.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        world.spawnEntity(miniGoomba);
                    }
                }
            }
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

package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.List;

public class ParagoombaEntity extends GoombaEntity {
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
        birdNavigation.setCanPathThroughDoors(true);
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
        return true;
    }

    protected void addFlapEffects() {
        if (random.nextFloat() < 0.06F)
            this.playSound(Sounds.ENTITY_GENERIC_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
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

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }
}

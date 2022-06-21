package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.common.util.entity.Stompable;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.more.ParticleInit;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.List;
import java.util.Random;

public class ParagoombaEntity extends GoombaEntity implements Stompable {
    private static final TrackedData<Boolean> MOTHER;

    static {
        MOTHER = DataTracker.registerData(ParagoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public ParagoombaEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
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

    public static DefaultAttributeContainer.Builder createAttributes() {
        return GoombaEntity.createAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (this.getSize() == 1 && random.nextInt(4) == 0) {
            this.setMother(true);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isMother() && this.isAlive() && !this.isOnGround() && this.isAttacking()) {
            LivingEntity target = this.getTarget();
            if (target != null && this.distanceTo(target) > 2) {
                List<Entity> list = world.getOtherEntities(this, this.getBoundingBox().expand(16, 16, 16), entity -> entity instanceof GoombaEntity);
                if (list.size() < 3 && random.nextFloat() > 0.98F) {
                    GoombaEntity miniGoomba = EntityInit.GOOMBA.create(world);
                    if (miniGoomba != null) {
                        miniGoomba.setSize(0);
                        miniGoomba.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        world.spawnEntity(miniGoomba);
                    }
                }
            }
        }
    }

    public static boolean canParagoombaSpawn(EntityType<? extends GoombaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return GoombaEntity.canSpawn(type, world, spawnReason, pos, random) && world.isSkyVisible(pos);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected boolean hasWings() {
        return true;
    }

    protected void addFlapEffects() {
        if (random.nextFloat() < 0.06F)
            this.playSound(SoundInit.ENTITY_MISC_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
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

    @Override
    public void stompResult(LivingEntity livingEntity) {
        if (!this.world.isClient) {
            GoombaEntity goombaEntity = this.convertTo(EntityInit.GOOMBA, false);
                goombaEntity.setSize(this.getSize());
                goombaEntity.setGold(this.isGold());
                goombaEntity.setGrowable(this.isGrowable());
        } else {
            world.addParticle(ParticleInit.FEATHER, this.getX() + 0.5 + (this.getSize() - 1), this.getY() + 1 + (this.getSize() - 1), this.getZ(), 0.01D, -0.01D, 0.0D);
            world.addParticle(ParticleInit.FEATHER, this.getX() - 0.5 + (this.getSize() - 1), this.getY() + 1 + (this.getSize() - 1), this.getZ(), -0.01D, -0.01D, 0.0D);
        }
    }
}

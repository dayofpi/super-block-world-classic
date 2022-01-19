package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.blocks.mechanics.ReactiveBlock;
import com.dayofpi.super_block_world.common.util.entity.ModEntityDamageSource;
import com.dayofpi.super_block_world.common.util.entity.StaticBodyControl;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class VolcanicDebrisEntity extends MobEntity implements IAnimatable {
    final AnimationFactory animationFactory = new AnimationFactory(this);
    private int appearTimer;
    private static final TrackedData<Integer> APPEAR_TIME;
    private static final TrackedData<Boolean> SPAWNING;

    @Override
    protected BodyControl createBodyControl() {
        return new StaticBodyControl(this);
    }

    static {
        APPEAR_TIME = DataTracker.registerData(VolcanicDebrisEntity.class, TrackedDataHandlerRegistry.INTEGER);
        SPAWNING = DataTracker.registerData(VolcanicDebrisEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public VolcanicDebrisEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENTITY_VOLCANIC_DEBRIS_SMASH;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<VolcanicDebrisEntity> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        animationData.addAnimationController(controller);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(APPEAR_TIME, 0);
        this.dataTracker.startTracking(SPAWNING, true);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Appear Time", this.getAppearTime());
        nbt.putBoolean("Spawning", this.isSpawning());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setAppearTime(nbt.getInt("Appear Time"));
        this.setSpawning(nbt.getBoolean("Spawning"));
    }

    @Override
    public boolean hasNoGravity() {
        return super.hasNoGravity() || this.isSpawning();
    }

    public int getAppearTime() {
        return this.dataTracker.get(APPEAR_TIME);
    }

    public void setAppearTime(int appearTime) {
        this.dataTracker.set(APPEAR_TIME, appearTime);
    }

    public boolean isSpawning() {
        return this.dataTracker.get(SPAWNING);
    }

    private void setSpawning(boolean spawning) {
        this.dataTracker.set(SPAWNING, spawning);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        for (int i = 0; i < 4; ++i) {
            this.world.addParticle(ParticleTypes.LARGE_SMOKE, true,this.getParticleX(1.0D), this.getY(), this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
        }
        if (this.isSpawning()) {
            ++appearTimer;
            if (this.appearTimer == 60) {
                this.setSpawning(false);
                this.playSound(SoundInit.ENTITY_VOLCANIC_DEBRIS_FALL, 1.0F, 1.0F);
            }
        }
        List<Entity> list = world.getOtherEntities(this, this.getBoundingBox().contract(0.0D, 0.4D, 0.0D), entity -> entity instanceof LivingEntity livingEntity && livingEntity.canTakeDamage());
        if (this.isOnGround() || !list.isEmpty()) {
            list.forEach(entity -> entity.damage(ModEntityDamageSource.mobDrop(this), 5));
            this.playSound(SoundInit.ENTITY_VOLCANIC_DEBRIS_SMASH, 0.6F, 1.0F);
            this.discard();
            if (world.isClient)
                world.addBlockBreakParticles(this.getBlockPos(), BlockInit.CHARROCK.getDefaultState());
            if (world.getBlockState(this.getBlockPos()).isAir() && this.isOnGround()) {
                world.setBlockState(this.getBlockPos(), Blocks.FIRE.getDefaultState());
            }
            if (world.getBlockState(this.getBlockPos().down()).isIn(TagInit.BRICKS) && this.isOnGround()) {
                world.breakBlock(this.getBlockPos().down(), true);
            } else  if (world.getBlockState(this.getBlockPos().down()).getBlock() instanceof ReactiveBlock reactiveBlock && this.isOnGround()) {
                reactiveBlock.activate(world.getBlockState(this.getBlockPos().down()), world, this.getBlockPos().down());
            }
        }
    }
}

package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.goals.BlooperAttackGoal;
import com.dayofpi.super_block_world.entity.entities.goals.FollowNannyGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlooperEntity extends SquidEntity {
    private static final TrackedData<Boolean> BABY;

    static {
        BABY = DataTracker.registerData(BlooperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public BlooperEntity(EntityType<? extends SquidEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBlooperAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0);
    }

    @SuppressWarnings("unused")
    public static boolean canBlooperSpawn(EntityType<BlooperEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0) && world.getBlockState(pos).isOf(Blocks.WATER) && pos.getY() < 63;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new FollowNannyGoal(this));
        this.goalSelector.add(1, new BlooperAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true, true));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (random.nextFloat() < 0.1f)
            this.setBaby(true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BABY, false);
    }

    @Override
    public boolean isBaby() {
        return this.dataTracker.get(BABY);
    }

    public void setBaby(boolean baby) {
        this.dataTracker.set(BABY, baby);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isBaby", this.isBaby());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBaby(nbt.getBoolean("isBaby"));
    }

    @Override
    public void tickMovement() {
        if (!this.isTouchingWater() && this.onGround && this.verticalCollision)
            this.flop();
        super.tickMovement();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5f;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (BABY.equals(data)) {
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    private void flop() {
        this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
        this.onGround = false;
        this.velocityDirty = true;
        this.playSound(Sounds.ENTITY_BLOOPER_FLOP, this.getSoundVolume(), this.getSoundPitch() * 0.6F);
    }

    protected void tickWaterBreathingAir(int air) {
        this.setAir(300);
    }
}

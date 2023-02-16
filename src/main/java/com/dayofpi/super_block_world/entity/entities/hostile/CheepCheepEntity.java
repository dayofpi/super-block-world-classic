package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.brains.CheepCheepBrain;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class CheepCheepEntity extends WaterCreatureEntity {
    private static final TrackedData<Boolean> SNOWY;
    private static final ImmutableList<? extends SensorType<? extends Sensor<? super CheepCheepEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_PLAYERS, SensorType.IS_IN_WATER);
    private static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN);

    static {
        SNOWY = DataTracker.registerData(CheepCheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public CheepCheepEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    @SuppressWarnings("unused")
    public static boolean canCheepCheepSpawn(EntityType<? extends CheepCheepEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && pos.getY() > -10;
    }

    public static DefaultAttributeContainer.Builder createCheepCheepAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 10);
    }

    @Override
    public boolean canFreeze() {
        return !this.isSnowy();
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    @Override
    protected Brain.Profile<CheepCheepEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CheepCheepBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<CheepCheepEntity> getBrain() {
        return (Brain<CheepCheepEntity>) super.getBrain();
    }


    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        if (registryEntry.value().getPrecipitation() == Biome.Precipitation.SNOW)
            this.setSnowy(true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SNOWY, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsSnowy", this.isSnowy());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSnowy(nbt.getBoolean("IsSnowy"));
    }

    public boolean isSnowy() {
        return this.dataTracker.get(SNOWY);
    }

    private void setSnowy(boolean snowy) {
        this.dataTracker.set(SNOWY, snowy);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65F;
    }

    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

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

    protected void tickWaterBreathingAir(int air) {
        this.setAir(300);
    }

    private void flop() {
        this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
        this.onGround = false;
        this.velocityDirty = true;
        this.playSound(Sounds.ENTITY_CHEEP_CHEEP_FLOP, this.getSoundVolume(), this.getSoundPitch() * 0.8F);

    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("cheepCheepBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("cheepCheepActivityUpdate");
        CheepCheepBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    public void tickMovement() {
        if (!this.isTouchingWater() && this.onGround && this.verticalCollision) this.flop();
        super.tickMovement();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_CHEEP_CHEEP_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_CHEEP_CHEEP_DEATH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }
}

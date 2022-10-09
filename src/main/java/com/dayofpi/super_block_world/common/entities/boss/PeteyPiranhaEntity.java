package com.dayofpi.super_block_world.common.entities.boss;

import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.brains.PeteyPiranhaBrain;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PeteyPiranhaEntity extends ModBossEntity {
    private static final ImmutableList<SensorType<? extends Sensor<? super PeteyPiranhaEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_PLAYERS, SensorType.NEAREST_LIVING_ENTITIES);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN);
    private static final TrackedData<Boolean> SPINNING;
    public final AnimationState chargingAnimationState = new AnimationState();

    static {
        SPINNING = DataTracker.registerData(PeteyPiranhaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    public PeteyPiranhaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.YELLOW, BossBar.Style.PROGRESS);
        this.experiencePoints = 25;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SPINNING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsSpinning", this.isSpinning());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSpinning(nbt.getBoolean("IsSpinning"));
    }

    public boolean isSpinning() {
        return this.dataTracker.get(SPINNING);
    }

    public void setSpinning(boolean spinning) {
        this.dataTracker.set(SPINNING, spinning);
    }


    @Override
    public boolean tryAttack(Entity target) {
        ModBossEntity.cooldown(this, 40);

        this.playSound(Sounds.ENTITY_PETEY_PIRANHA_ATTACK, 1.0F, this.getSoundPitch());
        return super.tryAttack(target);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(Sounds.ENTITY_PETEY_PIRANHA_STEP, 0.45F, 1.0F);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_PETEY_PIRANHA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_PETEY_PIRANHA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_PETEY_PIRANHA_DEATH;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    protected void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        this.world.getProfiler().push("peteyPiranhaBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("peteyPiranhaActivityUpdate");
        PeteyPiranhaBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    public static DefaultAttributeContainer.Builder createPeteyPiranhaAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6D);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SONIC_BOOM) {
            this.chargingAnimationState.start(this.age);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected Brain.Profile<PeteyPiranhaEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return PeteyPiranhaBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<PeteyPiranhaEntity> getBrain() {
        return (Brain<PeteyPiranhaEntity>) super.getBrain();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public MusicSound getBossMusic() {
        return ModMusic.BOSS_1;
    }

    @Override
    protected @Nullable Item getRareItem() {
        return ModItems.MUSIC_DISC_FIRE_FACTORY;
    }

    @Override
    public int getMaxAttacks() {
        return 3;
    }
}

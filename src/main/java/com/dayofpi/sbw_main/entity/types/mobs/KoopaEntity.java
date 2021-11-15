package com.dayofpi.sbw_main.entity.types.mobs;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.entity.types.bases.EnemyEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.UUID;

public class KoopaEntity extends EnemyEntity implements Angerable {
    private static final TrackedData<Integer> TYPE;
    private static final TrackedData<Integer> ANGER_TIME;
    private static final UniformIntProvider ANGER_TIME_RANGE;

    static {
        TYPE = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }

    @Nullable
    private UUID targetUuid;

    public KoopaEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<? extends KoopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).allowsSpawning(world, pos, type) && !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return EnemyEntity.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getKoopaType());
        this.writeAngerToNbt(nbt);
    }

    public int getKoopaType() {
        return this.dataTracker.get(TYPE);
    }

    private void setKoopaType(int color) {
        this.dataTracker.set(TYPE, color);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setKoopaType(nbt.getInt("Type"));
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.world, nbt);
    }

    public void tickMovement() {
        super.tickMovement();

        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld) this.world, true);
        }

        if (this.hasPassengers() && this.getFirstPassenger() instanceof HammerBroEntity) {
            this.setTarget(((HammerBroEntity) this.getFirstPassenger()).getTarget());
        }
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_KOOPA_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_KOOPA_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_KOOPA_DEATH;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setKoopaType(random.nextInt(2));
        if (world.getRandom().nextInt(30) == 0) {
            HammerBroEntity hammerBroEntity = ModEntities.HAMMER_BRO.create(this.world);
            if (hammerBroEntity != null) {
                hammerBroEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                hammerBroEntity.initialize(world, difficulty, spawnReason, null, null);
                hammerBroEntity.startRiding(this);
            }
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.7D;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Nullable
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
}

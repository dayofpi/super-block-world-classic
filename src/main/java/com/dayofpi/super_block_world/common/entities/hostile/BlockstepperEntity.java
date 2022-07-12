package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.goals.BlockstepperAttackGoal;
import com.dayofpi.super_block_world.common.entities.goals.BlockstepperPanicGoal;
import com.dayofpi.super_block_world.common.entities.goals.FormationGoal;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BlockstepperEntity extends HostileEntity implements Angerable {
    public static final String LEADER_KEY = "IsLeader";
    public static final String PANIC_TIME_KEY = "PanicTime";
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.randomUUID();
    private static final TrackedData<Boolean> LEADER = DataTracker.registerData(BlockstepperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.05, EntityAttributeModifier.Operation.ADDITION);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private static final UniformIntProvider PANIC_TIME_RANGE = TimeHelper.betweenSeconds(2, 4);
    private static final UniformIntProvider ANGER_PASSING_COOLDOWN_RANGE = TimeHelper.betweenSeconds(4, 6);
    private int angerTime;
    private int panicTime;
    @Nullable
    private UUID angryAt;
    private int windUp = 0;
    private int angerPassingCooldown;
    @Nullable
    private BlockstepperEntity following;
    @Nullable
    private BlockstepperEntity follower;

    public BlockstepperEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBlockstepperAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<BlockstepperEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && world.getBlockState(pos.down()).isOf(ModBlocks.CHERRY_GRASS);
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setPanicTime(PANIC_TIME_RANGE.get(this.random));
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LEADER, true);
    }

    public boolean isLeader() {
        return this.dataTracker.get(LEADER);
    }

    public void setLeader(boolean leader) {
        this.dataTracker.set(LEADER, leader);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BlockstepperPanicGoal(this, 1.0D));
        this.goalSelector.add(2, new BlockstepperAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new FormationGoal(this, 0.7D));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.7D, 0.2F));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, true));
    }

    @Override
    protected void mobTick() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance != null)
            if (this.hasAngerTime()) {
                if (!entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                    entityAttributeInstance.addTemporaryModifier(ATTACKING_SPEED_BOOST);
                }
            } else if (entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                entityAttributeInstance.removeModifier(ATTACKING_SPEED_BOOST);
            }
        this.tickAngerLogic((ServerWorld)this.world, true);
        if (this.getTarget() != null) {
            this.tickAngerPassing();
        }
        if (this.hasAngerTime()) {
            this.playerHitTimer = this.age;
        }
        if (this.getPanicTime() > 0) {
            this.setPanicTime(this.getPanicTime() - 1);
        }
        super.mobTick();
    }

    public void stopFollowing() {
        if (this.following != null) {
            this.following.follower = null;
        }
        this.following = null;
    }

    public void follow(BlockstepperEntity blockstepper) {
        this.following = blockstepper;
        this.following.follower = this;
    }

    public boolean hasFollower() {
        return this.follower != null;
    }

    public boolean isFollowing() {
        return this.following != null;
    }

    @Nullable
    public BlockstepperEntity getFollowing() {
        return this.following;
    }

    private void tickAngerPassing() {
        if (this.angerPassingCooldown > 0) {
            --this.angerPassingCooldown;
            return;
        }
        if (this.getVisibilityCache().canSee(this.getTarget())) {
            this.angerNearbyBlocksteppers();
        }
        this.angerPassingCooldown = ANGER_PASSING_COOLDOWN_RANGE.get(this.random);
    }

    private void angerNearbyBlocksteppers() {
        double d = this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        Box box = Box.from(this.getPos()).expand(d, 10.0, d);
        this.world.getEntitiesByClass(BlockstepperEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR).stream().filter(blockstepper -> blockstepper != this).filter(blockstepper -> blockstepper.getTarget() == null).filter(blockstepper -> !blockstepper.isTeammate(this.getTarget())).forEach(blockstepper -> {
            blockstepper.setTarget(this.getTarget());
            blockstepper.setPanicTime(PANIC_TIME_RANGE.get(this.random));
        });
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.getTarget() == null && target != null) {
            this.angerPassingCooldown = ANGER_PASSING_COOLDOWN_RANGE.get(this.random);
            this.playSound(Sounds.ENTITY_BLOCKSTEPPER_ALARM, this.getSoundVolume(), this.getSoundPitch());
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.jump();
        }
        if (target instanceof PlayerEntity) {
            this.setAttacking((PlayerEntity)target);
        }
        super.setTarget(target);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!this.hasAngerTime())
            this.playSound(Sounds.ENTITY_BLOCKSTEPPER_MARCH, 0.15F, this.getSoundPitch() + 0.1F);
        this.playSound(Sounds.ENTITY_BLOCKSTEPPER_STEP, 1.0F, this.getSoundPitch());
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.hasAngerTime())
            return Sounds.ENTITY_BLOCKSTEPPER_PANIC;
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_BLOCKSTEPPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_BLOCKSTEPPER_DEATH;
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            Vec3d vec3d = this.getVelocity();
            if (world.isClient && vec3d.horizontalLengthSquared() > 0)
                ++windUp;
        }
        super.tick();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(PANIC_TIME_KEY, this.getPanicTime());
        nbt.putBoolean(LEADER_KEY, this.isLeader());
        this.writeAngerToNbt(nbt);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0F;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setPanicTime(nbt.getInt(PANIC_TIME_KEY));
        this.setLeader(nbt.getBoolean(LEADER_KEY));
        this.readAngerFromNbt(this.world, nbt);
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    public int getPanicTime() {
        return this.panicTime;
    }

    public void setPanicTime(int panicTime) {
        this.panicTime = panicTime;
    }

    @Override
    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public boolean isAngryAt(PlayerEntity player) {
        return this.shouldAngerAt(player);
    }

    public int getWindUp() {
        return this.windUp;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        BlockstepperData blockstepperData;
        if (spawnReason != SpawnReason.NATURAL && random.nextInt(5) != 0) {
            this.setLeader(false);
        }
        if (entityData == null) {
            entityData = new BlockstepperData();
        }
        if ((blockstepperData = (BlockstepperData)entityData).getSpawnedCount() > 0) {
            this.setLeader(false);
        }
        blockstepperData.countSpawned();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static class BlockstepperData implements EntityData {
        private int spawnCount;

        private BlockstepperData() {
        }

        public int getSpawnedCount() {
            return this.spawnCount;
        }

        public void countSpawned() {
            ++this.spawnCount;
        }
    }
}

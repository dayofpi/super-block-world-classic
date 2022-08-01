package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoombaEntity extends HostileEntity {
    private static final TrackedData<Integer> SIZE;
    private static final TrackedData<Boolean> GROWABLE;
    private static final TrackedData<Boolean> GOLD;
    public final AnimationState walkingAnimationState = new AnimationState();

    static {
        SIZE = DataTracker.registerData(GoombaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        GROWABLE = DataTracker.registerData(GoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        GOLD = DataTracker.registerData(GoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public GoombaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createGoombaAttributes() {
        return HostileEntity.createHostileAttributes();
    }

    private static boolean isThereNoLight(WorldAccess world, BlockPos pos) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    @SuppressWarnings("unused")
    public static boolean canGoombaSpawn(EntityType<? extends GoombaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(ModTags.SURFACE_GOOMBA_SPAWN))
            return isThereNoLight(world, pos);
        else return isThereNoLight(world, pos) && world.getBlockState(pos.down()).isIn(ModTags.VANILLATE);
    }

    public void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0f, 1.2, 1.4));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, CatEntity.class, 6.0f, 1.2, 1.4));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SIZE, 1);
        this.dataTracker.startTracking(GROWABLE, true);
        this.dataTracker.startTracking(GOLD, false);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.isOnGround() && this.getTarget() == null && target != null) {
            this.playSound(Sounds.ENTITY_GENERIC_SPOT, this.getSoundVolume(), this.getSoundPitch());
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.jump();
        }
        super.setTarget(target);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getSize() == 1)
            return Sounds.ENTITY_GOOMBA_AMBIENT;
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (this.getSize() == 1)
            return Sounds.ENTITY_GOOMBA_HURT;
        else if (this.getSize() == 0)
            return null;
        else return Sounds.ENTITY_BIG_GOOMBA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (this.getSize() == 1)
            return Sounds.ENTITY_GOOMBA_DEATH;
        else if (this.getSize() == 0)
            return Sounds.ENTITY_MINI_GOOMBA_DEATH;
        else return Sounds.ENTITY_BIG_GOOMBA_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 1.0F, this.getSoundPitch());
    }

    protected SoundEvent getStepSound() {
        if (this.getSize() == 1)
            return Sounds.ENTITY_GOOMBA_STEP;
        else if (this.getSize() == 0)
            return Sounds.ENTITY_MINI_GOOMBA_STEP;
        else return Sounds.ENTITY_BIG_GOOMBA_STEP;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Size", this.getSize());
        nbt.putBoolean("Gold", this.isGold());
        nbt.putBoolean("Growable", this.isGrowable());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setSize(nbt.getInt("Size"));
        this.setGold(nbt.getBoolean("Gold"));
        this.setGrowable(nbt.getBoolean("Growable"));
        super.readCustomDataFromNbt(nbt);
    }

    private boolean shouldWalk() {
        return this.getVelocity().horizontalLengthSquared() > 1.0E-6;
    }
    @Override
    public void tick() {
        if (this.world.isClient) {
            if (this.deathTime > 0) {
                this.walkingAnimationState.stop();
            } else if (this.shouldWalk()) {
                this.walkingAnimationState.startIfNotRunning(this.age);
            }
            else {
                this.walkingAnimationState.stop();
            }
        }
        super.tick();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            if (this.getSize() < 2 && !world.isClient) {
                List<ItemEntity> list = this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.7), itemEntity -> itemEntity.getStack().isOf(ModItems.SUPER_MUSHROOM));
                if (!list.isEmpty()) {
                    this.setSize(this.getSize() + 1);
                    this.playSound(Sounds.ENTITY_GENERIC_POWER_UP, 2.0F, 1.0F);
                    list.get(0).discard();
                    if (!world.isSpaceEmpty(this)) {
                        for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 1, 1, 1)) {
                            BlockState blockState = world.getBlockState(blockPos);
                            if (blockState.shouldSuffocate(world, blockPos)) {
                                world.breakBlock(blockPos, true);
                            }
                        }
                    }
                }
            }
            if (this.getSize() == 0) {
                PlayerEntity playerEntity = world.getClosestPlayer(this, 1);
                if (playerEntity != null) {
                    playerEntity.slowMovement(playerEntity.getBlockStateAtPos(), new Vec3d(0.1D, 0.5D, 0.1D));
                    this.navigation.startMovingTo(playerEntity, 1.0D);
                }
            }
        }
    }

    @Override
    protected boolean shouldDropLoot() {
        return this.getSize() == 1;
    }

    private void initializeGoombaTower(ServerWorldAccess world, LocalDifficulty difficulty) {
        GoombaEntity goombaEntity = ModEntities.GOOMBA.create(world.toServerWorld());
        if (goombaEntity != null) {
            goombaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
            goombaEntity.initialize(world, difficulty, SpawnReason.JOCKEY, null, null);
            goombaEntity.setSize(1);
            goombaEntity.startRiding(this, true);
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (this.random.nextInt(100) == 0) {
            this.setGold(true);
        }
        if (random.nextFloat() > 0.5F) {
            this.setSize(0);
        } else if (random.nextFloat() > 0.5F && world.isSpaceEmpty(this.getBoundingBox().offset(0, 2, 0).expand(2))) {
            this.setSize(2);
        } else this.initializeGoomba(world, difficulty);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    protected void initializeGoomba(ServerWorldAccess world, LocalDifficulty difficulty) {
        this.setSize(1);
        if (this.random.nextInt(4) == 0) {
            this.initializeGoombaTower(world, difficulty);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.distanceTo(target) < 2F) {
            return super.tryAttack(target);
        }
        return false;
    }

    public int getSize() {
        return this.dataTracker.get(SIZE);
    }

    public void setSize(int size) {
        int clampedSize = MathHelper.clamp(size, 0, 2);
        this.dataTracker.set(SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        this.experiencePoints = clampedSize * 2 * (this.isGold() ? 2 : 1);

        EntityAttributeInstance health = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeInstance speed = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance attackDamage = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (health == null || speed == null || attackDamage == null)
            return;
        if (clampedSize == 0) {
            health.setBaseValue(1.0D);
            speed.setBaseValue(0.3D);
            attackDamage.setBaseValue(0.0D);
            this.experiencePoints = 1;
        } else if (clampedSize == 1) {
            health.setBaseValue(4.0D);
            speed.setBaseValue(0.22D);
            attackDamage.setBaseValue(3.0D);
            this.experiencePoints = 3;
        } else if (clampedSize == 2) {
            health.setBaseValue(7.0D);
            speed.setBaseValue(0.18D);
            attackDamage.setBaseValue(6.0D);
            this.experiencePoints = 5;
        }
        this.setHealth(this.getMaxHealth());
    }

    public boolean isGrowable() {
        return this.dataTracker.get(GROWABLE);
    }

    public void setGrowable(boolean growable) {
        this.dataTracker.set(GROWABLE, growable);
    }

    public boolean isGold() {
        return this.dataTracker.get(GOLD);
    }

    public void setGold(boolean gold) {
        this.dataTracker.set(GOLD, gold);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.isGold()) {
            this.dropItem(ModItems.COIN);
        }
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (SIZE.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
            if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
                this.onSwimmingStart();
            }
        }
        super.onTrackedDataSet(data);
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        float tallness = 1;
        if (this.getSize() >= 2) {
            tallness = 1.4f;
        }
        return super.getDimensions(pose).scaled(0.5F * (float) this.getSize() + 0.6F).scaled(tallness - 0.3f, tallness);
    }

    public void remove(RemovalReason reason) {
        int size = this.getSize();
        if (!this.world.isClient && this.isDead()) {
            if (size == 2) {
                Text text = this.getCustomName();
                float f = (float) 2 / 4.0F;
                int k = 2 + this.random.nextInt(3);
                for (int l = 0; l < k; ++l) {
                    float g = ((float) (l % 2) - 0.5F) * f;
                    float h = ((float) (l / 2) - 0.5F) * f;
                    GoombaEntity goombaEntity = (GoombaEntity) this.getType().create(this.world);
                    if (goombaEntity != null) {
                        if (this.isPersistent()) {
                            goombaEntity.setPersistent();
                        }
                        goombaEntity.setCustomName(text);
                        goombaEntity.setAiDisabled(this.isAiDisabled());
                        goombaEntity.setInvulnerable(this.isInvulnerable());
                        goombaEntity.setGold(this.isGold());
                        goombaEntity.setSize(1);
                        goombaEntity.setGrowable(false);
                        goombaEntity.refreshPositionAndAngles(this.getX() + (double) g, this.getY() + 0.5D, this.getZ() + (double) h, this.random.nextFloat() * 360.0F, 0.0F);
                        this.world.spawnEntity(goombaEntity);
                    }
                }
            }
        }

        super.remove(reason);
    }

    @Override
    public double getMountedHeightOffset() {
        return this.getDimensions(getPose()).height - 0.1D;
    }

    @Override
    public void calculateDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.calculateDimensions();
        this.setPosition(x, y, z);
    }
}
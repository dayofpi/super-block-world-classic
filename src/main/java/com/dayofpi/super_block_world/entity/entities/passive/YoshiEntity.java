package com.dayofpi.super_block_world.entity.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.block_entities.YoshiEggBE;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.goals.YoshiEatGoal;
import com.dayofpi.super_block_world.entity.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.entity.entities.hostile.ShyGuyEntity;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.criterion.ModCriteria;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class YoshiEntity extends AnimalEntity implements JumpingMount {
    private static final Ingredient BREEDING_INGREDIENT;
    private static final TrackedData<NbtCompound> STORED_ENTITY;
    private static final TrackedData<Integer> EXTEND_TIME;
    private static final TrackedData<Integer> EGG_LAY_TIME;
    private static final TrackedData<Boolean> TONGUE_OUT;
    private static final TrackedData<Boolean> CAN_FLUTTER;
    private static final TrackedData<String> COLOR;

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.YOSHI_COOKIE);
        STORED_ENTITY = DataTracker.registerData(YoshiEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
        EXTEND_TIME = DataTracker.registerData(YoshiEntity.class, TrackedDataHandlerRegistry.INTEGER);
        EGG_LAY_TIME = DataTracker.registerData(YoshiEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TONGUE_OUT = DataTracker.registerData(YoshiEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CAN_FLUTTER = DataTracker.registerData(YoshiEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        COLOR = DataTracker.registerData(YoshiEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    private final int maxEggLayTime = 200;
    public final AnimationState usingTongueAnimationState = new AnimationState();
    private boolean fluttering;
    private float jumpStrength;

    public YoshiEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
    }

    public static DefaultAttributeContainer.Builder createYoshiAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    public boolean shouldDropXp() {
        return false;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new YoshiEatGoal(this, 1.3, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, ShyGuyEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STORED_ENTITY, new NbtCompound());
        this.dataTracker.startTracking(EXTEND_TIME, 0);
        this.dataTracker.startTracking(EGG_LAY_TIME, maxEggLayTime);
        this.dataTracker.startTracking(TONGUE_OUT, false);
        this.dataTracker.startTracking(CAN_FLUTTER, false);
        this.dataTracker.startTracking(COLOR, DyeColor.GREEN.getName());
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("StoredEntity", this.getStoredEntity());
        nbt.putInt("ExtendTime", this.getExtendTime());
        nbt.putInt("EggLayTime", this.getEggLayTime());
        nbt.putBoolean("TongueOut", this.isTongueOut());
        nbt.putBoolean("CanFlutter", this.canFlutter());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setStoredEntity(nbt.getCompound("StoredEntity"));
        this.setExtendTime(nbt.getInt("ExtendTime"));
        this.setEggLayTime(nbt.getInt("EggLayTime"));
        this.setTongueOut(nbt.getBoolean("TongueOut"));
        this.setCanFlutter(nbt.getBoolean("CanFlutter"));
    }

    public boolean isTongueOut() {
        return this.dataTracker.get(TONGUE_OUT);
    }

    private void setTongueOut(boolean tongueOut) {
        this.dataTracker.set(TONGUE_OUT, tongueOut);
    }

    private int getExtendTime() {
        return this.dataTracker.get(EXTEND_TIME);
    }

    private void setExtendTime(int time) {
        this.dataTracker.set(EXTEND_TIME, time);
    }

    private int getEggLayTime() {
        return this.dataTracker.get(EGG_LAY_TIME);
    }

    private void setEggLayTime(int time) {
        this.dataTracker.set(EGG_LAY_TIME, time);
    }

    public boolean isFluttering() {
        return fluttering;
    }

    public boolean canFlutter() {
        return this.dataTracker.get(CAN_FLUTTER);
    }

    private void setCanFlutter(boolean canFlutter) {
        this.dataTracker.set(CAN_FLUTTER, canFlutter);
    }

    public NbtCompound getStoredEntity() {
        return this.dataTracker.get(STORED_ENTITY);
    }

    private void setStoredEntity(NbtCompound storedEntity) {
        this.dataTracker.set(STORED_ENTITY, storedEntity);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.55;
    }

    public double getJumpStrength() {
        return 0.8D;
    }

    @Override
    public void setJumpStrength(int strength) {
        if (strength < 0) strength = 0;
        this.jumpStrength = strength >= 90 ? 1.0f : 0.4f + 0.4f * (float) strength / 90.0f;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    private boolean isEdible(Entity entity) {
        return !entity.isPlayer() && !(entity instanceof EnderDragonEntity) && !(entity instanceof WitherEntity) && !(entity instanceof ElderGuardianEntity) && !(entity instanceof ModBossEntity);
    }

    public void extendTongue() {
        if (!this.getStoredEntity().isEmpty()) {
            NbtCompound nbtCompound = this.getStoredEntity();
            if (!world.isClient) EntityType.getEntityFromNbt(nbtCompound, world).ifPresent(entity -> {
                BlockPos blockPos = this.getBlockPos().offset(this.getHorizontalFacing());
                entity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                ((ServerWorld) world).tryLoadEntity(entity);
                this.playSound(Sounds.ENTITY_YOSHI_SPIT, 1.0F, this.getSoundPitch());
                this.setStoredEntity(new NbtCompound());
            });
            return;
        }
        this.setTongueOut(true);
        this.playSound(Sounds.ENTITY_YOSHI_REACH, 1.0F, 1.0F);
        Entity passenger = this.getPrimaryPassenger();
        BlockPos blockPos = this.getBlockPos().offset(this.getHorizontalFacing());
        List<Entity> list = world.getOtherEntities(this, Box.from(Vec3d.ofCenter(blockPos)).expand(0.7D), entity -> entity != passenger);
        if (list.isEmpty()) return;
        Entity entity = list.get(0);
        if (isEdible(entity) && !world.isClient) {
            eatEntity(entity);
        }
    }

    private void eatEntity(Entity food) {
        this.playSound(Sounds.ENTITY_YOSHI_EAT, 1.0F, 1.0F);
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("id", this.getSavedEntityId(food));
        food.writeNbt(nbtCompound);
        food.discard();
        this.setStoredEntity(nbtCompound);
        if (food instanceof FuzzyEntity)
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 2000, 2));
        Entity entity = this.getPrimaryPassenger();
        if (entity instanceof ServerPlayerEntity)
            ModCriteria.EAT_ENTITY_WITH_YOSHI.trigger((ServerPlayerEntity) entity);
    }

    @Nullable
    protected final String getSavedEntityId(Entity entity) {
        EntityType<?> entityType = entity.getType();
        Identifier identifier = EntityType.getId(entityType);
        return !entityType.isSaveable() || identifier == null ? null : identifier.toString();
    }

    private void retractTongue() {
        this.setTongueOut(false);
    }

    private void layEgg() {
        Iterable<BlockPos> range = BlockPos.iterateOutwards(this.getBlockPos(), 1, 0, 1);

        BlockPos blockPos = range.iterator().next();
        this.setEggLayTime(this.maxEggLayTime);
        if (world.isSpaceEmpty(Box.from(Vec3d.ofCenter(blockPos))) && world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.down()).isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            world.setBlockState(blockPos, ModBlocks.YOSHI_EGG.getDefaultState());
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof YoshiEggBE) {
                ((YoshiEggBE) blockEntity).setStoredEntity(this.getStoredEntity());
                this.setStoredEntity(new NbtCompound());
                this.playSound(Sounds.ENTITY_YOSHI_SWALLOW, 1.0F, this.getSoundPitch());
            }
        }
    }

    @Override
    public void tick() {
        if (world.isClient) {
             if (this.isTongueOut()) {
                 this.usingTongueAnimationState.startIfNotRunning(this.age);
             } else this.usingTongueAnimationState.stop();
             if (this.isFluttering()) {
                this.world.addParticle(ParticleTypes.WAX_OFF, this.getParticleX(0.5), this.getBlockY(), this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        } else if (!this.getStoredEntity().isEmpty()) {
            if (this.getEggLayTime() > 0) this.setEggLayTime(this.getEggLayTime() - 1);
            else if (this.getEggLayTime() == 0) {
                this.layEgg();
            }
        }

        super.tick();
        if (this.isTongueOut()) {
            int maxExtendTime = 7;
            if (this.getExtendTime() < maxExtendTime) this.setExtendTime(this.getExtendTime() + 1);
            else if (this.getExtendTime() == maxExtendTime) {
                this.setExtendTime(0);
                this.retractTongue();
            }
        }
        if (this.isOnGround() || this.isTouchingWater()) this.fluttering = false;
    }

    private void updateYaws(LivingEntity livingEntity) {
        this.setYaw(livingEntity.getYaw());
        this.prevYaw = this.getYaw();
        this.headYaw = this.getYaw();
        this.setRotation(this.getYaw(), this.getPitch());
    }

    private void startJumping() {
        double modified = this.getJumpStrength() * (double) this.jumpStrength * (double) this.getJumpVelocityMultiplier() + this.getJumpBoostVelocityModifier();

        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x, modified, vec3d.z);
        this.velocityDirty = true;
        this.jumpStrength = 0;
        if (!this.isOnGround()) {
            this.fluttering = true;
            world.emitGameEvent(null, GameEvent.FLAP, this.getBlockPos());
            this.setCanFlutter(false);
        }

        if (forwardSpeed > 0.0f) {
            float sin = MathHelper.sin(this.getYaw() * ((float) Math.PI / 180));
            float cos = MathHelper.cos(this.getYaw() * ((float) Math.PI / 180));
            this.setVelocity(this.getVelocity().add(-0.4f * sin * this.jumpStrength, 0.0, 0.4f * cos * this.jumpStrength));
        }
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (!this.isAlive()) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity) this.getPrimaryPassenger();

        if (!(this.hasPassengers())) {
            this.airStrafingSpeed = 0.02f;
            super.travel(movementInput);
            return;
        }

        if (livingEntity == null) {
            this.airStrafingSpeed = 0.02f;
            super.travel(movementInput);
            return;
        }
        updateYaws(livingEntity);
        float sidewaysSpeed = livingEntity.sidewaysSpeed * 0.5f;
        float forwardSpeed = livingEntity.forwardSpeed;
        if (forwardSpeed <= 0.0f) {
            forwardSpeed *= 0.25f;
        }

        if (this.jumpStrength > 0.0f && (this.isOnGround() || this.canFlutter())) {
            startJumping();
        }

        this.airStrafingSpeed = this.getMovementSpeed() * 0.2f;
        if (this.isLogicalSideForUpdatingMovement()) {
            this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            super.travel(new Vec3d(sidewaysSpeed, movementInput.y, forwardSpeed));
        } else if (livingEntity instanceof PlayerEntity) {
            this.setVelocity(Vec3d.ZERO);
        }
        if (this.onGround) {
            this.setCanFlutter(true);
            this.jumpStrength = 0.0f;
        }
        this.updateLimbs(this, this.isFluttering());
        this.tryCheckBlockCollision();
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return MathHelper.ceil((fallDistance * 0.5f - 3.0f) * damageMultiplier);
    }

    protected void putPlayerOnBack(PlayerEntity player) {
        if (!this.world.isClient && !player.isSneaking()) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            player.startRiding(this);
            this.playSound(Sounds.ENTITY_YOSHI_MOUNT, 1.0F, 1.0F);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getVehicle() == this) {
            this.extendTongue();
            return ActionResult.success(this.world.isClient);
        }
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.getHealth() < this.getMaxHealth()) {
            boolean isCookie = itemStack.isOf(ModItems.YOSHI_COOKIE);
            if (itemStack.isOf(ModItems.YOSHI_FRUIT) || isCookie) {
                this.eat(player, hand, itemStack);
                this.heal(isCookie ? 10 : 5);
                return ActionResult.SUCCESS;
            }
        } else if (isBreedingItem(itemStack)) {
            int i = this.getBreedingAge();
            if (!this.world.isClient && i == 0 && this.canEat()) {
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                return ActionResult.SUCCESS;
            }
        }
        if (this.isBaby()) return super.interactMob(player, hand);
        this.putPlayerOnBack(player);
        return ActionResult.success(this.world.isClient);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i;
        if (fallDistance > 1.0f) {
            this.playSound(Sounds.ENTITY_YOSHI_STEP, 0.5f, 1.0f);
        }
        if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) {
            return false;
        }
        this.damage(damageSource, i);
        if (this.hasPassengers()) {
            for (Entity entity : this.getPassengersDeep()) {
                entity.damage(damageSource, i);
            }
        }
        this.playBlockFallSound();
        return true;
    }

    @Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        super.eat(player, hand, stack);
        this.world.playSoundFromEntity(null, this, Sounds.ENTITY_YOSHI_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_YOSHI_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_YOSHI_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_YOSHI_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(Sounds.ENTITY_YOSHI_STEP, 0.25f, 1.0f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source == DamageSource.HOT_FLOOR) return false;
        boolean damage = super.damage(source, amount);
        if (damage && this.getPrimaryPassenger() != null && source != DamageSource.FALL)
            this.getPrimaryPassenger().stopRiding();
        return damage;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.BABY_YOSHI.create(world);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        if (!this.isOnGround()) {
            return this.getPos().add(0.0, 2.0, 0.0);
        }
        Vec3d vec3d = AbstractHorseEntity.getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0f : -90.0f));
        Vec3d vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        }
        Vec3d vec3d3 = AbstractHorseEntity.getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0f : -90.0f));
        Vec3d vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
        if (vec3d4 != null) {
            return vec3d4;
        }
        return this.getPos();
    }

    @Nullable
    private Vec3d locateSafeDismountingPos(Vec3d offset, LivingEntity passenger) {
        double x = this.getX() + offset.x;
        double y = this.getBoundingBox().minY;
        double z = this.getZ() + offset.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        block0:
        for (EntityPose entityPose : passenger.getPoses()) {
            mutable.set(x, y, z);
            double g = this.getBoundingBox().maxY + 0.75;
            do {
                Vec3d vec3d;
                double height = this.world.getDismountHeight(mutable);
                if ((double) mutable.getY() + height > g) continue block0;
                if (Dismounting.canDismountInBlock(height) && Dismounting.canPlaceEntityAt(this.world, passenger, passenger.getBoundingBox(entityPose).offset(vec3d = new Vec3d(x, (double) mutable.getY() + height, z)))) {
                    passenger.setPose(entityPose);
                    return vec3d;
                }
                mutable.move(Direction.UP);
            } while ((double) mutable.getY() < g);
        }
        return null;
    }

    @Override
    public boolean canJump() {
        return this.isOnGround() || this.canFlutter();
    }

    @Override
    public void startJumping(int height) {
        if (!this.isOnGround()) {
            this.playSound(Sounds.ENTITY_YOSHI_FLUTTER, 1.0F, 1.0F);
        }
    }

    @Override
    public void stopJumping() {
    }
}

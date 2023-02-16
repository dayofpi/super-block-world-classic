package com.dayofpi.super_block_world.entity.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.hostile.GoombaEntity;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GladGoombaEntity extends TameableEntity {
    private static final TrackedData<Boolean> BIG;
    private static final Ingredient BREEDING_INGREDIENT;
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();

    static {
        BIG = DataTracker.registerData(GladGoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.YOSHI_COOKIE);
    }

    int sittingTimer = 6;

    public GladGoombaEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createGladGoombaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new TemptGoal(this, 1.2D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 0.86));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GoombaEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BIG, false);
    }

    public boolean isBig() {
        return this.dataTracker.get(BIG);
    }

    public void setBig(boolean big) {
        if (big) {
            EntityAttributeInstance health = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            EntityAttributeInstance speed = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            EntityAttributeInstance attackDamage = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if (health != null)
                health.addPersistentModifier(new EntityAttributeModifier("Big health", 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
            if (speed != null)
                speed.addPersistentModifier(new EntityAttributeModifier("Big speed", 0.7, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
            if (attackDamage != null)
                attackDamage.addPersistentModifier(new EntityAttributeModifier("Big damage", 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        this.dataTracker.set(BIG, big);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Big", this.isBig());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setBig(nbt.getBoolean("Big"));
        super.readCustomDataFromNbt(nbt);
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

    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_GOOMBA_AMBIENT;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        if (this.isBig())
            return super.getDimensions(pose).scaled(1.5F);
        return super.getDimensions(pose);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (BIG.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.bodyYaw = this.headYaw;
            if (this.isTouchingWater() && this.random.nextInt(20) == 0) {
                this.onSwimmingStart();
            }
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public int getMaxLookPitchChange() {
        if (this.isInSittingPose()) {
            return 20;
        }
        return super.getMaxLookPitchChange();
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.6f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || BREEDING_INGREDIENT.test(itemStack) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        }
        if (this.isTamed()) {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                    this.world.playSoundFromEntity(null, this, Sounds.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
                }
                if (item.getFoodComponent() != null)
                    this.heal(item.getFoodComponent().getHunger());
                return ActionResult.SUCCESS;
            }
            ActionResult bl = super.interactMob(player, hand);
            if (bl.isAccepted() && !this.isBaby() || !this.isOwner(player)) return bl;
            this.setSitting(!this.isSitting());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return ActionResult.SUCCESS;
        }
        if (!itemStack.isOf(ModItems.YOSHI_COOKIE)) return super.interactMob(player, hand);
        if (!player.getAbilities().creativeMode) {
            itemStack.decrement(1);
            this.world.playSoundFromEntity(null, this, Sounds.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
        }
        if (this.random.nextInt(3) == 0) {
            this.setOwner(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setSitting(true);
            this.world.sendEntityStatus(this, (byte) 7);
            return ActionResult.SUCCESS;
        } else {
            this.world.sendEntityStatus(this, (byte) 6);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (int) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        if (bl) {
            this.applyDamageEffects(this, target);
        }
        return bl;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_GOOMBA_HURT;
    }

    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_GOOMBA_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 1.0F, this.getSoundPitch());
    }

    protected SoundEvent getStepSound() {
        if (this.isBig())
            return Sounds.ENTITY_BIG_GOOMBA_STEP;
        else if (this.isBaby())
            return Sounds.ENTITY_MINI_GOOMBA_STEP;
        else return Sounds.ENTITY_GOOMBA_STEP;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isInSittingPose() && sittingTimer > 0) {
            --sittingTimer;
        } else if (!this.isInSittingPose()) {
            sittingTimer = 6;
        }
        if (this.isAlive()) {
            if (!this.isBig() && !world.isClient) {
                List<ItemEntity> list = this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.7), itemEntity -> itemEntity.getStack().isOf(ModItems.SUPER_MUSHROOM));
                if (!list.isEmpty()) {
                    this.setBig(true);
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
        }

    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        GladGoombaEntity gladGoombaEntity = ModEntities.GLAD_GOOMBA.create(serverWorld);
        UUID uUID = this.getOwnerUuid();
        if (gladGoombaEntity != null && uUID != null) {
            gladGoombaEntity.setOwnerUuid(uUID);
            gladGoombaEntity.setTamed(true);
        }
        return gladGoombaEntity;
    }

    protected boolean shouldWalk() {
        return this.getVelocity().horizontalLengthSquared() > 1.0E-6;
    }

    @Override
    public void tick() {
        if (this.world.isClient()) {
            if (this.isInSittingPose()) {
                this.sittingAnimationState.startIfNotRunning(this.age);
            } else this.sittingAnimationState.stop();
            if (this.isDead()) {
                this.walkingAnimationState.stop();
            } else if (this.shouldWalk()) {
                this.walkingAnimationState.startIfNotRunning(this.age);
            } else this.walkingAnimationState.stop();
        }
        super.tick();
    }
}

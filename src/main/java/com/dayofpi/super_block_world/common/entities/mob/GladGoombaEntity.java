package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class GladGoombaEntity extends TameableEntity implements IAnimatable {
    private static final TrackedData<Boolean> BIG;
    private static final Ingredient BREEDING_INGREDIENT;
    private static final Ingredient TEMPTING_INGREDIENT;

    static {
        BIG = DataTracker.registerData(GladGoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BREEDING_INGREDIENT = Ingredient.ofItems(ItemInit.YOSHI_COOKIE);
        TEMPTING_INGREDIENT = Ingredient.ofItems(ItemInit.YOSHI_COOKIE, ItemInit.MAGIC_WINGS);
    }

    final AnimationFactory factory = new AnimationFactory(this);
    int sittingTimer = 6;
    public GladGoombaEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.86));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GoombaEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, TEMPTING_INGREDIENT, true));
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

    private void setBig(boolean big) {
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

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.isOnGround() && this.getTarget() == null && target != null) {
            this.playSound(SoundInit.ENTITY_MISC_SPOT, this.getSoundVolume(), this.getSoundPitch());
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x, 0.2F, vec3d.z);
        }
        super.setTarget(target);
    }

    protected SoundEvent getAmbientSound() {
        return SoundInit.ENTITY_GOOMBA_AMBIENT;
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
                }
                if (item.getFoodComponent() != null)
                    this.heal(item.getFoodComponent().getHunger());
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                return ActionResult.SUCCESS;
            } else if (itemStack.isOf(ItemInit.MAGIC_WINGS) && !(this instanceof GladParagoombaEntity) && !world.isClient) {
                GladParagoombaEntity gladParagoombaEntity = this.convertTo(EntityInit.GLAD_PARAGOOMBA, true);
                if (gladParagoombaEntity != null) {
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }
                    this.playSound(SoundInit.ITEM_POWER_UP, 1.0F, this.getSoundPitch());
                    gladParagoombaEntity.initialize((ServerWorldAccess) this.getWorld(), this.getWorld().getLocalDifficulty(getBlockPos()), SpawnReason.CONVERSION, null, null);
                    gladParagoombaEntity.setOwner((PlayerEntity) this.getOwner());

                }
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
        if (!itemStack.isOf(ItemInit.YOSHI_COOKIE)) return super.interactMob(player, hand);
        if (!player.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        if (this.random.nextInt(3) == 0) {
            this.setOwner(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setSitting(true);
            this.world.sendEntityStatus(this, (byte)7);
            return ActionResult.SUCCESS;
        } else {
            this.world.sendEntityStatus(this, (byte)6);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        if (bl) {
            this.applyDamageEffects(this, target);
        }
        return bl;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENTITY_GOOMBA_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_GOOMBA_DEATH;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isInSittingPose() && sittingTimer > 0) {
            --sittingTimer;
        } else if (!this.isInSittingPose()) {
            sittingTimer = 6;
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        GladGoombaEntity gladGoombaEntity = EntityInit.GLAD_GOOMBA.create(serverWorld);
        UUID uUID = this.getOwnerUuid();
        if (gladGoombaEntity != null && uUID != null) {
            gladGoombaEntity.setOwnerUuid(uUID);
            gladGoombaEntity.setTamed(true);
        }
        return gladGoombaEntity;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isInSittingPose()) {
            if (sittingTimer != 0)
                event.getController().setAnimation(new AnimationBuilder().addAnimation("sit", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("sitting", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
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
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

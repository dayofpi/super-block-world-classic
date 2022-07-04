package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.goals.FollowMamaGoal;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BabyYoshiEntity extends AnimalEntity implements IAnimatable {
    public static final int MAX_AGE = Math.abs(-24000);
    private final AnimationFactory FACTORY = new AnimationFactory(this);
    private int yoshiAge;

    public BabyYoshiEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBabyYoshiAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.YOSHI_FRUIT), false));
        this.goalSelector.add(4, new FollowMamaGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("waddle", true));
            return PlayState.CONTINUE;
        } else return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            this.setYoshiAge(this.yoshiAge + 1);
        }

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.getYoshiAge());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setYoshiAge(nbt.getInt("Age"));
    }

    private int getYoshiAge() {
        return this.yoshiAge;
    }

    private void setYoshiAge(int yoshiAge) {
        this.yoshiAge = yoshiAge;
        if (this.yoshiAge >= MAX_AGE) {
            this.growUp();
        }

    }

    private void increaseAge(int seconds) {
        this.setYoshiAge(this.yoshiAge + seconds * 20);
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_AGE - this.yoshiAge);
    }


    private boolean isBreedingIngredient(ItemStack stack) {
        return stack.isOf(ModItems.YOSHI_FRUIT);
    }

    private void decrementItem(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
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
    public boolean isBaby() {
        return true;
    }

    private void eatFruit(PlayerEntity player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.increaseAge(PassiveEntity.toGrowUpAge(this.getTicksUntilGrowth()));
        this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
        this.world.playSoundFromEntity(null, this, Sounds.ENTITY_YOSHI_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isBreedingIngredient(itemStack)) {
            this.eatFruit(player, itemStack);
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    private void growUp() {
        World world = this.world;
        YoshiEntity yoshiEntity = ModEntities.YOSHI.create(this.world);
        if (world instanceof ServerWorld serverWorld && yoshiEntity != null) {
            yoshiEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            yoshiEntity.initialize(serverWorld, this.world.getLocalDifficulty(yoshiEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
            yoshiEntity.setAiDisabled(this.isAiDisabled());
            if (this.hasCustomName()) {
                yoshiEntity.setCustomName(this.getCustomName());
                yoshiEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            yoshiEntity.setPersistent();
            serverWorld.spawnEntityAndPassengers(yoshiEntity);
            this.discard();
        }
    }

    public boolean shouldDropXp() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}

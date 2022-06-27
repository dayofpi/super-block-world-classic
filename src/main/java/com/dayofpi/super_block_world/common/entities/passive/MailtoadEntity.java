package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.Toad;
import com.dayofpi.super_block_world.common.entities.ToadTrades;
import com.dayofpi.super_block_world.common.entities.brains.ToadBrain;
import com.dayofpi.super_block_world.common.entities.hostile.RottenMushroomEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class MailtoadEntity extends MerchantEntity implements Toad, IAnimatable {
    protected static final ImmutableList<SensorType<? extends Sensor<? super PassiveEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.IS_PANICKING);
    private static final TrackedData<Integer> EMOTION;
    private static final TrackedData<Integer> TOAD_STATE;
    private static final TrackedData<Boolean> TOADETTE;
    private static final TrackedData<Integer> ATTENTION_COOLDOWN;

    static {
        EMOTION = DataTracker.registerData(MailtoadEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TOAD_STATE = DataTracker.registerData(MailtoadEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TOADETTE = DataTracker.registerData(MailtoadEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        ATTENTION_COOLDOWN = DataTracker.registerData(MailtoadEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    final AnimationFactory FACTORY = new AnimationFactory(this);

    public MailtoadEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
    }

    @Override
    protected Brain.Profile<PassiveEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ToadBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<MailtoadEntity> getBrain() {
        return (Brain<MailtoadEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("toadBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("toadActivityUpdate");
        ToadBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(EMOTION, 0);
        this.dataTracker.startTracking(TOAD_STATE, 0);
        this.dataTracker.startTracking(TOADETTE, false);
        this.dataTracker.startTracking(ATTENTION_COOLDOWN, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Toadette", this.isToadette());
        nbt.putInt("AttentionCooldown", this.getAttentionCooldown());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setToadette(nbt.getBoolean("Toadette"));
        this.setAttentionCooldown(nbt.getInt("AttentionCooldown"));
    }

    public int getAttentionCooldown() {
        return this.dataTracker.get(ATTENTION_COOLDOWN);
    }

    protected void setAttentionCooldown(int attentionCooldown) {
        this.dataTracker.set(ATTENTION_COOLDOWN, attentionCooldown);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setToadette(random.nextBoolean());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            RottenMushroomEntity rottenMushroomEntity = ModEntities.ROTTEN_MUSHROOM.create(world);
            if (rottenMushroomEntity != null) {
                rottenMushroomEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                rottenMushroomEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    rottenMushroomEntity.setCustomName(this.getCustomName());
                    rottenMushroomEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                world.spawnEntity(rottenMushroomEntity);
                this.discard();
            }
        } else {
            super.onStruckByLightning(world, lightning);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            List<Entity> baddies = world.getOtherEntities(this, this.getBoundingBox().expand(4, 4, 4), entity -> entity instanceof HostileEntity);
            if (!this.isScared()) {
                if (this.isCheering() || this.forwardSpeed != 0) {
                    if (!this.world.isClient && this.random.nextFloat() < 0.005F) {
                        this.setToadState(0);
                    }
                }

                if (this.world.getTimeOfDay() == 18000) {
                    for (TradeOffer tradeOffer : this.getOffers()) {
                        tradeOffer.clearSpecialPrice();
                        tradeOffer.resetUses();
                    }
                }

                if (!baddies.isEmpty()) {
                    this.setToadState(2);
                }
                if (!this.isHappy() && this.random.nextFloat() < 0.003F) {
                    this.setEmotion(1);
                } else if (this.random.nextFloat() < 0.005F) {
                    this.setEmotion(0);
                }
            } else if (baddies.isEmpty()) {
                this.setToadState(0);
            }
        }
    }

    public int getMinAmbientSoundDelay() {
        return 130;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isCheering() || this.isScared() ? null : Sounds.ENTITY_TOAD_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_TOAD_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_TOAD_DEATH;
    }

    @Override
    public void trade(TradeOffer offer) {
        offer.use();
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
        this.afterUsing(offer);
    }

    @Override
    protected void afterUsing(TradeOffer offer) {
        if (offer.shouldRewardPlayerExperience()) {
            int i = 3 + this.random.nextInt(4);
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY() + 0.5, this.getZ(), i));
        }
    }

    @Override
    public boolean isLeveledMerchant() {
        return false;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected SoundEvent getTradingSound(boolean sold) {
        return sold ? Sounds.ITEM_COIN : Sounds.ENTITY_TOAD_AMBIENT;
    }

    @Override
    public SoundEvent getYesSound() {
        return Sounds.ENTITY_TOAD_CHEER;
    }

    @Override
    public void onSellingItem(ItemStack stack) {
        if (!this.world.isClient && this.ambientSoundChance > -this.getMinAmbientSoundDelay() + 20) {
            this.ambientSoundChance = -this.getMinAmbientSoundDelay();
            this.playSound(this.getTradingSound(!stack.isEmpty()), this.getSoundVolume(), 1.0F);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isAlive() && !this.hasCustomer() && !this.isBaby()) {
            if (this.getOffers().isEmpty()) {
                return ActionResult.success(this.world.isClient);
            }
            if (!this.world.isClient) {
                this.setCustomer(player);
                this.sendOffers(player, this.getDisplayName(), 1);
                this.setAttentionCooldown(30);
            }
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void fillRecipes() {
        TradeOffers.Factory[] factories1 = ToadTrades.MAILTOAD_TRADES.get(1);
        TradeOffers.Factory[] factories2 = ToadTrades.MAILTOAD_TRADES.get(2);
        if (factories1 == null || factories2 == null) {
            return;
        }
        TradeOfferList tradeOfferList = this.getOffers();
        this.fillRecipesFromPool(tradeOfferList, factories1, 4);
        int i = this.random.nextInt(factories2.length);
        TradeOffers.Factory factory = factories2[i];
        TradeOffer tradeOffer = factory.create(this, this.random);
        if (tradeOffer != null) {
            tradeOfferList.add(tradeOffer);
        }
    }

    @Override
    public boolean canRefreshTrades() {
        return true;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public boolean isToadette() {
        return dataTracker.get(TOADETTE);
    }

    public void setToadette(boolean toadette) {
        this.dataTracker.set(TOADETTE, toadette);
    }

    public boolean isHappy() {
        return dataTracker.get(EMOTION) == 1;
    }

    public boolean isAnnoyed() {
        return dataTracker.get(EMOTION) == 2;
    }

    public void setEmotion(int emotion) {
        if (emotion < 0 || emotion > 3) {
            emotion = 0;
        }
        this.dataTracker.set(EMOTION, emotion);
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public boolean isWaving() {
        return dataTracker.get(TOAD_STATE) == 1;
    }

    public boolean isScared() {
        return dataTracker.get(TOAD_STATE) == 2;
    }

    public boolean isCheering() {
        return dataTracker.get(TOAD_STATE) == 3;
    }

    public void setToadState(int state) {
        this.dataTracker.set(TOAD_STATE, state);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            if (this.isScared())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
        } else {
            if (this.isWaving())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("wave", true));
            else if (this.isScared())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("scared", true));
            else if (this.isCheering())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("cheer", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }
}

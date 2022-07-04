package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.passive.ToadEntity;
import com.dayofpi.super_block_world.common.entities.goals.MummyMeTargetGoal;
import com.dayofpi.super_block_world.registry.ModCriteria;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

@SuppressWarnings("unused")
public class MummyMeEntity extends HostileEntity implements IAnimatable {
    private static final TrackedData<Boolean> TOADETTE;
    private static final TrackedData<Boolean> CONVERTING;
    private static final TrackedData<Boolean> HIDDEN;
    private static final TrackedData<Integer> COLOR;
    private static final TrackedData<Integer> COINS_WANTED;

    static {
        TOADETTE = DataTracker.registerData(MummyMeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CONVERTING = DataTracker.registerData(MummyMeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        HIDDEN = DataTracker.registerData(MummyMeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        COLOR = DataTracker.registerData(MummyMeEntity.class, TrackedDataHandlerRegistry.INTEGER);
        COINS_WANTED = DataTracker.registerData(MummyMeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    AnimationFactory factory = new AnimationFactory(this);
    @Nullable
    private BlockPos targetPos = null;
    @Nullable
    private UUID converter;
    private int conversionTimer;

    public MummyMeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMummyMeAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D);
    }

    public static boolean canMummyMeSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean isBlockValid = world.getBlockState(pos.down()).isIn(BlockTags.DIRT) || world.getBlockState(pos.down()).isIn(BlockTags.SAND);
        return isBlockValid && isSpawnDark((ServerWorldAccess) world, pos, random);
    }

    protected void initGoals() {
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.9D));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new MummyMeTargetGoal<>(this, PlayerEntity.class));
        this.targetSelector.add(3, new MummyMeTargetGoal<>(this, ToadEntity.class, true, toadEntity -> !toadEntity.isBaby()));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HIDDEN, true);
        this.dataTracker.startTracking(CONVERTING, false);
        this.dataTracker.startTracking(COLOR, 1);
        this.dataTracker.startTracking(TOADETTE, false);
        this.dataTracker.startTracking(COINS_WANTED, 30);
    }

    public int getMinAmbientSoundDelay() {
        return 130;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundCategory(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
            }
            return;
        }
        super.handleStatus(status);
    }

    @Override
    protected void updateGoalControls() {
        super.updateGoalControls();
        this.goalSelector.setControlEnabled(Goal.Control.TARGET, !this.isHidden());
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_MUMMY_ME_AMBIENT;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Color", this.getColor());
        nbt.putBoolean("Toadette", this.isToadette());
        nbt.putInt("WantedCoins", this.getWantedCoins());
        nbt.putInt("ConversionTime", this.isConverting() ? this.conversionTimer : -1);
        if (this.converter != null) {
            nbt.putUuid("ConversionPlayer", this.converter);
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setColor(nbt.getInt("Color"));
        this.setToadette(nbt.getBoolean("Toadette"));
        this.setWantedCoins(nbt.getInt("WantedCoins"));
        if (nbt.contains("ConversionTime", 99) && nbt.getInt("ConversionTime") > -1) {
            this.setConverting(nbt.containsUuid("ConversionPlayer") ? nbt.getUuid("ConversionPlayer") : null, nbt.getInt("ConversionTime"));
        }
    }

    private void setConverting(@Nullable UUID uuid, int delay) {
        this.converter = uuid;
        this.conversionTimer = delay;
        this.getDataTracker().set(CONVERTING, true);
        this.removeStatusEffect(StatusEffects.WEAKNESS);
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, delay, Math.min(this.world.getDifficulty().getId() - 1, 0)));
        this.world.sendEntityStatus(this, (byte) 16);
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isConverting();
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setToadette(random.nextBoolean());
        this.setColor(random.nextInt(6));
        this.setWantedCoins(UniformIntProvider.create(20, 30).get(random));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(ModItems.GOLDEN_MUSHROOM)) {
            if (this.hasStatusEffect(StatusEffects.WEAKNESS)) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                if (!this.world.isClient) {
                    this.setConverting(player.getUuid(), this.random.nextInt(2401) + 3600);
                }
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return super.interactMob(player, hand);
    }

    public boolean isConverting() {
        return this.getDataTracker().get(CONVERTING);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!this.isHidden()) this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        boolean bl = super.onKilledOther(world, other);
        if (other instanceof ToadEntity toadEntity) {
            MummyMeEntity mummyMeEntity = toadEntity.convertTo(ModEntities.MUMMY_ME, true);
            if (mummyMeEntity != null) {
                mummyMeEntity.initialize(world, world.getLocalDifficulty(mummyMeEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
                mummyMeEntity.setHealth(mummyMeEntity.getMaxHealth());
                mummyMeEntity.setPersistent();
                mummyMeEntity.setColor(toadEntity.getColor());
                mummyMeEntity.setToadette(toadEntity.isToadette());
                mummyMeEntity.setWantedCoins(toadEntity.getWantedCoins());
                mummyMeEntity.playSound(Sounds.ENTITY_MUMMY_ME_MUMMIFY, 1.0F, this.getSoundPitch());
            }
        }
        return bl;
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setColor(int type) {
        if (type < 0 || type > 6) {
            type = 0;
        }
        this.dataTracker.set(COLOR, type);
    }

    public boolean isToadette() {
        return dataTracker.get(TOADETTE);
    }

    public void setToadette(boolean toadette) {
        this.dataTracker.set(TOADETTE, toadette);
    }

    public int getWantedCoins() {
        return dataTracker.get(COINS_WANTED);
    }

    public void setWantedCoins(int coins) {
        dataTracker.set(COINS_WANTED, coins);
    }

    public boolean isHidden() {
        return this.getDataTracker().get(HIDDEN);
    }

    public void setHidden(boolean hidden) {
        this.getDataTracker().set(HIDDEN, hidden);
        if (hidden) this.setTarget(null);
        this.playSound(Sounds.ENTITY_MUMMY_ME_MAGIC, 1.0F, this.getSoundPitch());
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_HUSK_STEP;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.isHidden()) this.tryVanish();
        return super.damage(source, amount);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (this.isBaby()) {
            return 0.71f;
        }
        return 1.52f;
    }

    private void tryVanish() {
        int value = random.nextInt(8) * (random.nextBoolean() ? 1 : -1);
        BlockPos blockPos = this.getBlockPos().add(value * 1.5, 0, value * 1.5);
        if (blockPos != this.getBlockPos() && world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.down()).isSideSolidFullSquare(world, blockPos.down(), Direction.UP)) {
            this.targetPos = blockPos;
        }
    }

    @Override
    public void tickMovement() {
        if (!world.isClient()) {
            if (this.isHidden()) {
                if (this.targetPos == null) this.setHidden(false);
                else if (this.getNavigation().isIdle())
                    this.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0D);
                if (this.targetPos != null && (this.getBlockPos() == this.targetPos || this.squaredDistanceTo(Vec3d.ofCenter(this.targetPos)) <= 2)) {
                    this.targetPos = null;
                }
            } else {
                if (this.targetPos != null && this.isAlive()) {
                    this.setHidden(true);
                    this.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0D);
                }
            }
        }

        if (!this.world.isClient && this.isAlive() && this.isConverting()) {
            this.conversionTimer -= 1;
            if (this.conversionTimer <= 0) {
                this.finishConversion((ServerWorld) this.world);
            }
        }

        if (this.world.isClient) {
            if (this.isHidden()) {
                for (int i = 0; i < 5; ++i) {
                    this.world.addParticle(ParticleTypes.DRAGON_BREATH, true, this.getParticleX(1.0D), this.getRandomBodyY(), this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
                }
            } else if (random.nextFloat() > 0.8F) {
                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getParticleX(1.0D), this.getRandomBodyY(), this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
                }
            }
        }

        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.setOnFireFor(8);
        }
        super.tickMovement();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_MUMMY_ME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_MUMMY_ME_DEATH;
    }

    private void finishConversion(ServerWorld world) {
        ToadEntity toadEntity = this.convertTo(ModEntities.TOAD, true);
        if (toadEntity != null) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = this.getEquippedStack(equipmentSlot);
                if (itemStack.isEmpty()) continue;
                if (EnchantmentHelper.hasBindingCurse(itemStack)) {
                    toadEntity.getStackReference(equipmentSlot.getEntitySlotId() + 300).set(itemStack);
                    continue;
                }
                double d = this.getDropChance(equipmentSlot);
                if (!(d > 1.0)) continue;
                this.dropStack(itemStack);
            }

            toadEntity.setColor(this.getColor());
            toadEntity.setToadette(this.isToadette());
            toadEntity.setWantedCoins(MathHelper.clamp(this.getWantedCoins() + random.nextInt(3) - random.nextInt(4), 20, 40));
            toadEntity.setHealth(toadEntity.getMaxHealth());
            toadEntity.initialize(world, world.getLocalDifficulty(toadEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
            if (this.converter != null) {
                PlayerEntity playerEntity = world.getPlayerByUuid(this.converter);
                if (playerEntity instanceof ServerPlayerEntity) {
                    ModCriteria.CURE_TOAD.trigger((ServerPlayerEntity) playerEntity);
                }
            }
            toadEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
        } else event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

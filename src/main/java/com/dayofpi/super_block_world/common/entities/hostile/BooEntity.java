package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.goals.StealItemGoal;
import com.dayofpi.super_block_world.common.entities.misc.GhostEssenceEntity;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class BooEntity extends TameableEntity implements Monster {
    private static final TrackedData<Boolean> WEAKENED;
    private static final TrackedData<Integer> BOO_COLOR;
    private static final float MIN_ALPHA = 0.5F;

    static {
        WEAKENED = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOO_COLOR = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    private float alpha;

    public BooEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.lookControl = new YawAdjustingLookControl(this, 20);
    }

    public static DefaultAttributeContainer.Builder createBooAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @SuppressWarnings("unused")
    public static boolean canBooSpawn(EntityType<BooEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return HostileEntity.isSpawnDark((ServerWorldAccess) world, pos, random);
    }

    public float getAlpha() {
        return this.alpha;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(4, new StealItemGoal(this));
        this.goalSelector.add(5, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, true));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(5, new UntamedActiveTargetGoal<>(this, PlayerEntity.class, false, entity -> true));
    }

    private ActionResult doWildInteraction(ItemStack itemStack, PlayerEntity player, ActionResult result) {
        if (itemStack.isOf(ModItems.POISON_MUSHROOM)) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            if (!this.isSilent()) {
                this.world.playSound(null, this.getX(), this.getY(), this.getZ(), Sounds.ENTITY_GENERIC_EAT, this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
            }
            if (!this.world.isClient) {
                if (this.random.nextInt(10) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                } else {
                    this.world.sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
            }
            return ActionResult.success(this.world.isClient);
        }
        return result;
    }

    private ActionResult doTamedInteraction(ItemStack itemStack, PlayerEntity player, ActionResult result, Hand hand) {
        if (!(itemStack.getItem() instanceof DyeItem)) {
            if (this.isOwner(player)) {
                if (itemStack.isEmpty() && !this.getMainHandStack().isEmpty()) {
                    player.setStackInHand(hand, this.getMainHandStack().copy());
                    this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
                this.setSitting(!this.isSitting());
                this.jumping = false;
                this.navigation.stop();
                return ActionResult.SUCCESS;
            }
            return result;
        }
        DyeColor dyeColor = ((DyeItem) itemStack.getItem()).getColor();
        if (dyeColor != this.getBooColor()) {
            this.setBooColor(dyeColor);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }
        return result;
    }

    public DyeColor getBooColor() {
        return DyeColor.byId(this.dataTracker.get(BOO_COLOR));
    }

    public void setBooColor(DyeColor color) {
        this.dataTracker.set(BOO_COLOR, color.getId());
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ActionResult result = super.interactMob(player, hand);
        if (this.isTamed()) {
            return this.doTamedInteraction(itemStack, player, result, hand);
        } else return this.doWildInteraction(itemStack, player, result);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(WEAKENED, false);
        this.dataTracker.startTracking(BOO_COLOR, DyeColor.WHITE.getId());
    }

    public boolean isWeakened() {
        return this.dataTracker.get(WEAKENED);
    }

    public void setWeakened(boolean weakened) {
        this.dataTracker.set(WEAKENED, weakened);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Weakened", this.isWeakened());
        nbt.putByte("BooColor", (byte) this.getBooColor().getId());

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setWeakened(nbt.getBoolean("Weakened"));
        if (nbt.contains("BooColor", 99)) {
            this.setBooColor(DyeColor.byId(nbt.getInt("BooColor")));
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_BOO_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_BOO_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_BOO_DEATH;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.playSound(Sounds.ENTITY_BOO_ATTACK, 1.0F, this.getSoundPitch());
        return super.tryAttack(target);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(false);
        return birdNavigation;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean damage = super.damage(source, amount);
        if (damage)
            this.setSitting(false);
        if (!this.isWeakened() && !source.isOutOfWorld())
            return false;
        return damage;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        GhostEssenceEntity essenceEntity = new GhostEssenceEntity(world, this.getX(), this.getY(), this.getZ());
        world.spawnEntity(essenceEntity);
        super.onDeath(damageSource);
    }

    @Override
    public void tick() {
        int i = this.getWorld().getLightLevel(LightType.BLOCK, this.getBlockPos());

        if (i > 6)
            alpha += 0.05F;
        else
            alpha -= 0.05F;
        alpha = MathHelper.clamp(alpha, MIN_ALPHA, 1.0F);
        this.setWeakened(this.alpha == 1.0F);
        super.tick();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
}

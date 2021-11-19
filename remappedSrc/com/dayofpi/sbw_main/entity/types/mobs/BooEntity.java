package com.dayofpi.sbw_main.entity.types.mobs;

import com.dayofpi.sbw_main.SoundList;
import com.dayofpi.sbw_main.entity.goals.BooFollowOwnerGoal;
import com.dayofpi.sbw_main.entity.goals.BooPickupItemGoal;
import com.dayofpi.sbw_main.entity.goals.BooSitGoal;
import com.dayofpi.sbw_main.entity.types.bases.AbstractGhost;
import com.dayofpi.sbw_main.entity.types.bases.EnemyEntity;
import com.dayofpi.sbw_main.item.registry.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class BooEntity extends AbstractGhost implements Flutterer, Tameable {
    private static final TrackedData<Boolean> HIDING;
    private static final TrackedData<Boolean> DABBING;
    private static final TrackedData<Integer> BOO_COLOR;
    protected static final TrackedData<Byte> TAMEABLE_FLAGS;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    private boolean sitting;

    static {
        HIDING = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        DABBING = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOO_COLOR = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.INTEGER);
        OWNER_UUID = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
        TAMEABLE_FLAGS = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

    public BooEntity(EntityType<? extends BooEntity> entityType, World world) {
        super(entityType, world);
        this.onTamedChanged();
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return EnemyEntity.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.65D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new AvoidSunlightGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(2, new BooSitGoal(this));
        this.goalSelector.add(8, new BooWanderAroundGoal());
        this.goalSelector.add(2, new BooFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.targetSelector.add(2, new BooPickupItemGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, Ingredient.ofItems(ModItems.POISON_MUSHROOM), false));
        this.targetSelector.add(1, new BooTargetGoal<>(this, PlayerEntity.class));
    }

    public DyeColor getBooColor() {
        return DyeColor.byId(this.dataTracker.get(BOO_COLOR));
    }

    public void setBooColor(DyeColor color) {
        this.dataTracker.set(BOO_COLOR, color.getId());
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTamed() && this.age > 2400;
    }

    public boolean isDabbing() {
        return this.dataTracker.get(DABBING);
    }
    public void setDabbing(boolean dabbing) {
        this.dataTracker.set(DABBING, dabbing);
    }

    public void setHiding(boolean hiding) {
        this.dataTracker.set(HIDING, hiding);
    }
    public boolean isHiding() {
        return this.dataTracker.get(HIDING);
    }

    protected boolean shouldSwimInFluids() {
        return false;
    }
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5F;
    }
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HIDING, false);
        this.dataTracker.startTracking(DABBING, false);
        this.dataTracker.startTracking(BOO_COLOR, DyeColor.WHITE.getId());
        this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setDabbing(world.getRandom().nextDouble() < 0.02D);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.getEquippedStack(EquipmentSlot.MAINHAND).decrement(1);
        this.world.spawnEntity(itemEntity);
        itemEntity.setPickupDelay(40);
    }

    private void spit(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0D, this.getZ() + this.getRotationVector().z, stack);
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.getUuid());
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
            this.world.spawnEntity(itemEntity);
        }
    }

    protected void drop(DamageSource source) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.dropStack(itemStack);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.drop(source);
    }

    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItem(itemStack.split(i - 1));
            }

            this.spit(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
            this.sendPickup(item, itemStack.getCount());
            item.discard();
        }

    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.isOf(ModItems.POISON_MUSHROOM) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                ActionResult actionResult = super.interactMob(player, hand);
                if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                    if (!(item instanceof DyeItem)) {
                        if (this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                        this.setSitting(!this.isSitting());
                        this.jumping = false;
                        this.setVelocity(this.getVelocity().x, 0, this.getVelocity().z);
                        this.navigation.stop();
                        this.setTarget(null);
                        return ActionResult.SUCCESS;
                    } else {
                        this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
                        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        }
                    } else {
                        DyeColor dyeColor = ((DyeItem)item).getColor();
                        if (dyeColor != this.getBooColor()) {
                            this.setBooColor(dyeColor);
                            if (!player.getAbilities().creativeMode) {
                                itemStack.decrement(1);
                            }

                            return ActionResult.SUCCESS;
                        }
                    }
                }

                return actionResult;
            } else if (itemStack.isOf(ModItems.POISON_MUSHROOM)) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Dab", this.isDabbing());
        nbt.putByte("CollarColor", (byte)this.getBooColor().getId());
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        nbt.putBoolean("Sitting", this.sitting);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID uUID2;
        if (nbt.contains("CollarColor", 99)) {
            this.setBooColor(DyeColor.byId(nbt.getInt("CollarColor")));
        }
        if (nbt.containsUuid("Owner")) {
            uUID2 = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID2 != null) {
            try {
                this.setOwnerUuid(uUID2);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
        this.sitting = nbt.getBoolean("Sitting");
        this.setInSittingPose(this.sitting);
        this.setDabbing(nbt.getBoolean("Dab"));
    }

    public boolean tryAttack(Entity target) {
    this.playSound(SoundList.booLaugh, 1.0F, getSoundPitch());
    return super.tryAttack(target);
    }

    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    public SoundEvent getAmbientSound() {
        return this.isHiding() ? SoundList.booShy : SoundList.booAmbient;
    }

    public SoundEvent getHurtSound(DamageSource source) {
        return SoundList.booHurt;
    }

    public SoundEvent getDeathSound() {
        return SoundList.booDeath;
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public void tickMovement() {
        super.tickMovement();
        LivingEntity target = this.getTarget();
        ParticleEffect particleType = ParticleTypes.SPLASH;

        if (this.isAlive()) {
            if (((target instanceof PlayerEntity))) {
                this.setHiding(this.isPlayerStaring((PlayerEntity) target));
            }
            if (this.isHiding()) {
                this.setVelocity(this.getVelocity().multiply(0));

                if (random.nextFloat() > 0.95F && this.airStrafingSpeed != 0) {
                    world.addParticle(particleType, this.getX() + random.nextFloat(), this.getY() + random.nextFloat(), this.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }

                if (this.random.nextInt(400) == 0 && target == null) {
                    this.setHiding(false);
                }
                if (this.isTamed()) {
                    this.setHiding(false);
                }
            }
        }
    }

    boolean isPlayerStaring(PlayerEntity player) {
        final float f = world.getLightLevel(LightType.BLOCK, this.getBlockPos());
        ItemStack itemStack = player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem()) || f > lightLimit) {
            return false;
        } else {
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);
            return e > 1.0D - 0.1D / d && player.canSee(this) && !player.isCreative();
        }
    }

    @Nullable
    public UUID getOwnerUuid() {
        return (this.dataTracker.get(OWNER_UUID)).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
        if (player instanceof ServerPlayerEntity) {
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    protected void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = ParticleTypes.HEART;
        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
        }

    }

    public void handleStatus(byte status) {
        if (status == 7) {
            this.showEmoteParticle(true);
        } else if (status == 6) {
            this.showEmoteParticle(false);
        } else {
            super.handleStatus(status);
        }

    }

    public boolean isTamed() {
        return (this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b = this.dataTracker.get(TAMEABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b | 4));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b & -5));
        }

        this.onTamedChanged();
    }

    protected void onTamedChanged() {
    }

    public boolean isInSittingPose() {
        return (this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
    }

    public void setInSittingPose(boolean inSittingPose) {
        byte b = this.dataTracker.get(TAMEABLE_FLAGS);
        if (inSittingPose) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b | 1));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b & -2));
        }

    }

    public boolean canTarget(LivingEntity target) {
        return !this.isOwner(target) && super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    public AbstractTeam getScoreboardTeam() {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.getOwner();
            if (livingEntity != null) {
                return livingEntity.getScoreboardTeam();
            }
        }

        return super.getScoreboardTeam();
    }

    public boolean isTeammate(Entity other) {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.getOwner();
            if (other == livingEntity) {
                return true;
            }

            if (livingEntity != null) {
                return livingEntity.isTeammate(other);
            }
        }

        return super.isTeammate(other);
    }

    public void onDeath(DamageSource source) {
        if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendSystemMessage(this.getDamageTracker().getDeathMessage(), Util.NIL_UUID);
        }

        super.onDeath(source);
    }


    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    static class BooTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        protected final BooEntity boo;

        public BooTargetGoal(BooEntity booEntity, Class<T> class_) {
            super(booEntity, class_, true);
            this.boo = booEntity;
        }

        public boolean canStart() {
            return !this.boo.isTamed() && super.canStart();
        }

        public boolean shouldContinue() {
            if (this.targetEntity != null && !this.targetEntity.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.POISON_MUSHROOM))
                return super.shouldContinue();
            else return false;
        }
    }

    class BooWanderAroundGoal extends Goal {
        BooWanderAroundGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return BooEntity.this.navigation.isIdle() && BooEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return BooEntity.this.navigation.isFollowingPath();
        }
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                BooEntity.this.navigation.startMovingAlong(BooEntity.this.navigation.findPathTo(new BlockPos(vec3d), 1), 0.4D);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d4 = AboveGroundTargeting.find(BooEntity.this, 8, 7, BooEntity.this.getX(), BooEntity.this.getZ(), 1.5707964F, 3, 1);
            return vec3d4 != null ? vec3d4 : NoPenaltySolidTargeting.find(BooEntity.this, 8, 4, -2, BooEntity.this.getX(), BooEntity.this.getZ(), 1.5707963705062866D);
        }
    }
}

package com.dayofpi.sbw_main.common.entity.type.mobs;

import com.dayofpi.sbw_main.util.sounds.ModSounds;
import com.dayofpi.sbw_main.common.entity.goal.*;
import com.dayofpi.sbw_main.common.entity.type.bases.AbstractGhost;
import com.dayofpi.sbw_main.common.entity.type.bases.EnemyEntity;
import com.dayofpi.sbw_main.registry.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
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

import java.util.Optional;
import java.util.UUID;

public class BooEntity extends AbstractGhost implements Flutterer, Tameable {
    protected static final TrackedData<Byte> TAMEABLE_FLAGS;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    private static final TrackedData<Boolean> HIDING;
    private static final TrackedData<Boolean> DABBING;
    private static final TrackedData<Integer> FACE;
    private static final TrackedData<Integer> BOO_COLOR;

    static {
        HIDING = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        DABBING = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        FACE = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BOO_COLOR = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.INTEGER);
        OWNER_UUID = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
        TAMEABLE_FLAGS = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

    private boolean sitting;

    public BooEntity(EntityType<? extends BooEntity> entityType, World world) {
        super(entityType, world);
        this.onTamedChanged();
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    protected void onTamedChanged() {
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
        this.goalSelector.add(8, new BooWanderGoal(this));
        this.goalSelector.add(2, new BooFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.targetSelector.add(2, new BooPickupItemGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, Ingredient.ofItems(ModItems.POISON_MUSHROOM), false));
        this.targetSelector.add(1, new BooTargetGoal<>(this, PlayerEntity.class));
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public SoundEvent getAmbientSound() {
        return this.isHiding() ? ModSounds.ENTITY_BOO_SHY : ModSounds.ENTITY_BOO_AMBIENT;
    }

    public boolean isHiding() {
        return this.dataTracker.get(HIDING);
    }

    public void setHiding(boolean hiding) {
        this.dataTracker.set(HIDING, hiding);
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

    public boolean tryAttack(Entity target) {
        this.playSound(ModSounds.ENTITY_BOO_ATTACK, 1.0F, getSoundPitch());
        return super.tryAttack(target);
    }

    protected boolean shouldSwimInFluids() {
        return false;
    }

    public boolean canTarget(LivingEntity target) {
        return !this.isOwner(target) && super.canTarget(target);
    }

    protected void drop(DamageSource source) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.dropStack(itemStack);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.drop(source);
    }

    public SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_BOO_HURT;
    }

    public SoundEvent getDeathSound() {
        return ModSounds.ENTITY_BOO_DEATH;
    }

    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5F;
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sitting", this.sitting);
        nbt.putBoolean("Dab", this.isDabbing());
        nbt.putInt("Face", this.getBooFace());
        nbt.putByte("CollarColor", (byte) this.getBooColor().getId());
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    public boolean isDabbing() {
        return this.dataTracker.get(DABBING);
    }

    public int getBooFace() {
        return this.dataTracker.get(FACE);
    }

    public void setBooFace(int face) {
        this.dataTracker.set(FACE, face);
    }

    public void setDabbing(boolean dabbing) {
        this.dataTracker.set(DABBING, dabbing);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID uUID2;
        this.sitting = nbt.getBoolean("Sitting");
        this.setInSittingPose(this.sitting);
        this.setDabbing(nbt.getBoolean("Dab"));
        this.setBooFace(nbt.getInt("Face"));
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

    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTamed() && this.age > 2400;
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
                        DyeColor dyeColor = ((DyeItem) item).getColor();
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
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
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

    protected void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = ParticleTypes.HEART;
        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
        }

    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public DyeColor getBooColor() {
        return DyeColor.byId(this.dataTracker.get(BOO_COLOR));
    }

    public void setBooColor(DyeColor color) {
        this.dataTracker.set(BOO_COLOR, color.getId());
    }

    @Nullable
    public UUID getOwnerUuid() {
        return (this.dataTracker.get(OWNER_UUID)).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
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

    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
        if (player instanceof ServerPlayerEntity) {
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity) player, this);
        }
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public boolean isTamed() {
        return (this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b = this.dataTracker.get(TAMEABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 4));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -5));
        }

        this.onTamedChanged();
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setDabbing(world.getRandom().nextDouble() < 0.02D);
        this.setBooFace(random.nextInt(5));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HIDING, false);
        this.dataTracker.startTracking(DABBING, false);
        this.dataTracker.startTracking(FACE, 0);
        this.dataTracker.startTracking(BOO_COLOR, DyeColor.WHITE.getId());
        this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte) 0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            LivingEntity target = this.getTarget();
            if (((target instanceof PlayerEntity))) {
                this.setHiding(this.isPlayerStaring((PlayerEntity) target));
            }
            if (this.isHiding()) {
                this.setVelocity(this.getVelocity().multiply(0));

                if (random.nextFloat() > 0.95F && this.airStrafingSpeed != 0) {
                    world.addParticle(ParticleTypes.SPLASH, this.getX() + random.nextFloat(), this.getY() + random.nextFloat(), this.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
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
        final float level = world.getLightLevel(LightType.BLOCK, this.getBlockPos());
        ItemStack helmet = player.getInventory().armor.get(3);

        if (!helmet.isOf(Blocks.CARVED_PUMPKIN.asItem()) && level < lightLimit) {
            Vec3d rotation = player.getRotationVec(1.0F).normalize();
            Vec3d difference = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double length = difference.length();
            difference = difference.normalize();
            double product = rotation.dotProduct(difference);
            return product > 1.0D - 0.1D / length && player.canSee(this) && !player.isCreative();
        } else return false;
    }

    public void onDeath(DamageSource source) {
        if (this.world.isClient) {
            world.addParticle(ParticleTypes.SOUL, getX() + 0.5, getY() + 0.5, getZ() + 0.5, 0, 0, 0);
        } else {
            if (this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
                this.getOwner().sendSystemMessage(this.getDamageTracker().getDeathMessage(), Util.NIL_UUID);
            }
        }

        super.onDeath(source);
    }

    public boolean isInSittingPose() {
        return (this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
    }

    public void setInSittingPose(boolean inSittingPose) {
        byte flags = this.dataTracker.get(TAMEABLE_FLAGS);
        if (inSittingPose) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (flags | 1));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (flags & -2));
        }

    }

    public AbstractTeam getScoreboardTeam() {
        if (this.isTamed()) {
            LivingEntity owner = this.getOwner();
            if (owner != null) {
                return owner.getScoreboardTeam();
            }
        }

        return super.getScoreboardTeam();
    }

    public boolean isTeammate(Entity other) {
        if (this.isTamed()) {
            LivingEntity owner = this.getOwner();
            if (other == owner) {
                return true;
            }

            if (owner != null) {
                return owner.isTeammate(other);
            }
        }

        return super.isTeammate(other);
    }
}

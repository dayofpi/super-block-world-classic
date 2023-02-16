package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.goals.BooAttackGoal;
import com.dayofpi.super_block_world.entity.entities.goals.BooSitGoal;
import com.dayofpi.super_block_world.entity.entities.goals.StealItemGoal;
import com.dayofpi.super_block_world.entity.entities.misc.GhostEssenceEntity;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.entity.entities.BooFace;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class BooEntity extends TameableEntity implements Monster {
    private static final TrackedData<String> BOO_FACE;
    private static final TrackedData<Boolean> WEAKENED;
    private static final TrackedData<Boolean> SHY;
    private static final TrackedData<Integer> BOO_COLOR;
    private static final float MIN_ALPHA = 0.6F;

    static {
        BOO_FACE = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.STRING);
        WEAKENED = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SHY = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOO_COLOR = DataTracker.registerData(BooEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    private float alpha;
    private boolean shy;

    public BooEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.lookControl = new YawAdjustingLookControl(this, 20);
    }

    public static DefaultAttributeContainer.Builder createBooAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<BooEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return HostileEntity.isSpawnDark((ServerWorldAccess) world, pos, random) && pos.getY() >= 63;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTamed();
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return -world.getPhototaxisFavor(pos);
    }

    public float getAlpha() {
        return this.alpha;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BooSitGoal(this));
        this.goalSelector.add(3, new StealItemGoal(this));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, true));
        this.goalSelector.add(5, new BooAttackGoal(this, 1.0, true));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new UntamedActiveTargetGoal<>(this, PlayerEntity.class, false, entity -> true));
        this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
    }
    public BooFace getBooFace() {
        return BooFace.fromName(this.dataTracker.get(BOO_FACE));
    }

    public void setBooFace(String name) {
        this.dataTracker.set(BOO_FACE, name);
    }

    public DyeColor getBooColor() {
        return DyeColor.byId(this.dataTracker.get(BOO_COLOR));
    }

    public void setBooColor(DyeColor color) {
        this.dataTracker.set(BOO_COLOR, color.getId());
    }

    private boolean isPlayerStaring(PlayerEntity player) {
        Vec3d rotation = player.getRotationVec(1.0F).normalize();
        Vec3d difference = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double length = difference.length();
        difference = difference.normalize();
        double product = rotation.dotProduct(difference);
        return product > 1.0D - 0.1D / length && player.canSee(this) && !player.isCreative();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            if (this.isTamed() && this.isOwner(player)) {
                return ActionResult.SUCCESS;
            } else {
                return !this.isBreedingItem(itemStack) || !(this.getHealth() < this.getMaxHealth()) && this.isTamed() ? ActionResult.PASS : ActionResult.SUCCESS;
            }
        } else {
            ActionResult actionResult;
            if (this.isTamed()) {
                if (this.isOwner(player)) {
                    if (!(item instanceof DyeItem)) {
                        if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                            this.eat(player, hand, itemStack);
                            this.heal(2.0F);
                            return ActionResult.CONSUME;
                        }
                        else if (this.getMainHandStack().isEmpty() && !itemStack.isEmpty()) {
                            this.setStackInHand(Hand.MAIN_HAND, itemStack.split(1));
                            return ActionResult.CONSUME;
                        }
                        else if (!this.getMainHandStack().isEmpty() && itemStack.isEmpty()){
                            player.giveItemStack(this.getMainHandStack().copy());
                            this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                            player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, 1.0F);
                            return ActionResult.CONSUME;
                        }

                        actionResult = super.interactMob(player, hand);
                        if (!actionResult.isAccepted() || this.isBaby()) {
                            this.setSitting(!this.isSitting());
                        }

                        return actionResult;
                    }

                    DyeColor dyeColor = ((DyeItem) item).getColor();
                    if (dyeColor != this.getBooColor()) {
                        this.setBooColor(dyeColor);
                        if (!player.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }

                        this.setPersistent();
                        return ActionResult.CONSUME;
                    }
                }
            } else if (this.isBreedingItem(itemStack)) {
                this.eat(player, hand, itemStack);
                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }

                this.setPersistent();
                return ActionResult.CONSUME;
            }

            actionResult = super.interactMob(player, hand);
            if (actionResult.isAccepted()) {
                this.setPersistent();
            }

            return actionResult;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.POISON_MUSHROOM);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHY, false);
        this.dataTracker.startTracking(WEAKENED, false);
        this.dataTracker.startTracking(BOO_COLOR, DyeColor.WHITE.getId());
        this.dataTracker.startTracking(BOO_FACE, BooFace.CHEEKY.id());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setBooFace(BooFace.LIST.get(random.nextInt(BooFace.LIST.size())).id());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public boolean isWeakened() {
        return this.dataTracker.get(WEAKENED);
    }

    public void setWeakened(boolean weakened) {
        this.dataTracker.set(WEAKENED, weakened);
    }

    public boolean isShy() {
        return this.dataTracker.get(SHY);
    }

    public void setShy(boolean shy) {
        this.dataTracker.set(SHY, shy);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("BooFace", this.getBooFace().id());
        nbt.putBoolean("Shy", this.isShy());
        nbt.putBoolean("Weakened", this.isWeakened());
        nbt.putByte("BooColor", (byte) this.getBooColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBooFace(nbt.getString("BooFace"));
        this.setShy(nbt.getBoolean("Shy"));
        this.setWeakened(nbt.getBoolean("Weakened"));
        if (nbt.contains("BooColor", 99)) {
            this.setBooColor(DyeColor.byId(nbt.getInt("BooColor")));
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isShy() ? Sounds.ENTITY_BOO_SHY : Sounds.ENTITY_BOO_AMBIENT;
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
    public boolean canBeLeashedBy(PlayerEntity player) {
        return super.canBeLeashedBy(player) && this.isTamed();
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
        if (!this.isWeakened() && !source.isOutOfWorld())
            return false;
        boolean damage = super.damage(source, amount);
        if (damage)
            this.setSitting(false);
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
            this.alpha += 0.05F;
        else
            this.alpha -= 0.05F;
        this.alpha = MathHelper.clamp(alpha, MIN_ALPHA, 1.0F);
        this.setWeakened(this.alpha == 1.0F);
        if (this.getTarget() instanceof PlayerEntity playerEntity) {
            if (this.isPlayerStaring(playerEntity)) {
                this.setShy(true);
                this.world.sendEntityStatus(this, EntityStatuses.TAME_OCELOT_SUCCESS);
            } else {
                this.setShy(false);
                this.world.sendEntityStatus(this, EntityStatuses.TAME_OCELOT_FAILED);
            }
        } else {
            this.setShy(false);
            this.world.sendEntityStatus(this, EntityStatuses.TAME_OCELOT_FAILED);
        }
        super.tick();
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.TAME_OCELOT_SUCCESS)
            this.shy = true;
        else if (status == EntityStatuses.TAME_OCELOT_FAILED)
            this.shy = false;
    }

    public boolean isClientShy() {
        return this.shy;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isClientShy() && this.world.isClient && random.nextFloat() > 0.9F) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.SPLASH, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0, 0, 0);
            }
        }
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

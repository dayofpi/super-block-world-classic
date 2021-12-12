package com.dayofpi.sbw_main.common.entity.type.mobs;

import com.dayofpi.sbw_main.util.sounds.ModSounds;
import com.dayofpi.sbw_main.registry.entity.ModEntities;
import com.dayofpi.sbw_main.common.entity.type.bases.EnemyEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.UUID;

public class KoopaEntity extends EnemyEntity implements Angerable, ItemSteerable, Saddleable {
    private static final TrackedData<Integer> TYPE;
    private static final TrackedData<Integer> ANGER_TIME;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;
    private static final UniformIntProvider ANGER_TIME_RANGE;

    static {
        TYPE = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(10, 20);
        SADDLED = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    private final SaddledComponent saddledComponent;

    @Nullable
    private UUID targetUuid;

    public KoopaEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
    }

    public static boolean canSpawn(EntityType<? extends KoopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).allowsSpawning(world, pos, type);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return EnemyEntity.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, Ingredient.ofItems(Items.CARROT_ON_A_STICK), false));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SADDLED, false);
        this.dataTracker.startTracking(TYPE, 0);
        this.dataTracker.startTracking(ANGER_TIME, 0);
        this.dataTracker.startTracking(BOOST_TIME, 0);
    }

    @Override
    public boolean canBeControlledByRider() {
        Entity entity = this.getPrimaryPassenger();
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        } else {
            return livingEntity.getMainHandStack().isOf(Items.CARROT_ON_A_STICK) || livingEntity.getOffHandStack().isOf(Items.CARROT_ON_A_STICK);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.world.isClient) {
                player.startRiding(this);
            }

            return ActionResult.success(this.world.isClient);
        } else {
            ActionResult actionResult = super.interactMob(player, hand);
            if (!actionResult.isAccepted()) {
                ItemStack itemStack = player.getStackInHand(hand);
                return itemStack.isOf(Items.SADDLE) ? itemStack.useOnEntity(player, this, hand) : ActionResult.PASS;
            } else {
                return actionResult;
            }
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
        nbt.putInt("Type", this.getKoopaType());
        this.writeAngerToNbt(nbt);
    }

    public int getKoopaType() {
        return this.dataTracker.get(TYPE);
    }

    private void setKoopaType(int color) {
        this.dataTracker.set(TYPE, color);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setKoopaType(nbt.getInt("Type"));
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
        this.readAngerFromNbt(this.world, nbt);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (BOOST_TIME.equals(data) && this.world.isClient) {
            this.saddledComponent.boost();
        }

        super.onTrackedDataSet(data);
    }

    public void tickMovement() {
        super.tickMovement();

        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld) this.world, true);
        }

        if (this.hasPassengers() && this.getFirstPassenger() instanceof HammerBroEntity) {
            this.setTarget(((HammerBroEntity) this.getFirstPassenger()).getTarget());
        }
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_KOOPA_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_KOOPA_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_KOOPA_DEATH;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setKoopaType(random.nextInt(2));
        if (world.getRandom().nextInt(30) == 0) {
            HammerBroEntity hammerBroEntity = ModEntities.HAMMER_BRO.create(this.world);
            if (hammerBroEntity != null) {
                hammerBroEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                hammerBroEntity.initialize(world, difficulty, spawnReason, null, null);
                hammerBroEntity.startRiding(this);
                this.saddledComponent.setSaddled(true);
            }
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public boolean canBeSaddled() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void saddle(@Nullable SoundCategory sound) {
        this.saddledComponent.setSaddled(true);
        if (sound != null) {
            this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_PIG_SADDLE, sound, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddled() {
        return this.saddledComponent.isSaddled();
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.isSaddled()) {
            this.dropItem(Items.SADDLE);
        }
    }

    public Entity getPrimaryPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int[][] is = Dismounting.getDismountOffsets(direction);
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (EntityPose entityPose : passenger.getPoses()) {
                Box box = passenger.getBoundingBox(entityPose);

                for (int[] js : is) {
                    mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                    double d = this.world.getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.world, passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }

        }
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public boolean consumeOnAStickItem() {
        return this.saddledComponent.boost(this.getRandom());
    }

    @Override
    public void setMovementInput(Vec3d movementInput) {
        super.travel(movementInput);
    }

    @Override
    public float getSaddledSpeed() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.5F + (this.getKoopaType() * 0.3F);
    }

    @Override
    public void travel(Vec3d movementInput) {
        this.travel(this, this.saddledComponent, movementInput);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.7D;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Nullable
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
}

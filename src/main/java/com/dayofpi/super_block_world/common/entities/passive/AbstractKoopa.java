package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.brains.KoopaBrain;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.VibrationListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.UUID;

public abstract class AbstractKoopa extends PassiveEntity implements VibrationListener.Callback, Saddleable, ItemSteerable, Angerable {
    private static final ImmutableList<SensorType<? extends Sensor<? super AbstractKoopa>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_ADULT);
    static final TrackedData<Boolean> SADDLED;
    static final TrackedData<Integer> BOOST_TIME;
    private static final TrackedData<Integer> KOOPA_COLOR;
    private static final TrackedData<Integer> ANGER_TIME;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private static final Logger logger = Main.LOGGER;
    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState dancingAnimationState = new AnimationState();
    final SaddledComponent saddledComponent;
    private final EntityGameEventHandler<VibrationListener> gameEventHandler;
    private int danceTime;
    @Nullable
    private UUID targetUuid;

    static {
        SADDLED = DataTracker.registerData(AbstractKoopa.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(AbstractKoopa.class, TrackedDataHandlerRegistry.INTEGER);
        KOOPA_COLOR = DataTracker.registerData(AbstractKoopa.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME = DataTracker.registerData(AbstractKoopa.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(10, 20);

    }

    protected AbstractKoopa(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
        this.gameEventHandler = new EntityGameEventHandler<>(new VibrationListener(new EntityPositionSource(this, this.getStandingEyeHeight()), 16, this, null, 0.0f, 0));
    }

    private boolean shouldDance() {
        return this.danceTime > 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_KOOPA_TROOPA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_KOOPA_TROOPA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_KOOPA_TROOPA_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }

    @Nullable
    public Entity getPrimaryPassenger() {
        Entity entity = this.getFirstPassenger();
        return entity != null && this.canBeControlledByRider(entity) ? entity : null;
    }

    private boolean canBeControlledByRider(Entity entity) {
        if (this.isSaddled() && entity instanceof PlayerEntity playerEntity) {
            return playerEntity.getMainHandStack().isOf(Items.CARROT_ON_A_STICK) || playerEntity.getOffHandStack().isOf(Items.CARROT_ON_A_STICK);
        } else {
            return false;
        }
    }

    public void travel(Vec3d movementInput) {
        this.travel(this, this.saddledComponent, movementInput);
    }

    public float getSaddledSpeed() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.225F + (this.getKoopaColor() * 0.1F);
    }

    public void setMovementInput(Vec3d movementInput) {
        super.travel(movementInput);
    }

    public boolean consumeOnAStickItem() {
        return this.saddledComponent.boost(this.getRandom());
    }

    public boolean canBeSaddled() {
        return this.isAlive() && !this.isBaby() && !this.hasAngerTime();
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
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.7D;
    }

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

    @Override
    public void tick() {
        if (world.isClient()) {
            if (this.shouldDance()) {
                --this.danceTime;
                this.dancingAnimationState.startIfNotRunning(this.age);
            }
            else this.dancingAnimationState.stop();

            if (this.shouldWalk()) {
                this.walkingAnimationState.startIfNotRunning(this.age);
                this.idlingAnimationState.stop();
            } else {
                this.idlingAnimationState.startIfNotRunning(this.age);
                this.walkingAnimationState.stop();
            }
        }
        else {
            this.gameEventHandler.getListener().tick(this.world);
        }
        super.tick();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (random.nextInt(10) == 0) this.setKoopaColor(1);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.isSaddled()) {
            this.dropItem(Items.SADDLE);
        }
        if (this.random.nextInt(6) == 0) {
            if (this.getKoopaColor() == 0) this.dropItem(ModItems.GREEN_SHELL);
            if (this.getKoopaColor() == 1) this.dropItem(ModItems.RED_SHELL);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, true);
        }
    }

    @Override
    protected Brain.Profile<AbstractKoopa> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return KoopaBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<AbstractKoopa> getBrain() {
        return (Brain<AbstractKoopa>) super.getBrain();
    }


    protected boolean shouldWalk() {
        return this.getVelocity().horizontalLengthSquared() > 1.0E-6;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    public int getKoopaColor() {
        return this.dataTracker.get(KOOPA_COLOR);
    }

    public void setKoopaColor(int color) {
        this.dataTracker.set(KOOPA_COLOR, color);
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    @Nullable
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID targetUuid) {
        this.targetUuid = targetUuid;
    }


    public void onTrackedDataSet(TrackedData<?> data) {
        if (BOOST_TIME.equals(data) && this.world.isClient) {
            this.saddledComponent.boost();
        }
        super.onTrackedDataSet(data);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SADDLED, false);
        this.dataTracker.startTracking(BOOST_TIME, 0);
        this.dataTracker.startTracking(KOOPA_COLOR, 0);
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
        nbt.putInt("KoopaColor", this.getKoopaColor());
        this.writeAngerToNbt(nbt);
        VibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.gameEventHandler.getListener()).resultOrPartial(logger::error).ifPresent(nbtElement -> nbt.put("listener", nbtElement));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
        this.setKoopaColor(nbt.getInt("KoopaColor"));
        this.readAngerFromNbt(this.world, nbt);
        if (nbt.contains("listener", NbtElement.COMPOUND_TYPE)) {
            VibrationListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener"))).resultOrPartial(logger::error).ifPresent(vibrationListener -> this.gameEventHandler.setListener(vibrationListener, this.world));
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public TagKey<GameEvent> getTag() {
        return GameEventTags.ALLAY_CAN_LISTEN;
    }

    @Override
    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, GameEvent.Emitter emitter) {
        return this.world == world && !this.isRemoved() && !this.isAiDisabled();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.EARS_TWITCH)
            this.danceTime = 60;
        else super.handleStatus(status);
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        if (event == GameEvent.NOTE_BLOCK_PLAY) {
            world.sendEntityStatus(this, EntityStatuses.EARS_TWITCH);
        }
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("koopaBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("koopaActivityUpdate");
        KoopaBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public boolean isSaddled() {
        return this.saddledComponent.isSaddled();
    }

    public void saddle(@Nullable SoundCategory sound) {
        this.saddledComponent.setSaddled(true);
        if (sound != null) {
            this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_PIG_SADDLE, sound, 0.5F, 1.0F);
        }
    }
}

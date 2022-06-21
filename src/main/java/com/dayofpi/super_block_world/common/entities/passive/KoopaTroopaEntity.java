package com.dayofpi.super_block_world.common.entities.passive;

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
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class KoopaTroopaEntity extends PassiveEntity implements IAnimatable, Saddleable, ItemSteerable, Angerable {
    private static final ImmutableList<SensorType<? extends Sensor<? super KoopaTroopaEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_ADULT);
    private static final TrackedData<Integer> KOOPA_COLOR;
    private static final TrackedData<Integer> ANGER_TIME;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;
    private static final UniformIntProvider ANGER_TIME_RANGE;

    static {
        KOOPA_COLOR = DataTracker.registerData(KoopaTroopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME = DataTracker.registerData(KoopaTroopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(10, 20);
        SADDLED = DataTracker.registerData(KoopaTroopaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(KoopaTroopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    final SaddledComponent saddledComponent;
    private final AnimationFactory factory = new AnimationFactory(this);
    @Nullable
    private UUID targetUuid;

    public KoopaTroopaEntity(EntityType<? extends KoopaTroopaEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
    }

    @SuppressWarnings("unused")
    public static boolean canKoopaSpawn(EntityType<? extends KoopaTroopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    public static DefaultAttributeContainer.Builder createKoopaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f).add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
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

    @Override
    protected Brain.Profile<KoopaTroopaEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return KoopaBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<KoopaTroopaEntity> getBrain() {
        return (Brain<KoopaTroopaEntity>) super.getBrain();
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
        return this.isAlive() && !this.isBaby();
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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        } else event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
        this.setKoopaColor(nbt.getInt("KoopaColor"));
        this.readAngerFromNbt(this.world, nbt);
    }
}

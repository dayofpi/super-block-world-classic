package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.common.util.entity.Stompable;
import com.dayofpi.super_block_world.registry.main.EntityInit;
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

import java.util.Random;
import java.util.UUID;

@SuppressWarnings("unused")
public class KoopaEntity extends AbstractEnemy implements Stompable, Angerable, ItemSteerable, Saddleable, IAnimatable {
    private static final TrackedData<Integer> TYPE;
    private static final TrackedData<Integer> ANGER_TIME;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private final AnimationFactory factory = new AnimationFactory(this);

    static {
        TYPE = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(10, 20);
        SADDLED = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(KoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    final SaddledComponent saddledComponent;

    @Nullable
    private UUID targetUuid;

    public KoopaEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
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

    public static boolean canSpawn(EntityType<? extends KoopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() > 0;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AbstractEnemy.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, Ingredient.ofItems(Items.CARROT, Items.CARROT_ON_A_STICK), false));
        this.targetSelector.add(1, new KoopaRevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (source == DamageSource.LAVA)
            this.convertTo(EntityInit.DRY_BONES, false);
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
        if (!(entity instanceof PlayerEntity playerEntity)) {
            return false;
        }
        return playerEntity.getMainHandStack().isOf(Items.CARROT_ON_A_STICK) || playerEntity.getOffHandStack().isOf(Items.CARROT_ON_A_STICK);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean isCarrot = player.getStackInHand(hand).isOf(Items.CARROT);
        if (!isCarrot && this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.world.isClient) {
                player.startRiding(this);
            }
            return ActionResult.success(this.world.isClient);
        } else if (isCarrot) {
            this.eat(player, hand, player.getStackInHand(hand));
            this.stopAnger();
            return ActionResult.success(this.world.isClient);
        }
        ActionResult actionResult = super.interactMob(player, hand);
        if (!actionResult.isAccepted()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.SADDLE)) {
                return itemStack.useOnEntity(player, this, hand);
            }
            return ActionResult.PASS;
        }
        return actionResult;
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

    public void setKoopaType(int color) {
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
    }

    protected SoundEvent getAmbientSound() {
        return SoundInit.ENTITY_KOOPA_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENTITY_KOOPA_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_KOOPA_DEATH;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (random.nextInt(8) == 0)
            this.setKoopaType(1);
        HammerBroEntity jockey;
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        if (world.getRandom().nextInt(20) == 0) {
            jockey = EntityInit.HAMMER_BRO.create(this.world);
            if (jockey != null) {
                this.saddle(null);
                jockey.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.CARROT_ON_A_STICK));
                jockey.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);
                jockey.initialize(world, difficulty, spawnReason, null, null);
                jockey.startRiding(this);
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

    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, this.getSoundPitch());
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
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.2f + (this.getKoopaType() * 0.05F);
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
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
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

    @Override
    public void stompResult(LivingEntity livingEntity) {
        if (!this.world.isClient) {
            KoopaShellEntity koopaShellEntity = this.convertTo(EntityInit.KOOPA_SHELL, true);
            if (koopaShellEntity != null) {
                koopaShellEntity.setKoopaType(this.getKoopaType());
                koopaShellEntity.setHasMob(true);
                if (this.isSaddled())
                    this.dropInventory();
            }
        }
    }

    class KoopaRevengeGoal extends RevengeGoal {
        KoopaRevengeGoal(KoopaEntity koopaEntity) {
            super(koopaEntity);
        }

        @Override
        public boolean shouldContinue() {
            return KoopaEntity.this.hasAngerTime() && super.shouldContinue();
        }
    }
}

package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.common.entities.BuzzyShellEntity;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBuzzy;
import com.dayofpi.super_block_world.common.util.entity.Stompable;
import com.dayofpi.super_block_world.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BuzzyEntity extends AbstractBuzzy implements ItemSteerable, Saddleable, Stompable {
    private static final TrackedData<Boolean> HIDING;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;

    static {
        HIDING = DataTracker.registerData(BuzzyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SADDLED = DataTracker.registerData(BuzzyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(BuzzyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    private final SaddledComponent saddledComponent;

    public BuzzyEntity(EntityType<? extends BuzzyEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
    }

    public static boolean canBuzzySpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return AbstractBuzzy.canSpawn(type, world, spawnReason, pos, random) && pos.getY() > 0;
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, Ingredient.ofItems(ItemInit.GREEN_MUSHROOM_ON_A_STICK), false));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(MushroomBlocks.GREEN_MUSHROOM), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
        super.initGoals();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (this.isBaby()) {
            return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        } else {
            if (this.random.nextInt(20) == 0 && world.getBlockState(this.getBlockPos().up()).isAir()) {
                BuzzyEntity passiveEntity = EntityInit.BUZZY_BEETLE.create(world.toServerWorld());
                if (passiveEntity != null) {
                    passiveEntity.setBreedingAge(-24000);
                    entityData = this.initializeRider(world, difficulty, passiveEntity);
                }
            }
        } return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private EntityData initializeRider(ServerWorldAccess world, LocalDifficulty difficulty, MobEntity rider) {
        rider.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
        rider.initialize(world, difficulty, SpawnReason.JOCKEY, null, null);
        rider.startRiding(this, true);
        return new PassiveData(0.0F);
    }

    @Override
    public BuzzyEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        if (passiveEntity != null) {
            passiveEntity.setPersistent();
        }
        return EntityInit.BUZZY_BEETLE.create(serverWorld);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
    }

    public boolean isHiding() {
        return this.dataTracker.get(HIDING);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (BOOST_TIME.equals(data) && this.world.isClient) {
            this.saddledComponent.boost();
        }

        super.onTrackedDataSet(data);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isBaby() && this.getVehicle() != null && source == DamageSource.IN_WALL) {
            this.dismountVehicle();
            return false;
        } return super.damage(source, amount);
    }

    @Override
    public boolean canBeControlledByRider() {
        Entity entity = this.getPrimaryPassenger();
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        } else {
            return livingEntity.getMainHandStack().isOf(ItemInit.GREEN_MUSHROOM_ON_A_STICK) || livingEntity.getOffHandStack().isOf(ItemInit.GREEN_MUSHROOM_ON_A_STICK);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(MushroomBlocks.GREEN_MUSHROOM.asItem());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        if (!bl && this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
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

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SADDLED, false);
        this.dataTracker.startTracking(HIDING, false);
        this.dataTracker.startTracking(BOOST_TIME, 0);
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

    @Override
    public void travel(Vec3d movementInput) {
        this.travel(this, this.saddledComponent, movementInput);
    }

    @Override
    public double getMountedHeightOffset() {
        double d = 0.3D;
        if (this.isUpsideDown()) {
            d = -2D;
        }
        return super.getMountedHeightOffset() + d;
    }

    @Nullable
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
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.55F;
    }

    @Override
    public void stompResult(LivingEntity livingEntity) {
        if (!this.world.isClient) {
                BuzzyShellEntity buzzyShellEntity = this.convertTo(EntityInit.BUZZY_SHELL, true);
            if (buzzyShellEntity != null)
                buzzyShellEntity.setHasMob(true);
            if (this.isSaddled())
                this.dropInventory();
        }
    }
}

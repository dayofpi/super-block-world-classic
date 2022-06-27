/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.common.blocks.FlagBlock;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class GoKartEntity extends Entity {
    private static final TrackedData<Integer> KART_COLOR = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<OptionalInt> FLAG_TYPE = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);

    private static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> LEFT_PADDLE_MOVING = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_PADDLE_MOVING = DataTracker.registerData(GoKartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final float[] paddlePhases = new float[2];
    private float yawVelocity;
    private int field_7708;
    private double x;
    private double y;
    private double z;
    private double boatYaw;
    private double boatPitch;
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingForward;
    private boolean pressingBack;
    private Location location;
    private Location lastLocation;
    public int tirePitch = 0;

    public GoKartEntity(EntityType<? extends GoKartEntity> entityType, World world) {
        super(entityType, world);
        this.intersectionChecked = true;
        this.stepHeight = 1.0f;
    }

    public GoKartEntity(World world, double x, double y, double z) {
        this(ModEntities.GO_KART, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    public static boolean canCollide(Entity entity, Entity other) {
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }

    public DyeColor getKartColor() {
        return DyeColor.byId(this.dataTracker.get(KART_COLOR));
    }

    public void setKartColor(DyeColor color) {
        this.dataTracker.set(KART_COLOR, color.getId());
    }

    public OptionalInt getFlagType() {
        return this.dataTracker.get(FLAG_TYPE);
    }

    public void setFlagType(int i) {
        this.dataTracker.set(FLAG_TYPE, OptionalInt.of(i));
    }

    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DAMAGE_WOBBLE_TICKS, 0);
        this.dataTracker.startTracking(DAMAGE_WOBBLE_SIDE, 1);
        this.dataTracker.startTracking(DAMAGE_WOBBLE_STRENGTH, 0.0f);
        this.dataTracker.startTracking(LEFT_PADDLE_MOVING, false);
        this.dataTracker.startTracking(RIGHT_PADDLE_MOVING, false);
        this.dataTracker.startTracking(KART_COLOR, DyeColor.RED.getId());
        this.dataTracker.startTracking(FLAG_TYPE, OptionalInt.empty());
    }

    @Override
    public boolean collidesWith(Entity other) {
        return GoKartEntity.canCollide(this, other);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("KartColor", NbtElement.NUMBER_TYPE)) {
            this.setKartColor(DyeColor.byId(nbt.getInt("KartColor")));
        }
        if (nbt.contains("Flag")) {
            this.setFlagType(nbt.getInt("Flag"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putByte("KartColor", (byte) this.getKartColor().getId());
        if (this.getFlagType().isPresent())
            nbt.putInt("Flag", (byte) this.getFlagType().getAsInt());

    }

    @Override
    protected Vec3d positionInPortal(Direction.Axis portalAxis, BlockLocating.Rectangle portalRect) {
        return LivingEntity.positionInPortal(super.positionInPortal(portalAxis, portalRect));
    }

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.EVENTS;
    }

    @Override
    public double getMountedHeightOffset() {
        return -0.1;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.world.isClient || this.isRemoved()) {
            return true;
        }
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
        this.scheduleVelocityUpdate();
        this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
        bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode;
        if (bl || this.getDamageWobbleStrength() > 40.0f) {
            if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                this.dropItem(ModItems.GO_KART);
                this.dropFlag();
            }
            this.discard();
        }
        return true;
    }

    private void dropFlag() {
        if (this.getFlagType().isEmpty())
            return;
        switch(this.getFlagType().getAsInt()) {
            case 0:
                this.dropItem(ModBlocks.WHITE_FLAG);
            case 1:
                this.dropItem(ModBlocks.ORANGE_FLAG);
            case 2:
                this.dropItem(ModBlocks.MAGENTA_FLAG);
            case 3:
                this.dropItem(ModBlocks.LIGHT_BLUE_FLAG);
            case 4:
                this.dropItem(ModBlocks.YELLOW_FLAG);
            case 5:
                this.dropItem(ModBlocks.LIME_FLAG);
            case 6:
                this.dropItem(ModBlocks.PINK_FLAG);
            case 7:
                this.dropItem(ModBlocks.GRAY_FLAG);
            case 8:
                this.dropItem(ModBlocks.LIGHT_GRAY_FLAG);
            case 9:
                this.dropItem(ModBlocks.CYAN_FLAG);
            case 10:
                this.dropItem(ModBlocks.PURPLE_FLAG);
            case 11:
                this.dropItem(ModBlocks.BLUE_FLAG);
            case 12:
                this.dropItem(ModBlocks.BROWN_FLAG);
            case 13:
                this.dropItem(ModBlocks.GREEN_FLAG);
            case 14:
                this.dropItem(ModBlocks.RED_FLAG);
            case 15:
                this.dropItem(ModBlocks.BLACK_FLAG);
            default:
                this.dropItem(ModBlocks.RAINBOW_FLAG);
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity instanceof GoKartEntity) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.pushAwayFrom(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.pushAwayFrom(entity);
        }
    }

    @Override
    public void animateDamage() {
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() * 11.0f);
    }

    @Override
    public boolean collides() {
        return !this.isRemoved();
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.field_7708 = 10;
    }

    @Override
    public Direction getMovementDirection() {
        return this.getHorizontalFacing().rotateYClockwise();
    }

    @Override
    public void tick() {
        this.lastLocation = this.location;
        this.location = this.checkLocation();

        if (this.getDamageWobbleTicks() > 0) {
            this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
        }
        if (this.getDamageWobbleStrength() > 0.0f) {
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0f);
        }
        if (world.isClient && this.getVelocity().horizontalLengthSquared() > 0)
            ++tirePitch;
        super.tick();
        this.updatePositionAndRotation();
        if (this.isLogicalSideForUpdatingMovement()) {
            if (!(this.getFirstPassenger() instanceof PlayerEntity)) {
                this.setPaddleMovings(false, false);
            }
            this.updateVelocity();
            if (this.world.isClient) {
                this.updatePaddles();
                this.world.sendPacket(new BoatPaddleStateC2SPacket(this.isPaddleMoving(0), this.isPaddleMoving(1)));
            }
            this.move(MovementType.SELF, this.getVelocity());
        } else {
            this.setVelocity(Vec3d.ZERO);
        }
        for (int i = 0; i <= 1; ++i) {
            if (this.isPaddleMoving(i)) {
                SoundEvent soundEvent;
                if (!this.isSilent() && (double) (this.paddlePhases[i] % ((float) Math.PI * 2)) <= 0.7853981852531433 && (double) ((this.paddlePhases[i] + 0.3926991f) % ((float) Math.PI * 2)) >= 0.7853981852531433 && (soundEvent = this.getPaddleSoundEvent()) != null) {
                    Vec3d vec3d = this.getRotationVec(1.0f);
                    double d = i == 1 ? -vec3d.z : vec3d.z;
                    double e = i == 1 ? vec3d.x : -vec3d.x;
                    this.world.playSound(null, this.getX() + d, this.getY(), this.getZ() + e, soundEvent, this.getSoundCategory(), 1.0f, 0.8f + 0.4f * this.random.nextFloat());
                }
                this.paddlePhases[i] = this.paddlePhases[i] + 0.3926991f;
                continue;
            }
            this.paddlePhases[i] = 0.0f;
        }
        this.checkBlockCollision();
        List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2f, -0.01f, 0.2f), EntityPredicates.canBePushedBy(this));
        if (!list.isEmpty()) {
            boolean bl = !this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity);
            for (Entity entity : list) {
                if (entity.hasPassenger(this)) continue;
                if (bl && this.getPassengerList().size() < this.getMaxPassengers() && !entity.hasVehicle() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterCreatureEntity) && !(entity instanceof PlayerEntity)) {
                    entity.startRiding(this);
                    continue;
                }
                this.pushAwayFrom(entity);
            }
        }
    }

    @Nullable
    private SoundEvent getPaddleSoundEvent() {
        switch (this.checkLocation()) {
            case IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER -> {
                return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
            }
            case ON_LAND -> {
                return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
            }
        }
        return null;
    }

    public void setPaddleMovings(boolean leftMoving, boolean rightMoving) {
        this.dataTracker.set(LEFT_PADDLE_MOVING, leftMoving);
        this.dataTracker.set(RIGHT_PADDLE_MOVING, rightMoving);
    }

    private void updatePositionAndRotation() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.field_7708 = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }
        if (this.field_7708 <= 0) {
            return;
        }
        double d = this.getX() + (this.x - this.getX()) / (double) this.field_7708;
        double e = this.getY() + (this.y - this.getY()) / (double) this.field_7708;
        double f = this.getZ() + (this.z - this.getZ()) / (double) this.field_7708;
        double g = MathHelper.wrapDegrees(this.boatYaw - (double) this.getYaw());
        this.setYaw(this.getYaw() + (float) g / (float) this.field_7708);
        this.setPitch(this.getPitch() + (float) (this.boatPitch - (double) this.getPitch()) / (float) this.field_7708);
        --this.field_7708;
        this.setPosition(d, e, f);
        this.setRotation(this.getYaw(), this.getPitch());
    }

    private Location checkLocation() {
        Location location = this.getUnderWaterLocation();
        if (location != null) {
            return location;
        }
        if (this.checkKartInWater()) {
            return Location.IN_WATER;
        }
        float f = this.getNearbySlipperiness();
        if (f > 0.0f) {
            return Location.ON_LAND;
        }
        return Location.IN_AIR;
    }

    public float getNearbySlipperiness() {
        Box box = this.getBoundingBox();
        Box box2 = new Box(box.minX, box.minY - 0.001, box.minZ, box.maxX, box.minY, box.maxZ);
        int i = MathHelper.floor(box2.minX) - 1;
        int j = MathHelper.ceil(box2.maxX) + 1;
        int k = MathHelper.floor(box2.minY) - 1;
        int l = MathHelper.ceil(box2.maxY) + 1;
        int m = MathHelper.floor(box2.minZ) - 1;
        int n = MathHelper.ceil(box2.maxZ) + 1;
        VoxelShape voxelShape = VoxelShapes.cuboid(box2);
        float f = 0.0f;
        int o = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int p = i; p < j; ++p) {
            for (int q = m; q < n; ++q) {
                int r = (p == i || p == j - 1 ? 1 : 0) + (q == m || q == n - 1 ? 1 : 0);
                if (r == 2) continue;
                for (int s = k; s < l; ++s) {
                    if (r > 0 && (s == k || s == l - 1)) continue;
                    mutable.set(p, s, q);
                    BlockState blockState = this.world.getBlockState(mutable);
                    if (blockState.getBlock() instanceof LilyPadBlock || !VoxelShapes.matchesAnywhere(blockState.getCollisionShape(this.world, mutable).offset(p, s, q), voxelShape, BooleanBiFunction.AND))
                        continue;
                    f += blockState.getBlock().getSlipperiness();
                    ++o;
                }
            }
        }
        return f / (float) o;
    }

    private boolean checkKartInWater() {
        Box box = this.getBoundingBox();
        int i = MathHelper.floor(box.minX);
        int j = MathHelper.ceil(box.maxX);
        int k = MathHelper.floor(box.minY);
        int l = MathHelper.ceil(box.minY + 0.001);
        int m = MathHelper.floor(box.minZ);
        int n = MathHelper.ceil(box.maxZ);
        boolean bl = false;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int o = i; o < j; ++o) {
            for (int p = k; p < l; ++p) {
                for (int q = m; q < n; ++q) {
                    mutable.set(o, p, q);
                    FluidState fluidState = this.world.getFluidState(mutable);
                    if (!fluidState.isIn(FluidTags.WATER)) continue;
                    float f = (float) p + fluidState.getHeight(this.world, mutable);
                    bl |= box.minY < (double) f;
                }
            }
        }
        return bl;
    }

    @Nullable
    private Location getUnderWaterLocation() {
        Box box = this.getBoundingBox();
        double d = box.maxY + 0.001;
        int i = MathHelper.floor(box.minX);
        int j = MathHelper.ceil(box.maxX);
        int k = MathHelper.floor(box.maxY);
        int l = MathHelper.ceil(d);
        int m = MathHelper.floor(box.minZ);
        int n = MathHelper.ceil(box.maxZ);
        boolean bl = false;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int o = i; o < j; ++o) {
            for (int p = k; p < l; ++p) {
                for (int q = m; q < n; ++q) {
                    mutable.set(o, p, q);
                    FluidState fluidState = this.world.getFluidState(mutable);
                    if (!fluidState.isIn(FluidTags.WATER) || !(d < (double) ((float) mutable.getY() + fluidState.getHeight(this.world, mutable))))
                        continue;
                    if (fluidState.isStill()) {
                        bl = true;
                        continue;
                    }
                    return Location.UNDER_FLOWING_WATER;
                }
            }
        }
        return bl ? Location.UNDER_WATER : null;
    }

    private void updateVelocity() {
        double gravity = this.hasNoGravity() ? 0.0 : (double) -0.04f;
        float velocityDecay = 0.05f;

        if (this.lastLocation == Location.IN_AIR && this.location != Location.IN_AIR && this.location != Location.ON_LAND) {
            this.location = Location.IN_WATER;
        }
        else {
            if (this.location == Location.IN_WATER) {
                velocityDecay = 0.9f;
            } else if (this.location == Location.UNDER_FLOWING_WATER) {
                gravity = -7.0E-4;
                velocityDecay = 0.9f;
            } else if (this.location == Location.UNDER_WATER) {
                velocityDecay = 0.9f;
            } else if (this.location == Location.IN_AIR) {
                velocityDecay = 1.0f;
            } else if (this.location == Location.ON_LAND) {
                velocityDecay = 0.9f;
            }
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x * (double) velocityDecay, vec3d.y + gravity, vec3d.z * (double) velocityDecay);
            this.yawVelocity *= (velocityDecay - 0.1f);
        }
    }

    public boolean isPaddleMoving(int paddle) {
        return this.dataTracker.get(paddle == 0 ? LEFT_PADDLE_MOVING : RIGHT_PADDLE_MOVING) && this.getPrimaryPassenger() != null;
    }

    @Override
    public void updatePassengerPosition(Entity entity) {
        if (!this.hasPassenger(entity)) {
            return;
        }

        float hOffset = 0.0f;
        float vOffset = (float) ((this.isRemoved() ? (double) 0.01f : this.getMountedHeightOffset()) + entity.getHeightOffset());

        if (this.getPassengerList().size() > 1) {
            int i = this.getPassengerList().indexOf(entity);
            hOffset = i == 0 ? 0.2f : -0.6f;
            if (entity instanceof AnimalEntity) {
                hOffset += 0.2f;
            }
        }

        Vec3d vec3d = new Vec3d(hOffset, 0.0, 0.0).rotateY(-this.getYaw() * ((float) Math.PI / 180) - 1.5707964f);
        entity.setPosition(this.getX() + vec3d.x, this.getY() + (double) vOffset, this.getZ() + vec3d.z);
        entity.setYaw(entity.getYaw() + this.yawVelocity);
        entity.setHeadYaw(entity.getHeadYaw() + this.yawVelocity);
        this.copyEntityData(entity);
        if (entity instanceof AnimalEntity && this.getPassengerList().size() == this.getMaxPassengers()) {
            int j = entity.getId() % 2 == 0 ? 90 : 270;
            entity.setBodyYaw(((AnimalEntity) entity).bodyYaw + (float) j);
            entity.setHeadYaw(entity.getHeadYaw() + (float) j);
        }
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        double z;
        Vec3d vec3d = GoKartEntity.getPassengerDismountOffset(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO, passenger.getWidth(), passenger.getYaw());
        double x = this.getX() + vec3d.x;
        BlockPos blockPos = new BlockPos(x, this.getBoundingBox().maxY, z = this.getZ() + vec3d.z);
        BlockPos blockPos2 = blockPos.down();
        if (!this.world.isWater(blockPos2)) {
            double dismountHeight2;
            ArrayList<Vec3d> list = Lists.newArrayList();
            double dismountHeight1 = this.world.getDismountHeight(blockPos);
            if (Dismounting.canDismountInBlock(dismountHeight1)) {
                list.add(new Vec3d(x, (double) blockPos.getY() + dismountHeight1, z));
            }
            if (Dismounting.canDismountInBlock(dismountHeight2 = this.world.getDismountHeight(blockPos2))) {
                list.add(new Vec3d(x, (double) blockPos2.getY() + dismountHeight2, z));
            }
            for (EntityPose entityPose : passenger.getPoses()) {
                for (Vec3d vec3d2 : list) {
                    if (!Dismounting.canPlaceEntityAt(this.world, vec3d2, passenger, entityPose)) continue;
                    passenger.setPose(entityPose);
                    return vec3d2;
                }
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    protected void copyEntityData(Entity entity) {
        entity.setBodyYaw(this.getYaw());
        float yaw = MathHelper.wrapDegrees(entity.getYaw() - this.getYaw());
        float clamp = MathHelper.clamp(yaw, -105.0f, 105.0f);
        entity.prevYaw += clamp - yaw;
        entity.setYaw(entity.getYaw() + clamp - yaw);
        entity.setHeadYaw(entity.getYaw());
    }

    @Override
    public void onPassengerLookAround(Entity passenger) {
        this.copyEntityData(passenger);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (item instanceof DyeItem) {
            DyeColor dyeColor = ((DyeItem) item).getColor();
            if (dyeColor != this.getKartColor()) {
                this.setKartColor(dyeColor);
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.CONSUME;
            }
        }
        if (this.getFlagType().isEmpty()) {
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof FlagBlock flagBlock) {
                if (flagBlock.isRainbow()) {
                    this.chooseRainbowFlag(itemStack.getName().getString());
                } else {
                    this.setFlagType(flagBlock.getColor().getId());
                }
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.CONSUME;
            }
        }
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        }
        if (!this.world.isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }

    private void chooseRainbowFlag(String string) {
        for (String name : FlagBlock.TRANS) {
            if (string.contains(name)) {
                this.setFlagType(17);
                return;
            }
        }
        for (String name : FlagBlock.BI) {
            if (string.contains(name)) {
                this.setFlagType(18);
                return;
            }
        }
        for (String name : FlagBlock.LESBIAN) {
            if (string.contains(name)) {
                this.setFlagType(19);
                return;
            }
        }
        this.setFlagType(16);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (this.hasVehicle()) {
            return;
        }
        if (onGround) {
            if (this.fallDistance > 4.0f) {
                if (this.location != Location.ON_LAND) {
                    this.onLanding();
                    return;
                }
                this.handleFallDamage(this.fallDistance, 1.0f, DamageSource.FALL);
                if (!this.world.isClient && !this.isRemoved()) {
                    this.kill();
                    if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        int i;
                        for (i = 0; i < 3; ++i) {
                            this.dropItem(ModItems.KART_TIRE);
                        }
                        for (i = 0; i < 2; ++i) {
                            this.dropItem(ModItems.BRONZE_INGOT);
                        }
                    }
                }
            }
            this.onLanding();
        } else if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0) {
            this.fallDistance -= (float) heightDifference;
        }
    }

    public float getDamageWobbleStrength() {
        return this.dataTracker.get(DAMAGE_WOBBLE_STRENGTH);
    }

    public void setDamageWobbleStrength(float wobbleStrength) {
        this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, wobbleStrength);
    }

    public int getDamageWobbleTicks() {
        return this.dataTracker.get(DAMAGE_WOBBLE_TICKS);
    }

    public void setDamageWobbleTicks(int wobbleTicks) {
        this.dataTracker.set(DAMAGE_WOBBLE_TICKS, wobbleTicks);
    }

    public int getDamageWobbleSide() {
        return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
    }

    public void setDamageWobbleSide(int side) {
        this.dataTracker.set(DAMAGE_WOBBLE_SIDE, side);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }

    private int getMaxPassengers() {
        return 2;
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        return this.getFirstPassenger();
    }

    private void updatePaddles() {
        if (!this.hasPassengers()) {
            return;
        }
        float f = 0.0f;
        if (this.pressingLeft) {
            this.yawVelocity -= 1.0f;
        }
        if (this.pressingRight) {
            this.yawVelocity += 1.0f;
        }
        if (this.pressingRight != this.pressingLeft && !this.pressingForward && !this.pressingBack) {
            f += 0.005f;
        }
        this.setYaw(this.getYaw() + this.yawVelocity);
        if (this.pressingForward) {
            f += 0.04f;
        }
        if (this.pressingBack) {
            f -= 0.005f;
        }
        this.setVelocity(this.getVelocity().add(MathHelper.sin(-this.getYaw() * ((float) Math.PI / 180)) * f, 0.0, MathHelper.cos(this.getYaw() * ((float) Math.PI / 180)) * f));
        this.setPaddleMovings(this.pressingRight && !this.pressingLeft || this.pressingForward, this.pressingLeft && !this.pressingRight || this.pressingForward);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean isSubmergedInWater() {
        return this.location == Location.UNDER_WATER || this.location == Location.UNDER_FLOWING_WATER;
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.GO_KART);
    }

    public enum Location {
        IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR
    }
}

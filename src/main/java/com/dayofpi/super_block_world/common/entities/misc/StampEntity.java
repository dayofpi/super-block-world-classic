package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class StampEntity extends AbstractDecorationEntity implements IAnimatable {
    private static final TrackedData<String> STAMP = DataTracker.registerData(StampEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> ROTATION = DataTracker.registerData(StampEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimationFactory FACTORY = new AnimationFactory(this);

    public StampEntity(EntityType<StampEntity> type, World world) {
        super(type, world);
    }

    public StampEntity(World world, BlockPos pos, Direction facing) {
        super(ModEntities.STAMP, world, pos);
        this.setFacing(facing);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, animationEvent -> PlayState.STOP));
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(STAMP, Stamp.ARROW.name);
        this.getDataTracker().startTracking(ROTATION, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Stamp", this.getStamp().name);
        nbt.putByte("Facing", (byte) this.facing.getId());
        nbt.putByte("Rotation", (byte) this.getRotation());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setStamp(Stamp.fromName(nbt.getString("Stamp")));
        this.setFacing(Direction.byId(nbt.getByte("Facing")));
        this.setRotation(nbt.getByte("Rotation"));
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        this.setRotation(this.getRotation() + 1);
        return ActionResult.success(this.world.isClient);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.setFacing(Direction.byId(packet.getEntityData()));
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(this.getItemFromStamp(this.getStamp()));
    }

    public int getRotation() {
        return this.getDataTracker().get(ROTATION);
    }

    public void setRotation(int value) {
        this.getDataTracker().set(ROTATION, value % 8);
    }

    @Override
    public int getWidthPixels() {
        return 16;
    }

    @Override
    public int getHeightPixels() {
        return 16;
    }

    @Override
    public float getTargetingMargin() {
        return 0.0f;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.0f;
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = 16.0;
        return distance < (d *= 64.0 * getRenderDistanceMultiplier()) * d;
    }

    public Stamp getStamp() {
        return Stamp.fromName(this.dataTracker.get(STAMP));
    }

    public void setStamp(Stamp stamp) {
        this.dataTracker.set(STAMP, stamp.name);
    }

    private Item getItemFromStamp(Stamp stamp) {
        switch (stamp) {
            case ARROW -> {
                return ModItems.ARROW_STAMP;
            }
            case BOO -> {
                return ModItems.BOO_STAMP;
            }
            case BUZZY -> {
                return ModItems.BUZZY_STAMP;
            }
            case CAT -> {
                return ModItems.CAT_STAMP;
            }
            case CLOUD -> {
                return ModItems.CLOUD_STAMP;
            }
            case FLOWER -> {
                return ModItems.FLOWER_STAMP;
            }
            case LUIGI -> {
                return ModItems.LUIGI_STAMP;
            }
            case MARIO -> {
                return ModItems.MARIO_STAMP;
            }
            case MOM -> {
                return ModItems.MOM_STAMP;
            }
            case MUSHROOM -> {
                return ModItems.MUSHROOM_STAMP;
            }
            case PRINCESS -> {
                return ModItems.PRINCESS_STAMP;
            }
            case SMILEY -> {
                return ModItems.SMILEY_STAMP;
            }
        }
        return ModItems.ARROW_STAMP;
    }

    @Override
    public void onBreak(@Nullable Entity entity) {
        this.playSound(this.getBreakSound(), 1.0f, 1.0f);
        if (entity instanceof PlayerEntity player && player.getAbilities().creativeMode)
            return;
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
            this.dropStack(new ItemStack(this.getItemFromStamp(this.getStamp())));
    }

    public SoundEvent getBreakSound() {
        return Sounds.ENTITY_STAMP_BREAK;
    }

    @Override
    public void onPlace() {
        this.playSound(this.getPlaceSound(), 1.0f, 1.0f);
    }

    public SoundEvent getPlaceSound() {
        return Sounds.ENTITY_STAMP_PLACE;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, this.facing.getId(), this.getDecorationBlockPos());
    }

    @Override
    protected void setFacing(Direction facing) {
        Validate.notNull(facing);
        this.facing = facing;
        if (facing.getAxis().isHorizontal()) {
            this.setPitch(0.0f);
            this.setYaw(this.facing.getHorizontal() * 90);
        } else {
            this.setPitch(-90 * facing.getDirection().offset());
            this.setYaw(0.0f);
        }
        this.prevPitch = this.getPitch();
        this.prevYaw = this.getYaw();
        this.updateAttachmentPosition();
    }

    @Override
    protected void updateAttachmentPosition() {
        if (this.facing == null) {
            return;
        }
        double e = (double) this.attachmentPos.getX() + 0.5 - (double) this.facing.getOffsetX() * 0.46875;
        double f = (double) this.attachmentPos.getY() + 0.5 - (double) this.facing.getOffsetY() * 0.46875;
        double g = (double) this.attachmentPos.getZ() + 0.5 - (double) this.facing.getOffsetZ() * 0.46875;
        this.setPos(e, f, g);
        double h = this.getWidthPixels();
        double i = this.getHeightPixels();
        double j = this.getWidthPixels();
        Direction.Axis axis = this.facing.getAxis();
        switch (axis) {
            case X -> h = 1.0;
            case Y -> i = 1.0;
            case Z -> j = 1.0;
        }
        this.setBoundingBox(new Box(e - (h /= 32.0), f - (i /= 32.0), g - (j /= 32.0), e + h, f + i, g + j));
    }

    @Override
    public boolean canStayAttached() {
        if (!this.world.isSpaceEmpty(this)) {
            return false;
        }
        BlockState blockState = this.world.getBlockState(this.attachmentPos.offset(this.facing.getOpposite()));
        if (!(blockState.getMaterial().isSolid() || this.facing.getAxis().isHorizontal() && AbstractRedstoneGateBlock.isRedstoneGate(blockState))) {
            return false;
        }
        return this.world.getOtherEntities(this, this.getBoundingBox(), PREDICATE).isEmpty();
    }

    public enum Stamp {
        ARROW("arrow"),
        BOO("boo"),
        BUZZY("buzzy"),
        CAT("cat"),
        CLOUD("cloud"),
        FLOWER("flower"),
        FUZZY("fuzzy"),
        LUIGI("luigi"),
        MARIO("mario"),
        MOM("mom"),
        MUSHROOM("mushroom"),
        PRINCESS("princess"),
        SMILEY("smiley");

        private final String name;

        Stamp(String name) {
            this.name = name;
        }

        static Stamp fromName(String name) {
            for (Stamp type : Stamp.values()) {
                if (!type.name.equals(name)) continue;
                return type;
            }
            return ARROW;
        }

        public String getName() {
            return name;
        }
    }
}

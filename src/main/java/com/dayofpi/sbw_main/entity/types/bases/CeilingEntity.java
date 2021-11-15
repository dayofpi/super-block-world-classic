package com.dayofpi.sbw_main.entity.types.bases;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.*;

import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class CeilingEntity extends AnimalEntity {
    private static final TrackedData<Boolean> UPSIDE_DOWN;

    static {
        UPSIDE_DOWN = DataTracker.registerData(AbstractBuzzy.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    protected CeilingEntity(EntityType<? extends CeilingEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int i = world.toServerWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLightLevel(pos);
            return i <= random.nextInt(8);
        }
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.5F - world.getBrightness(pos);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted()) {
            this.setPersistent();
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(UPSIDE_DOWN, false);
    }

    public boolean isOnCeiling(BlockPos pos) {
        if (!this.isBaby()) {
            return world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos, Direction.DOWN) && !this.hasVehicle();
        } else
            return world.getStatesInBox(this.getBoundingBox().offset(0.0D, 0.0D, 0.5D)).anyMatch(BlockState::isOpaque);

    }

    public boolean isUpsideDown() {
        return this.dataTracker.get(UPSIDE_DOWN);
    }

    public void setUpsideDown(boolean upsideDown) {
        this.dataTracker.set(UPSIDE_DOWN, upsideDown);
    }
}

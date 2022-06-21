package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.mob.BooEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class BooFollowOwnerGoal extends Goal {
	private final BooEntity tameable;
	private LivingEntity owner;
	private final WorldView world;
	private final double speed;
	private final EntityNavigation navigation;
	private int updateCountdownTicks;
	private final float maxDistance;
	private final float minDistance;
	private float oldWaterPathfindingPenalty;

	public BooFollowOwnerGoal(BooEntity booEntity, double d, float f, float g) {
		this.tameable = booEntity;
		this.world = booEntity.world;
		this.speed = d;
		this.navigation = booEntity.getNavigation();
		this.minDistance = f;
		this.maxDistance = g;
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		if (!(booEntity.getNavigation() instanceof MobNavigation) && !(booEntity.getNavigation() instanceof BirdNavigation)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		}
	}

	public boolean canStart() {
		LivingEntity livingEntity = this.tameable.getOwner();
		if (livingEntity == null) {
			return false;
		} else if (livingEntity.isSpectator()) {
			return false;
		} else if (this.tameable.isSitting()) {
			return false;
		} else if (this.tameable.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
			return false;
		} else {
			this.owner = livingEntity;
			return true;
		}
	}

	public boolean shouldContinue() {
		if (this.navigation.isIdle()) {
			return false;
		} else if (this.tameable.isSitting()) {
			return false;
		} else {
			return !(this.tameable.squaredDistanceTo(this.owner) <= (double)(this.maxDistance * this.maxDistance));
		}
	}

	public void start() {
		this.updateCountdownTicks = 0;
		this.oldWaterPathfindingPenalty = this.tameable.getPathfindingPenalty(PathNodeType.WATER);
		this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
	}

	public void stop() {
		this.owner = null;
		this.navigation.stop();
		this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
	}

	public void tick() {
		this.tameable.getLookControl().lookAt(this.owner, 10.0F, (float)this.tameable.getMaxLookPitchChange());
		if (--this.updateCountdownTicks <= 0) {
			this.updateCountdownTicks = 10;
			if (!this.tameable.isLeashed() && !this.tameable.hasVehicle()) {
				if (this.tameable.squaredDistanceTo(this.owner) >= 144.0D) {
					this.tryTeleport();
				} else {
					this.navigation.startMovingTo(this.owner, this.speed);
				}

			}
		}
	}

	private void tryTeleport() {
		BlockPos blockPos = this.owner.getBlockPos();

		for(int i = 0; i < 10; ++i) {
			int j = this.getRandomInt(-3, 3);
			int k = this.getRandomInt(-1, 1);
			int l = this.getRandomInt(-3, 3);
			boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
			if (bl) {
				return;
			}
		}

	}

	private boolean tryTeleportTo(int x, int y, int z) {
		if (Math.abs((double)x - this.owner.getX()) < 2.0D && Math.abs((double)z - this.owner.getZ()) < 2.0D) {
			return false;
		} else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
			return false;
		} else {
			this.tameable.refreshPositionAndAngles((double)x + 0.5D, y, (double)z + 0.5D, this.tameable.getYaw(), this.tameable.getPitch());
			this.navigation.stop();
			return true;
		}
	}

	private boolean canTeleportTo(BlockPos pos) {
		PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
		if (pathNodeType != PathNodeType.WALKABLE) {
			return false;
		} else {
			BlockPos blockPos = pos.subtract(this.tameable.getBlockPos());
			return this.world.isSpaceEmpty(this.tameable, this.tameable.getBoundingBox().offset(blockPos));
		}
	}

	private int getRandomInt(int min, int max) {
		return this.tameable.getRandom().nextInt(max - min + 1) + min;
	}
}

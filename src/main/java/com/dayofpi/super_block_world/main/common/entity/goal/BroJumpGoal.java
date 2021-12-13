package com.dayofpi.super_block_world.main.common.entity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class BroJumpGoal extends Goal {
	private final MobEntity mob;
	private LivingEntity target;
	private final float velocity;

	public BroJumpGoal(MobEntity mobEntity, float f) {
		this.mob = mobEntity;
		this.velocity = f;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	public boolean canStart() {
		if (this.mob.hasPassengers()) {
			return false;
		} else {
			this.target = this.mob.getTarget();
			if (this.target == null) {
				return false;
			} else {
				double d = this.mob.squaredDistanceTo(this.target);
				if (!(d < 4.0D) && !(d > 19.0D)) {
					return this.mob.getRandom().nextInt(toGoalTicks(50)) == 0;
				} else {
					return false;
				}
			}
		}
	}

	public boolean shouldContinue() {
		return !this.mob.isOnGround();
	}

	public void start() {
		Vec3d vec3d = this.mob.getVelocity();
		Vec3d vec3d2 = new Vec3d(this.target.getX() - this.mob.getX(), 0.0D, this.target.getZ() - this.mob.getZ());
		if (vec3d2.lengthSquared() > 1.0E-7D) {
			vec3d2 = vec3d2.normalize().multiply(1.0D).add(vec3d.multiply(0.2D));
		}

		this.mob.setVelocity(-vec3d2.x, this.velocity, vec3d2.z * mob.getRandom().nextFloat());
	}
}

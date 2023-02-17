package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.hostile.AbstractBro;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class BroJumpGoal extends Goal {
	private final AbstractBro abstractBro;
	private LivingEntity target;
	private final float velocity;

	public BroJumpGoal(AbstractBro abstractBro, float velocity) {
		this.abstractBro = abstractBro;
		this.velocity = velocity;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	public boolean canStart() {
		if (this.abstractBro.hasPassengers()) {
			return false;
		} else {
			this.target = this.abstractBro.getTarget();
			if (this.target == null) {
				return false;
			} else {
				double targetDistance = this.abstractBro.squaredDistanceTo(this.target);
				if (!(targetDistance < 4.0D) && !(targetDistance > 19.0D)) {
					return this.abstractBro.isOnGround() && this.abstractBro.getRandom().nextInt(toGoalTicks(49)) == 0;
				} else {
					return false;
				}
			}
		}
	}

	public boolean shouldContinue() {
		return this.abstractBro.isOnGround();
	}

	public void start() {
		final Vec3d mobVelocity = this.abstractBro.getVelocity();
		Vec3d dest = new Vec3d(this.target.getX() - this.abstractBro.getX(), 0.0D, this.target.getZ() - this.abstractBro.getZ());
		if (dest.lengthSquared() > 1.0E-7D) {
			dest = dest.normalize().multiply(1.0D).add(mobVelocity.multiply(0.2D));
		}

		this.abstractBro.setVelocity(-dest.x, this.velocity, dest.z * abstractBro.getRandom().nextFloat());
		this.abstractBro.playSound(Sounds.ENTITY_GENERIC_JUMP, 1.0F, this.abstractBro.getSoundPitch());
	}
}

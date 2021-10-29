package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.entity.types.mobs.BooEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BooSitGoal extends Goal {
	private final BooEntity tameable;

	public BooSitGoal(BooEntity tameableEntity) {
		this.tameable = tameableEntity;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	public boolean shouldContinue() {
		return this.tameable.isSitting();
	}

	public boolean canStart() {
		if (!this.tameable.isTamed()) {
			return false;
		} else if (this.tameable.isInsideWaterOrBubbleColumn()) {
			return false;
		} else {
			LivingEntity livingEntity = this.tameable.getOwner();
			if (livingEntity == null) {
				return true;
			} else {
				return (!(this.tameable.squaredDistanceTo(livingEntity) < 144.0D) || livingEntity.getAttacker() == null) && this.tameable.isSitting();
			}
		}
	}

	public void start() {
		this.tameable.getNavigation().stop();
		this.tameable.setInSittingPose(true);
	}

	public void stop() {
		this.tameable.setInSittingPose(false);
	}
}

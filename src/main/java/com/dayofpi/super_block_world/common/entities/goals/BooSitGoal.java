package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.mob.BooEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BooSitGoal extends Goal {
	private final BooEntity boo;

	public BooSitGoal(BooEntity booEntity) {
		this.boo = booEntity;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	public boolean shouldContinue() {
		return this.boo.isSitting();
	}

	public boolean canStart() {
		if (!this.boo.isTamed()) {
			return false;
		} else if (this.boo.isInsideWaterOrBubbleColumn()) {
			return false;
		} else {
			LivingEntity owner = this.boo.getOwner();
			if (owner == null) {
				return true;
			} else {
				return (!(this.boo.squaredDistanceTo(owner) < 144.0D) || owner.getAttacker() == null) && this.boo.isSitting();
			}
		}
	}

	public void start() {
		this.boo.getNavigation().stop();
		this.boo.setInSittingPose(true);
	}

	public void stop() {
		this.boo.setInSittingPose(false);
	}
}

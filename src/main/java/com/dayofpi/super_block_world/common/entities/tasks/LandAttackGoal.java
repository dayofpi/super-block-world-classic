package com.dayofpi.super_block_world.common.entities.tasks;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class LandAttackGoal extends MeleeAttackGoal {
    public LandAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        return !this.mob.isTouchingWater() && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isTouchingWater() && super.shouldContinue();
    }
}

/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.sbw_main.entity.goals;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class JumpyAttackGoal extends MeleeAttackGoal {
    public JumpyAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    public void start() {
        super.start();
        Vec3d vel = this.mob.getVelocity();
        if (this.mob.getTarget() != null)
            this.mob.setVelocity(vel.x, 0.3D, vel.z);
    }
}


/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.entity.types.bases.EnemyEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.math.Vec3d;

public class JumpyAttackGoal extends MeleeAttackGoal {
    public JumpyAttackGoal(EnemyEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    public void start() {
        super.start();
        Vec3d vel = this.mob.getVelocity();
        if (this.mob.getTarget() != null) {
            this.mob.setVelocity(vel.x, 0.3D, vel.z);
            this.mob.playSound(ModSounds.ENTITY_ENEMY_SPOT, 1.0F, mob.getSoundPitch());
        }
    }
}


package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.BlooperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.math.Vec3d;

public class BlooperAttackGoal extends MeleeAttackGoal {
    private final BlooperEntity blooper;

    public BlooperAttackGoal(BlooperEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        this.blooper = mob;
    }

    @Override
    public void start() {
        super.start();
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            Vec3d vec3d = new Vec3d(livingEntity.getX() - this.mob.getX(), livingEntity.getY() - this.mob.getY(), livingEntity.getZ() - this.mob.getZ());
            this.blooper.setSwimmingVector((float) vec3d.x / 30, (float) vec3d.y / 40, (float) vec3d.z / 30);
        }
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null && this.blooper.getRandom().nextInt(BlooperAttackGoal.toGoalTicks(40)) == 0) {
            Vec3d vec3d = new Vec3d(livingEntity.getX() - this.mob.getX(), livingEntity.getY() - this.mob.getY(), livingEntity.getZ() - this.mob.getZ());
            this.blooper.setSwimmingVector((float) vec3d.x / 30, (float) vec3d.y / 40, (float) vec3d.z / 30);
        }
        super.tick();
    }
}

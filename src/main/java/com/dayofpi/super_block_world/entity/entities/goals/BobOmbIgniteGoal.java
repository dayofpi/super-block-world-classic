package com.dayofpi.super_block_world.entity.entities.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.BobOmbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BobOmbIgniteGoal extends Goal {
    private final BobOmbEntity bobOmbEntity;
    @Nullable
    private LivingEntity target;

    public BobOmbIgniteGoal(BobOmbEntity bobOmbEntity) {
        this.bobOmbEntity = bobOmbEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.bobOmbEntity.getTarget();
        return this.bobOmbEntity.getFuseSpeed() > 0 || livingEntity != null && this.bobOmbEntity.squaredDistanceTo(livingEntity) < 9.0D;
    }

    public void start() {
        this.bobOmbEntity.getNavigation().stop();
        this.target = this.bobOmbEntity.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        if (this.target == null) {
            this.bobOmbEntity.setFuseSpeed(-1);
        } else if (this.bobOmbEntity.squaredDistanceTo(this.target) > 49.0D) {
            this.bobOmbEntity.setFuseSpeed(-1);
        } else if (!this.bobOmbEntity.getVisibilityCache().canSee(this.target)) {
            this.bobOmbEntity.setFuseSpeed(-1);
        } else {
            this.bobOmbEntity.setFuseSpeed(1);
        }
    }
}

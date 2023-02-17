package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.entity.entities.passive.YoshiEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FollowMamaGoal extends Goal {
    public static final int HORIZONTAL_CHECK_RANGE = 8;
    public static final int VERTICAL_CHECK_RANGE = 4;
    private final AnimalEntity animal;
    private final double speed;
    @Nullable
    private LivingEntity parent;
    private int delay;

    public FollowMamaGoal(AnimalEntity animal, double speed) {
        this.animal = animal;
        this.speed = speed;
    }

    public boolean canStart() {
        if (this.animal.getBreedingAge() >= 0) {
            return false;
        } else {
            List<? extends LivingEntity> list = this.animal.world.getNonSpectatingEntities(LivingEntity.class, this.animal.getBoundingBox().expand(HORIZONTAL_CHECK_RANGE, VERTICAL_CHECK_RANGE, HORIZONTAL_CHECK_RANGE));
            LivingEntity parent = null;
            double d = 1.79D;

            for (LivingEntity target : list) {
                if (target instanceof YoshiEntity || target instanceof PlayerEntity) {
                    double e = this.animal.squaredDistanceTo(target);
                    if (!(e > d)) {
                        d = e;
                        parent = target;
                    }
                }
            }

            if (parent == null) {
                return false;
            } else if (d < 9.0D) {
                return false;
            } else {
                this.parent = parent;
                return true;
            }
        }
    }

    public boolean shouldContinue() {
        if (this.animal.getBreedingAge() >= 0) {
            return false;
        } else if (this.parent == null || !this.parent.isAlive()) {
            return false;
        } else {
            double d = this.animal.squaredDistanceTo(this.parent);
            return !(d < 9.0D) && !(d > 256.0D);
        }
    }

    public void start() {
        this.delay = 0;
    }

    public void stop() {
        this.parent = null;
    }

    public void tick() {
        if (--this.delay <= 0) {
            this.delay = this.getTickCount(10);
            this.animal.getNavigation().startMovingTo(this.parent, this.speed);
        }
    }
}

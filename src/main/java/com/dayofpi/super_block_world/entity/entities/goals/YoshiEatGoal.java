package com.dayofpi.super_block_world.entity.entities.goals;

import com.dayofpi.super_block_world.entity.entities.passive.YoshiEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.Hand;

public class YoshiEatGoal extends MeleeAttackGoal {
    public YoshiEatGoal(YoshiEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        return super.canStart() && ((YoshiEntity)this.mob).getStoredEntity().isEmpty();
    }

    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        if (squaredDistance <= d && this.getCooldown() <= 0) {
            this.resetCooldown();
            ((YoshiEntity)this.mob).extendTongue();
            this.mob.swingHand(Hand.MAIN_HAND);
        }
    }

    @Override
    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.mob.getWidth() * 3.0F * this.mob.getWidth() * 3.0F + entity.getWidth();
    }
}

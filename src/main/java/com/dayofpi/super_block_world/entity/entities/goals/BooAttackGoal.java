package com.dayofpi.super_block_world.entity.entities.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.BooEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class BooAttackGoal extends MeleeAttackGoal {
    private final BooEntity boo;
    public BooAttackGoal(BooEntity boo, double speed, boolean pauseWhenMobIdle) {
        super(boo, speed, pauseWhenMobIdle);
        this.boo = boo;
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !this.boo.isShy();
    }
}

package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.BlockstepperEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class BlockstepperAttackGoal extends MeleeAttackGoal {
    public BlockstepperAttackGoal(BlockstepperEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        return ((BlockstepperEntity)this.mob).getPanicTime() == 0 && super.canStart();
    }
}

package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.mob.BooEntity;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.util.Hand;

public class BooTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    protected final BooEntity boo;

    public BooTargetGoal(BooEntity booEntity, Class<T> class_) {
        super(booEntity, class_, false);
        this.boo = booEntity;
    }

    public boolean canStart() {
        return !this.boo.isTamed() && super.canStart();
    }

    public boolean shouldContinue() {
        if (this.targetEntity != null && !this.targetEntity.getStackInHand(Hand.MAIN_HAND).isOf(ItemInit.POISON_MUSHROOM))
            return super.shouldContinue();
        else return false;
    }
}
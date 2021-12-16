package com.dayofpi.super_block_world.main.common.entity.goal;

import com.dayofpi.super_block_world.main.common.entity.mob.ghost.BooEntity;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
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
        if (this.targetEntity != null && !this.targetEntity.getStackInHand(Hand.MAIN_HAND).isOf(ItemRegistry.POISON_MUSHROOM))
            return super.shouldContinue();
        else return false;
    }
}
package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.entity.types.mobs.HammerBroEntity;
import com.dayofpi.sbw_main.item.registry.ModItems;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.util.Hand;

public class HammerAttackGoal extends ProjectileAttackGoal {
    private final HammerBroEntity entity;

    public HammerAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, float f) {
        super(rangedAttackMob, d, i, f);
        this.entity = (HammerBroEntity)rangedAttackMob;
    }

    public boolean canStart() {
        return super.canStart() && this.entity.getMainHandStack().isOf(ModItems.HAMMER);
    }

    public void start() {
        super.start();
        this.entity.setAttacking(true);
        this.entity.setCurrentHand(Hand.MAIN_HAND);
    }

    public void stop() {
        super.stop();
        this.entity.clearActiveItem();
        this.entity.setAttacking(false);
    }
}
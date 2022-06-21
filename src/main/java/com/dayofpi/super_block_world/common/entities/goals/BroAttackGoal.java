package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.abst.AbstractBro;
import com.dayofpi.super_block_world.common.entities.mob.HammerBroEntity;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.util.Hand;

public class BroAttackGoal extends ProjectileAttackGoal {
    private final AbstractBro entity;

    public BroAttackGoal(RangedAttackMob mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
        super(mob, mobSpeed, minIntervalTicks, maxIntervalTicks, maxShootRange);
        this.entity = (AbstractBro) mob;
    }

    public boolean canStart() {
        if (entity instanceof HammerBroEntity)
            return super.canStart() && this.entity.getMainHandStack().isOf(ItemInit.HAMMER);
        else return super.canStart();
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
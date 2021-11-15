package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.entity.types.bases.AbstractBro;
import com.dayofpi.sbw_main.entity.types.mobs.HammerBroEntity;
import com.dayofpi.sbw_main.item.registry.ModItems;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.util.Hand;

public class BroAttackGoal extends ProjectileAttackGoal {
    private final AbstractBro entity;

    public BroAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, int j, float f) {
        super(rangedAttackMob, d, i, j, f);
        this.entity = (AbstractBro) rangedAttackMob;
    }

    public boolean canStart() {
        if (entity instanceof HammerBroEntity)
            return super.canStart() && this.entity.getMainHandStack().isOf(ModItems.HAMMER);
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

    @Override
    public void tick() {
        super.tick();
    }
}
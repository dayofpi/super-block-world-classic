package com.dayofpi.super_block_world.entity.entities.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.BooEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.SitGoal;

public class BooSitGoal extends SitGoal {
    private final BooEntity boo;

    public BooSitGoal(BooEntity booEntity) {
        super(booEntity);
        this.boo = booEntity;
    }

    @Override
    public boolean canStart() {
        if (!this.boo.isTamed()) {
            return false;
        }
        LivingEntity livingEntity = this.boo.getOwner();
        if (livingEntity == null) {
            return true;
        }
        if (this.boo.squaredDistanceTo(livingEntity) < 144.0 && livingEntity.getAttacker() != null) {
            return false;
        }
        return this.boo.isSitting();
    }
}

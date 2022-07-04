package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class PickUpTargetGoal extends Goal {
    private final KingBobOmbEntity kingBobOmbEntity;
    @Nullable
    private LivingEntity target;

    public PickUpTargetGoal(KingBobOmbEntity kingBobOmbEntity) {
        this.kingBobOmbEntity = kingBobOmbEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.kingBobOmbEntity.isSummoning())
            return false;
        LivingEntity livingEntity = kingBobOmbEntity.getTarget();
        return livingEntity != null && this.kingBobOmbEntity.getThrowCooldown() == 0;
    }

    public void start() {
        this.kingBobOmbEntity.setLaughing(false);
        this.target = this.kingBobOmbEntity.getTarget();
    }

    @Override
    public boolean shouldContinue() {
        if (this.kingBobOmbEntity.getThrowCooldown() > 0)
            return false;
        if (!this.kingBobOmbEntity.canTarget(target))
            return false;
        if (this.kingBobOmbEntity.isSummoning())
            return false;
        return this.kingBobOmbEntity.getPassengerList().isEmpty();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (target == null)
            return;
        if (this.kingBobOmbEntity.distanceTo(target) > 3) {
            this.kingBobOmbEntity.getNavigation().startMovingTo(target, 1.2D);
        } else {
            this.target.setSneaking(false);
            this.target.startRiding(this.kingBobOmbEntity);
        }
    }
}

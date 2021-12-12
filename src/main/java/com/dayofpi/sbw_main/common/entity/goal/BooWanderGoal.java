package com.dayofpi.sbw_main.common.entity.goal;

import com.dayofpi.sbw_main.common.entity.type.mobs.BooEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BooWanderGoal extends Goal {
    private final BooEntity boo;

    public BooWanderGoal(BooEntity booEntity) {
        this.boo = booEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return boo.getNavigation().isIdle() && boo.getRandom().nextInt(10) == 0;
    }

    public boolean shouldContinue() {
        return boo.getNavigation().isFollowingPath();
    }
    public void start() {
        Vec3d randomLocation = this.getRandomLocation();
        if (randomLocation != null) {
            boo.getNavigation().startMovingAlong(boo.getNavigation().findPathTo(new BlockPos(randomLocation), 1), 0.4D);
        }
    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d randomLocation = AboveGroundTargeting.find(boo, 8, 7, boo.getX(), boo.getZ(), 1.5707964F, 3, 1);
        return randomLocation != null ? randomLocation : NoPenaltySolidTargeting.find(boo, 8, 4, -2, boo.getX(), boo.getZ(), 1.5707963705062866D);
    }
}
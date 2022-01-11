package com.dayofpi.super_block_world.common.entity.goal;

import com.dayofpi.super_block_world.common.entity.mob.ghost.BooEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BooWanderGoal extends Goal {
    private final BooEntity booEntity;

    public BooWanderGoal(BooEntity booEntity) {
        this.booEntity = booEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return booEntity.getNavigation().isIdle() && booEntity.getRandom().nextInt(10) == 0;
    }

    public boolean shouldContinue() {
        return booEntity.getNavigation().isFollowingPath();
    }
    public void start() {
        Vec3d randomLocation = this.getRandomLocation();
        if (randomLocation != null) {
            booEntity.getNavigation().startMovingAlong(booEntity.getNavigation().findPathTo(new BlockPos(randomLocation), 1), 0.4D);
        }
    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d randomLocation = AboveGroundTargeting.find(booEntity, 8, 7, booEntity.getX(), booEntity.getZ(), 1.5707964F, 4, 2);
        return randomLocation != null ? randomLocation : NoPenaltySolidTargeting.find(booEntity, 8, 4, -2, booEntity.getX(), booEntity.getZ(), 1.5707963705062866D);
    }
}
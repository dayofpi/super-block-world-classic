package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.boss.ModBossEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class StayByArenaGoal extends Goal {
    private final ModBossEntity bossEntity;
    BlockPos blockPos;

    public StayByArenaGoal(ModBossEntity bossEntity) {
        this.bossEntity = bossEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public void start() {
        this.blockPos = this.bossEntity.getArenaPos();
    }

    @Override
    public boolean canStart() {
        if (this.bossEntity.getBlockPos() == this.blockPos)
            return false;
        if (this.bossEntity.getTarget() == null)
            return true;
        return this.bossEntity.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ()) > 32;
    }

    @Override
    public void tick() {
        Path path = this.bossEntity.getNavigation().findPathTo(blockPos, 40);
        this.bossEntity.getNavigation().startMovingAlong(path, 1.0D);
    }
}

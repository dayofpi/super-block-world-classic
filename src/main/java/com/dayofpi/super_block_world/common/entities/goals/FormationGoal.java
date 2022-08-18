package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.hostile.BlockstepperEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class FormationGoal extends Goal {
    public final BlockstepperEntity blockstepper;
    private double speed;
    private static final int MAX_LENGTH = 8;
    private int counter;

    public FormationGoal(BlockstepperEntity blockstepper, double speed) {
        this.blockstepper = blockstepper;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        double e;
        BlockstepperEntity blockstepperEntity2;
        if (this.blockstepper.isLeader() || this.blockstepper.isFollowing()) {
            return false;
        }
        List<Entity> list = this.blockstepper.world.getOtherEntities(this.blockstepper, this.blockstepper.getBoundingBox().expand(9.0, 4.0, 9.0), entity -> {
            EntityType<?> entityType = entity.getType();
            return entityType == ModEntities.BLOCKSTEPPER;
        });
        BlockstepperEntity blockstepperEntity = null;
        double d = Double.MAX_VALUE;
        for (Entity entity2 : list) {
            blockstepperEntity2 = (BlockstepperEntity)entity2;
            if (!blockstepperEntity2.isFollowing() || blockstepperEntity2.hasFollower() || (e = this.blockstepper.squaredDistanceTo(blockstepperEntity2)) > d) continue;
            d = e;
            blockstepperEntity = blockstepperEntity2;
        }
        if (blockstepperEntity == null) {
            for (Entity entity2 : list) {
                blockstepperEntity2 = (BlockstepperEntity)entity2;
                if (!blockstepperEntity2.isLeader() || blockstepperEntity2.hasFollower() || (e = this.blockstepper.squaredDistanceTo(blockstepperEntity2)) > d) continue;
                d = e;
                blockstepperEntity = blockstepperEntity2;
            }
        }
        if (blockstepperEntity == null) {
            return false;
        }
        if (d < 4.0) {
            return false;
        }
        if (!blockstepperEntity.isLeader() && !this.canFollow(blockstepperEntity, 1)) {
            return false;
        }
        this.blockstepper.follow(blockstepperEntity);
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (!(this.blockstepper.getFollowing() != null && this.blockstepper.getFollowing().isAlive() && this.canFollow(this.blockstepper, 0))) {
            return false;
        }
        double d = this.blockstepper.squaredDistanceTo(this.blockstepper.getFollowing());
        if (d > 676.0) {
            if (this.speed <= 3.0) {
                this.speed *= 1.2;
                this.counter = FormationGoal.toGoalTicks(40);
                return true;
            }
            if (this.counter == 0) {
                return false;
            }
        }
        if (this.counter > 0) {
            --this.counter;
        }
        return true;
    }

    @Override
    public void stop() {
        this.blockstepper.stopFollowing();
        this.speed = 2.1;
    }

    @Override
    public void tick() {
        if (!this.blockstepper.isFollowing()) {
            return;
        }
        if (this.blockstepper.getHoldingEntity() instanceof LeashKnotEntity) {
            return;
        }
        BlockstepperEntity blockstepperEntity = this.blockstepper.getFollowing();
        if (blockstepperEntity == null)
            return;
        double d = this.blockstepper.distanceTo(blockstepperEntity);
        Vec3d vec3d = new Vec3d(blockstepperEntity.getX() - this.blockstepper.getX(), blockstepperEntity.getY() - this.blockstepper.getY(), blockstepperEntity.getZ() - this.blockstepper.getZ()).normalize().multiply(Math.max(d - 2.0, 0.0));
        this.blockstepper.getNavigation().startMovingTo(this.blockstepper.getX() + vec3d.x, this.blockstepper.getY() + vec3d.y, this.blockstepper.getZ() + vec3d.z, this.speed);
    }

    private boolean canFollow(BlockstepperEntity blockstepper, int length) {
        if (length > MAX_LENGTH) {
            return false;
        }
        if (blockstepper.getFollowing() != null) {
            if (blockstepper.getFollowing().isLeader()) {
                return true;
            }
            return this.canFollow(blockstepper.getFollowing(), ++length);
        }
        return false;
    }
}

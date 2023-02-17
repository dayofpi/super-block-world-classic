package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.BlockstepperEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class BlockstepperPanicGoal extends EscapeDangerGoal {
    public BlockstepperPanicGoal(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }

    @Override
    public boolean canStart() {
        return ((BlockstepperEntity)this.mob).getPanicTime() > 0;
    }

    @Override
    protected boolean findTarget() {
        Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 4, 1);
        if (vec3d == null) {
            return false;
        }
        int i = this.mob.getRandom().nextInt(2) * (this.mob.getRandom().nextBoolean() ? -1 : 1);
        this.targetX = vec3d.x + i;
        this.targetY = vec3d.y;
        this.targetZ = vec3d.z + i;
        return true;
    }

}

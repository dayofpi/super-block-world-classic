package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.hostile.BlooperEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FollowNannyGoal extends Goal {
    private final BlooperEntity blooperEntity;
    @Nullable
    private BlooperEntity nanny;
    private int delay;

    public FollowNannyGoal(BlooperEntity blooperEntity) {
        this.blooperEntity = blooperEntity;
    }

    @Override
    public boolean canStart() {
        if (!this.blooperEntity.isBaby()) {
            return false;
        }
        List<BlooperEntity> list = this.blooperEntity.world.getNonSpectatingEntities(BlooperEntity.class, this.blooperEntity.getBoundingBox().expand(8.0, 4.0, 8.0));
        BlooperEntity animalEntity = null;
        double d = Double.MAX_VALUE;
        for (BlooperEntity grownUps : list) {
            double e = this.blooperEntity.squaredDistanceTo(grownUps);
            if (grownUps.isBaby() || e > d) continue;
            d = e;
            animalEntity = grownUps;
        }
        if (animalEntity == null) {
            return false;
        }
        if (d < 4.0) {
            return false;
        }
        this.nanny = animalEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (!this.blooperEntity.isBaby())
            return false;
        if (this.nanny == null)
            return false;
        if (!this.nanny.isAlive())
            return false;
        double d = this.blooperEntity.squaredDistanceTo(this.nanny);
        return !(d < 9.0) && !(d > 256.0);
    }

    @Override
    public void start() {
        this.delay = 0;
    }

    @Override
    public void stop() {
        this.nanny = null;
    }

    @Override
    public void tick() {
        if (--this.delay > 0 || this.nanny == null) {
            return;
        }
        this.delay = this.getTickCount(10);
        Vec3d vec3d = new Vec3d(this.nanny.getX() - this.blooperEntity.getX(), this.nanny.getY() - this.blooperEntity.getY(), this.nanny.getZ() - this.blooperEntity.getZ());
        this.blooperEntity.setSwimmingVector((float) vec3d.x / 30, (float) vec3d.y / 40, (float) vec3d.z / 30);
    }
}

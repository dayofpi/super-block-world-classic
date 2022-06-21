package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class CleanSeedsGoal extends Goal {
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = item -> !item.cannotPickup() && item.isAlive() && item.getStack().isOf(ModItems.SMILEY_SUNFLOWER_SEED);
    protected final PathAwareEntity mob;

    public CleanSeedsGoal(PathAwareEntity mob) {
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        List<ItemEntity> list = this.mob.world.getEntitiesByClass(ItemEntity.class, this.mob.getBoundingBox().expand(8.0, 8.0, 8.0), PICKABLE_DROP_FILTER);
        return !list.isEmpty();
    }

    @Override
    public void tick() {
        List<ItemEntity> list = this.mob.world.getEntitiesByClass(ItemEntity.class, this.mob.getBoundingBox().expand(8.0, 8.0, 8.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            this.mob.getNavigation().startMovingTo(list.get(0), 1.0f);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = this.mob.world.getEntitiesByClass(ItemEntity.class, this.mob.getBoundingBox().expand(8.0, 8.0, 8.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            this.mob.getNavigation().startMovingTo(list.get(0), 1.0f);
        }
    }
}

package com.dayofpi.super_block_world.common.entity.goal;

import com.dayofpi.super_block_world.common.entity.mob.EnemyEntity;
import com.dayofpi.super_block_world.common.entity.mob.goomba.GoombaEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.Item;

import java.util.EnumSet;
import java.util.List;

public class SeekPowerUpGoal extends Goal {
    private final EnemyEntity entity;
    private final Item item;
    private final List<ItemEntity> list;

    public SeekPowerUpGoal(EnemyEntity entity, Item item) {
        this.entity = entity;
        this.item = item;
        this.list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(16, 0, 16), itemEntity -> itemEntity.getStack().isOf(item));
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(16, 0, 16), itemEntity -> itemEntity.getStack().isOf(this.item));
        if (entity instanceof GoombaEntity goombaEntity)
            return goombaEntity.getSize() == 1;
        else return !list.isEmpty();
    }

    public void tick() {
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(16, 0, 16), itemEntity -> itemEntity.getStack().isOf(this.item));
        if (!list.isEmpty()) {
            entity.getNavigation().startMovingTo(list.get(0), 1.2000000476837158D);
        }
    }

    public void start() {
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(16, 0, 16), itemEntity -> itemEntity.getStack().isOf(this.item));
        if (!list.isEmpty()) {
            entity.getNavigation().startMovingTo(list.get(0), 1.2000000476837158D);
        }

    }
}

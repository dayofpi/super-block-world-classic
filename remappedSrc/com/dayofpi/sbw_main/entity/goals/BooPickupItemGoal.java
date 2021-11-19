package com.dayofpi.sbw_main.entity.goals;

import com.dayofpi.sbw_main.entity.types.mobs.BooEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.EnumSet;
import java.util.List;

public class BooPickupItemGoal extends Goal {
    private final BooEntity boo;

    public BooPickupItemGoal(BooEntity boo) {
        this.boo = boo;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    public boolean canStart() {
        if (!boo.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else if (boo.getTarget() == null && boo.getAttacker() == null) {
            if (!boo.isTamed() || boo.isInSittingPose()) {
                return false;
            } else if (boo.getRandom().nextInt(5) != 0) {
                return false;
            } else {
                List<ItemEntity> list = boo.world.getEntitiesByClass(ItemEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), EntityPredicates.VALID_ENTITY);
                return !list.isEmpty() && boo.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    public void tick() {
        List<ItemEntity> list = boo.world.getEntitiesByClass(ItemEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), EntityPredicates.VALID_ENTITY);
        ItemStack itemStack = boo.getEquippedStack(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty() && !list.isEmpty()) {
            boo.getNavigation().startMovingTo(list.get(0), 1.2000000476837158D);
        }

    }

    public void start() {
        List<ItemEntity> list = boo.world.getEntitiesByClass(ItemEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), EntityPredicates.VALID_ENTITY);
        if (!list.isEmpty()) {
            boo.setCanPickUpLoot(true);
            boo.getNavigation().startMovingTo(list.get(0), 1.2000000476837158D);
        }

    }
}
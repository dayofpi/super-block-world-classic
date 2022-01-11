package com.dayofpi.super_block_world.common.entity.goal;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entity.mob.ghost.BooEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.List;

public class BooPickupGoal extends Goal {
    private final BooEntity boo;

    public BooPickupGoal(BooEntity booEntity) {
        this.boo = booEntity;
        this.setControls(EnumSet.of(Control.MOVE));
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
                List<ItemEntity> itemList = boo.world.getEntitiesByClass(ItemEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), EntityPredicates.VALID_ENTITY);
                List<PathAwareEntity> enemyList = boo.world.getEntitiesByClass(PathAwareEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), pathAwareEntity -> pathAwareEntity.getTarget() != null && pathAwareEntity.getTarget() == boo.getOwner() && !pathAwareEntity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty());
                return (!itemList.isEmpty() || !enemyList.isEmpty()) && boo.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    public void tick() {
        List<ItemEntity> itemList = boo.world.getEntitiesByClass(ItemEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), EntityPredicates.VALID_ENTITY);
        List<PathAwareEntity> enemyList = boo.world.getEntitiesByClass(PathAwareEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), pathAwareEntity -> pathAwareEntity.getTarget() != null && pathAwareEntity.getTarget() == boo.getOwner() && !pathAwareEntity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty());

        ItemStack itemStack = boo.getEquippedStack(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty() && !itemList.isEmpty()) {
            boo.getNavigation().startMovingTo(itemList.get(0), 1.2000000476837158D);
        } else if (itemStack.isEmpty() && !enemyList.isEmpty()) {
            boo.getNavigation().startMovingTo(enemyList.get(0), 1.2);
            PathAwareEntity enemy = enemyList.get(0);
            ItemStack robberyTarget = enemy.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!robberyTarget.isEmpty() && boo.distanceTo(enemy) <= 2) {
                boo.playSound(SoundInit.ENTITY_BOO_ATTACK, 1.0F, 1.0F);
                boo.equipStack(EquipmentSlot.MAINHAND, robberyTarget);
                enemy.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            }
        }

    }

    public void start() {
        List<ItemEntity> list = boo.world.getEntitiesByClass(ItemEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), EntityPredicates.VALID_ENTITY);
        List<PathAwareEntity> enemyList = boo.world.getEntitiesByClass(PathAwareEntity.class, boo.getBoundingBox().expand(16.0D, 16.0D, 16.0D), pathAwareEntity -> pathAwareEntity.getTarget() != null && pathAwareEntity.getTarget() == boo.getOwner() && !pathAwareEntity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty());

        if (!list.isEmpty()) {
            boo.setCanPickUpLoot(true);
            boo.getNavigation().startMovingTo(list.get(0), 1.2D);
        } else if (!enemyList.isEmpty()) {
            boo.getNavigation().startMovingTo(enemyList.get(0), 1.2);
            PathAwareEntity enemy = enemyList.get(0);
            ItemStack robberyTarget = enemy.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!robberyTarget.isEmpty() && boo.distanceTo(enemy) <= 2) {
                boo.playSound(SoundInit.ENTITY_BOO_ATTACK, 1.0F, 1.0F);
                boo.equipStack(EquipmentSlot.MAINHAND, robberyTarget);
                enemy.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            }
        }

    }
}
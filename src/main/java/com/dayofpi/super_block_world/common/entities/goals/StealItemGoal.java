package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.hostile.BooEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.List;

public class StealItemGoal extends Goal {
    private final BooEntity boo;

    public StealItemGoal(BooEntity boo) {
        this.boo = boo;
    }

    @Override
    public boolean canStart() {
        if (!this.boo.isTamed() || this.boo.isSitting()) {
            return false;
        }
        LivingEntity owner = this.boo.getOwner();
        if (owner == null) {
            return false;
        }
        if (!this.boo.getMainHandStack().isEmpty())
            return false;
        List<MobEntity> list = boo.world.getEntitiesByClass(MobEntity.class, this.boo.getBoundingBox().expand(6.0), entity -> !entity.getMainHandStack().isEmpty() && entity.getTarget() != null && entity.getTarget() == owner);
        return !list.isEmpty();
    }

    @Override
    public void start() {
        LivingEntity owner = this.boo.getOwner();
        List<MobEntity> list = boo.world.getEntitiesByClass(MobEntity.class, this.boo.getBoundingBox().expand(6.0), entity -> entity.getTarget() != null  && entity.getTarget() == owner);
        if (!list.isEmpty()) {
            MobEntity entity = list.get(0);
            this.boo.getNavigation().startMovingTo(entity, 1.2D);
            if (this.boo.distanceTo(entity) < 2) {
                this.boo.setStackInHand(Hand.MAIN_HAND, entity.getMainHandStack().copy());
                entity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                this.boo.playSound(Sounds.ENTITY_BOO_STEAL, 1.0F, this.boo.getSoundPitch());
            }
        }
    }
}

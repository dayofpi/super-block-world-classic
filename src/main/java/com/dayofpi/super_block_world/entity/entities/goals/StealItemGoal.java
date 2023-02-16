package com.dayofpi.super_block_world.entity.entities.goals;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.hostile.BooEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.List;

public class StealItemGoal extends Goal {
    private final BooEntity boo;
    private LivingEntity owner;
    private MobEntity target;

    public StealItemGoal(BooEntity boo) {
        this.boo = boo;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (!this.boo.isTamed() || this.boo.isSitting()) {
            return false;
        }
        LivingEntity livingEntity = this.boo.getOwner();
        if (livingEntity == null) {
            return false;
        }
        this.owner = livingEntity;
        if (!this.boo.getMainHandStack().isEmpty())
            return false;
        List<MobEntity> list = boo.world.getEntitiesByClass(MobEntity.class, this.boo.getBoundingBox().expand(6.0), entity -> !entity.getMainHandStack().isEmpty() && entity.getTarget() != null && entity.getTarget() == livingEntity);
        return !list.isEmpty();
    }

    @Override
    public void start() {
        List<MobEntity> list = this.boo.world.getEntitiesByClass(MobEntity.class, this.boo.getBoundingBox().expand(6.0), entity -> entity.getTarget() != null  && entity.getTarget() == this.owner);
        if (!list.isEmpty()) {
            this.target = list.get(0);
            this.boo.getNavigation().startMovingTo(this.target, 1.2D);
        }
    }

    @Override
    public void tick() {
        if (this.target != null) {
            this.boo.getNavigation().startMovingTo(this.target, 1.2D);
            if (this.target.distanceTo(this.boo) < 1.75f) {
                this.boo.setStackInHand(Hand.MAIN_HAND, this.target.getMainHandStack().copy());
                this.target.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                this.boo.playSound(Sounds.ENTITY_BOO_STEAL, 1.0F, this.boo.getSoundPitch());
            }
        }
    }
}

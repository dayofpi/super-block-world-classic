package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class CoinTemptGoal extends Goal {
    private static final TargetPredicate TEMPTING_ENTITY_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(10.0).ignoreVisibility();
    private final TargetPredicate predicate;
    protected final PathAwareEntity mob;
    private final double speed;
    @Nullable
    protected PlayerEntity closestPlayer;
    private int cooldown;

    public CoinTemptGoal(PathAwareEntity entity, double speed) {
        this.mob = entity;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        this.predicate = TEMPTING_ENTITY_PREDICATE.copy().setPredicate(this::isTemptedBy);
    }

    @Override
    public boolean canStart() {
        if (this.mob.getTarget() != null)
            return false;
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.closestPlayer = this.mob.world.getClosestPlayer(this.predicate, this.mob);
        return this.closestPlayer != null;
    }

    private boolean isTemptedBy(LivingEntity entity) {
        if (!entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.SHY_GUY_MASK))
            return false;
        return entity.getMainHandStack().isOf(ModItems.COIN) || entity.getOffHandStack().isOf(ModItems.COIN);
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
        this.mob.getNavigation().stop();
        this.cooldown = TemptGoal.toGoalTicks(100);
    }

    @Override
    public void tick() {
        this.mob.getLookControl().lookAt(this.closestPlayer, this.mob.getMaxHeadRotation() + 20, this.mob.getMaxLookPitchChange());
        if (this.mob.squaredDistanceTo(this.closestPlayer) < 6.25) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().startMovingTo(this.closestPlayer, this.speed);
        }
    }
}

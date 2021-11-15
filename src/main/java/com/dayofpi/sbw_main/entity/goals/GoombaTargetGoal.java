/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.sbw_main.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class GoombaTargetGoal<T extends LivingEntity>
extends TrackTargetGoal {
    protected final Class<T> targetClass;
    /**
     * The reciprocal of chance to actually search for a target on every tick
     * when this goal is not started. This is also the average number of ticks
     * between each search (as in a poisson distribution).
     */
    protected final int reciprocalChance;
    @Nullable
    protected LivingEntity targetEntity;
    protected TargetPredicate targetPredicate;

    public GoombaTargetGoal(MobEntity mobEntity, Class<T> class_, boolean bl) {
        this(mobEntity, class_, bl, false);
    }

    public GoombaTargetGoal(MobEntity mobEntity, Class<T> class_, boolean bl, boolean bl2) {
        this(mobEntity, class_, 10, bl, bl2, null);
    }

    public GoombaTargetGoal(MobEntity mobEntity, Class<T> class_, int i, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mobEntity, bl, bl2);
        this.targetClass = class_;
        this.reciprocalChance = GoombaTargetGoal.toGoalTicks(i);
        this.setControls(EnumSet.of(Control.TARGET));
        this.targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(predicate);
    }

    @Override
    public boolean canStart() {
        if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
            return false;
        }
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0, distance);
    }

    protected void findClosestTarget() {
        this.targetEntity = this.targetClass == PlayerEntity.class || this.targetClass == ServerPlayerEntity.class ? this.mob.world.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : this.mob.world.getClosestEntity(this.mob.world.getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()), livingEntity -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    @Override
    public void start() {
        this.mob.setTarget(this.targetEntity);
        super.start();
        if (this.mob.isOnGround())
            this.mob.addVelocity(0, 0.3, 0);
    }

    public void setTargetEntity(@Nullable LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }
}


/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.common.entity.goal;

import com.dayofpi.super_block_world.common.entity.mob.MummyToadEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;

import java.util.function.Predicate;

public class MummyTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    private final MummyToadEntity mummyToadEntity;

    public MummyTargetGoal(MummyToadEntity mob, Class<T> targetClass) {
        super(mob, targetClass, true);
        this.mummyToadEntity = mob;
    }

    public MummyTargetGoal(MummyToadEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, 10, checkVisibility, false, targetPredicate);
        this.mummyToadEntity = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !mummyToadEntity.isHidden();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !mummyToadEntity.isHidden();
    }
}


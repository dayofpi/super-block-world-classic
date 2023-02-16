/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.entity.entities.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.MummyMeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;

import java.util.function.Predicate;

public class MummyMeTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    private final MummyMeEntity mummyMeEntity;

    public MummyMeTargetGoal(MummyMeEntity mob, Class<T> targetClass) {
        super(mob, targetClass, true);
        this.mummyMeEntity = mob;
    }

    public MummyMeTargetGoal(MummyMeEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, 10, checkVisibility, false, targetPredicate);
        this.mummyMeEntity = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !mummyMeEntity.isHidden();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !mummyMeEntity.isHidden();
    }
}


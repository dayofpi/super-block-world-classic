/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.common.entities.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

import java.util.Optional;

public class MeleeAttackFixTask
extends Task<MobEntity> {
    private final int interval;

    public MeleeAttackFixTask(int interval) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT));
        this.interval = interval;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
        LivingEntity livingEntity = this.getAttackTarget(mobEntity);
        return !this.isHoldingUsableRangedWeapon(mobEntity) && livingEntity != null && mobEntity.isInAttackRange(livingEntity);
    }

    private boolean isHoldingUsableRangedWeapon(MobEntity entity) {
        return entity.isHolding(stack -> {
            Item item = stack.getItem();
            return item instanceof RangedWeaponItem && entity.canUseRangedWeapon((RangedWeaponItem)item);
        });
    }

    @Override
    protected void run(ServerWorld serverWorld, MobEntity entity, long l) {
        LivingEntity livingEntity = this.getAttackTarget(entity);
        LookTargetUtil.lookAt(entity, livingEntity);
        entity.swingHand(Hand.MAIN_HAND);
        entity.tryAttack(livingEntity);
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);
    }

    private LivingEntity getAttackTarget(MobEntity entity) {
        Optional<LivingEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optional.orElse(null);
    }
}


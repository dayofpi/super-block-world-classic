package com.dayofpi.super_block_world.entity.entities.brains;

import com.dayofpi.super_block_world.entity.entities.hostile.CheepCheepEntity;
import com.dayofpi.super_block_world.entity.entities.tasks.MeleeAttackFixTask;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class CheepCheepBrain {
    public static Brain<?> create(Brain<CheepCheepEntity> brain) {
        CheepCheepBrain.addCoreActivities(brain);
        CheepCheepBrain.addIdleActivities(brain);
        CheepCheepBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<CheepCheepEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<CheepCheepEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))), Pair.of(1, new ConditionalTask<>(ImmutableMap.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT), new UpdateAttackTargetTask<>(entity -> entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)))), Pair.of(2, new AquaticStrollTask(1.0f)), Pair.of(2, new StrollTask(0.8f)), Pair.of(2, new CompositeTask<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(Pair.of(new StrollTask(0.5f), 2), Pair.of(new GoTowardsLookTarget(0.5f, 3), 3), Pair.of(new WaitTask(30, 60), 5))))));
    }

    private static void addFightActivities(Brain<CheepCheepEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 0, ImmutableList.of(new ForgetAttackTargetTask<>(), new RangedApproachTask(0.9f), new MeleeAttackFixTask(20), new ForgetTask<>(Entity::isInsideWaterOrBubbleColumn, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
    }

    public static void updateActivities(CheepCheepEntity cheepCheep) {
        cheepCheep.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT));
    }
}

package com.dayofpi.super_block_world.entity.entities.brains;

import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.tasks.PickUpTargetTask;
import com.dayofpi.super_block_world.entity.entities.tasks.ThrowBobOmbTask;
import com.dayofpi.super_block_world.entity.entities.tasks.ThrowBombTask;
import com.dayofpi.super_block_world.entity.entities.tasks.ThrowTargetTask;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class KingBobOmbBrain {
    public static Brain<?> create(Brain<KingBobOmbEntity> brain) {
        KingBobOmbBrain.addCoreActivities(brain);
        KingBobOmbBrain.addIdleActivities(brain);
        KingBobOmbBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void updateActivities(KingBobOmbEntity kingBobOmb) {
        Brain<KingBobOmbEntity> brain = kingBobOmb.getBrain();
        brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    private static void addCoreActivities(Brain<KingBobOmbEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.8f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask()
                )
        );
    }

    private static void addIdleActivities(Brain<KingBobOmbEntity> brain) {
        TimeLimitedTask<LivingEntity> followPlayerTask = new TimeLimitedTask<>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60));
        GoTowardsLookTarget goTowardsLookTargetTask = new GoTowardsLookTarget(1.0F, 3);
        GoToNearbyPositionTask goToNearbyPositionTask = new GoToNearbyPositionTask(MemoryModuleType.HOME, 1.0f, 2, 10);
        WaitTask waitTask = new WaitTask(30, 60);
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new UpdateAttackTargetTask<>(ModBossEntity::getAttackTarget)),
                        Pair.of(1, followPlayerTask),
                        Pair.of(2, new CompositeTask<>(
                                ImmutableMap.of(MemoryModuleType.WALK_TARGET,
                                MemoryModuleState.VALUE_ABSENT),
                                ImmutableSet.of(), CompositeTask.Order.ORDERED,
                                CompositeTask.RunMode.TRY_ALL,
                                ImmutableList.of(
                                        Pair.of(goTowardsLookTargetTask, 3),
                                        Pair.of(goToNearbyPositionTask, 1),
                                        Pair.of(waitTask, 5)
                                )))));
    }

    private static void addFightActivities(Brain<KingBobOmbEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        new ForgetAttackTargetTask<>(),
                        new RangedApproachTask(1.0F),
                        new ThrowBobOmbTask(),
                        new ThrowBombTask(),
                        new PickUpTargetTask(60),
                        new ThrowTargetTask(70),
                        new ConditionalTask<>(KingBobOmbBrain::isCarryingEntity, new WalkTask(1.2F))
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isCarryingEntity(KingBobOmbEntity kingBobOmb) {
        return kingBobOmb.getCarriedEntity() != null;
    }
}

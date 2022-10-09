package com.dayofpi.super_block_world.common.entities.brains;

import com.dayofpi.super_block_world.common.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.common.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.common.entities.tasks.SpinTask;
import com.dayofpi.super_block_world.common.entities.tasks.SpitGoopTask;
import com.dayofpi.super_block_world.common.entities.tasks.TweesterTask;
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

public class PeteyPiranhaBrain {
    public static Brain<?> create(Brain<PeteyPiranhaEntity> brain) {
        PeteyPiranhaBrain.addCoreActivities(brain);
        PeteyPiranhaBrain.addIdleActivities(brain);
        PeteyPiranhaBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void updateActivities(PeteyPiranhaEntity peteyPiranha) {
        Brain<PeteyPiranhaEntity> brain = peteyPiranha.getBrain();
        brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    private static void addCoreActivities(Brain<PeteyPiranhaEntity> brain) {
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

    private static void addIdleActivities(Brain<PeteyPiranhaEntity> brain) {
        TimeLimitedTask<LivingEntity> followPlayerTask = new TimeLimitedTask<>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60));
        GoToNearbyPositionTask goToNearbyPositionTask = new GoToNearbyPositionTask(MemoryModuleType.HOME, 1.0f, 1, 10);
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
                                        Pair.of(goToNearbyPositionTask, 1),
                                        Pair.of(waitTask, 5)
                                )))));
    }

    private static void addFightActivities(Brain<PeteyPiranhaEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        new ForgetAttackTargetTask<>(),
                        new RangedApproachTask(1.5F),
                        new MeleeAttackTask(18),
                        new SpitGoopTask(),
                        new SpinTask(),
                        new TweesterTask()
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }
}

package com.dayofpi.super_block_world.entity.brains;

import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.tasks.PickUpTargetTask;
import com.dayofpi.super_block_world.entity.tasks.ThrowBobOmbTask;
import com.dayofpi.super_block_world.entity.tasks.ThrowBombTask;
import com.dayofpi.super_block_world.entity.tasks.ThrowTargetTask;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class KingBobOmbBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super KingBobOmbEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_PLAYERS);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.SONIC_BOOM_COOLDOWN);

    public static Brain<?> create(Brain<KingBobOmbEntity> brain) {
        KingBobOmbBrain.addCoreActivities(brain);
        KingBobOmbBrain.addIdleActivities(brain);
        KingBobOmbBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static Brain.Profile<KingBobOmbEntity> createProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
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


        WaitTask waitTask = new WaitTask(30, 60);
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, UpdateAttackTargetTask.create(ModBossEntity::getAttackTarget)),
                        Pair.of(1, FollowMobWithIntervalTask.follow(EntityType.PLAYER, 6.0f, UniformIntProvider.create(30, 60))),
                        Pair.of(2, new CompositeTask<>(
                                ImmutableMap.of(MemoryModuleType.WALK_TARGET,
                                        MemoryModuleState.VALUE_ABSENT),
                                ImmutableSet.of(), CompositeTask.Order.ORDERED,
                                CompositeTask.RunMode.TRY_ALL,
                                ImmutableList.of(
                                        Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 3),
                                        Pair.of(GoToNearbyPositionTask.create(MemoryModuleType.HOME, 1.0f, 2, 10), 1),
                                        Pair.of(waitTask, 5)
                                )))));
    }

    private static void addFightActivities(Brain<KingBobOmbEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        ForgetAttackTargetTask.create(),
                        RangedApproachTask.create(1.0F),
                        new ThrowBobOmbTask(),
                        new ThrowBombTask(),
                        new PickUpTargetTask(60),
                        new ThrowTargetTask(70)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }
}

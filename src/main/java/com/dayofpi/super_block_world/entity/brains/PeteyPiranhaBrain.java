package com.dayofpi.super_block_world.entity.brains;

import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.entity.tasks.SpinTask;
import com.dayofpi.super_block_world.entity.tasks.SpitGoopTask;
import com.dayofpi.super_block_world.entity.tasks.TweesterTask;
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

public class PeteyPiranhaBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super PeteyPiranhaEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_PLAYERS, SensorType.NEAREST_LIVING_ENTITIES);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN);

    public static Brain<?> create(Brain<PeteyPiranhaEntity> brain) {
        PeteyPiranhaBrain.addCoreActivities(brain);
        PeteyPiranhaBrain.addIdleActivities(brain);
        PeteyPiranhaBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static Brain.Profile<PeteyPiranhaEntity> createProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
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
                                        Pair.of(GoToNearbyPositionTask.create(MemoryModuleType.HOME, 1.0f, 1, 10), 1),
                                        Pair.of(new WaitTask(30, 60), 5)
                                )))));
    }

    private static void addFightActivities(Brain<PeteyPiranhaEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        ForgetAttackTargetTask.create(),
                        RangedApproachTask.create(1.5F),
                        MeleeAttackTask.create(18),
                        new SpitGoopTask(),
                        new SpinTask(),
                        new TweesterTask()
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }
}

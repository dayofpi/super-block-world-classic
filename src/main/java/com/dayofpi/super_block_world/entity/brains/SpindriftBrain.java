package com.dayofpi.super_block_world.entity.brains;

import com.dayofpi.super_block_world.entity.entities.passive.SpindriftEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class SpindriftBrain {
    public static Brain<?> create(Brain<SpindriftEntity> brain) {
        SpindriftBrain.addCoreActivities(brain);
        SpindriftBrain.addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<SpindriftEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new WalkTask(2.0f), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<SpindriftEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, FollowMobWithIntervalTask.follow(EntityType.PLAYER, 6.0f, UniformIntProvider.create(30, 60))), Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(StrollTask.create(1.0f), 2), Pair.of(GoTowardsLookTargetTask.create(1.0f, 3), 2), Pair.of(new WaitTask(30, 60), 1))))));
    }

    public static void updateActivities(SpindriftEntity spindrift) {
        spindrift.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }
}

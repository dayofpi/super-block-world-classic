package com.dayofpi.super_block_world.entity.brains;

import com.dayofpi.super_block_world.entity.entities.passive.LilOinkEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class LilOinkBrain {
    public static Brain<?> create(Brain<LilOinkEntity> brain) {
        LilOinkBrain.addCoreActivities(brain);
        LilOinkBrain.addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<LilOinkEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new WalkTask(2.0f), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<LilOinkEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(2, WalkTowardClosestAdultTask.create(UniformIntProvider.create(5, 16), 1.25f)), Pair.of(0, FollowMobWithIntervalTask.follow(EntityType.PLAYER, 6.0f, UniformIntProvider.create(30, 60))),
                Pair.of(2, new CompositeTask<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(Pair.of(StrollTask.create(0.5f), 2), Pair.of(GoTowardsLookTargetTask.create(0.5f, 3), 3), Pair.of(new WaitTask(30, 60), 5))))));
    }

    public static void updateActivities(LilOinkEntity lilOink) {
        lilOink.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }
}

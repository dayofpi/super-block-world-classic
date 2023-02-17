package com.dayofpi.super_block_world.entity.brains;

import com.dayofpi.super_block_world.entity.tasks.FollowPrincessTask;
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
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ToadBrain {
    private static final UniformIntProvider WALK_TOWARD_ADULT_RANGE = UniformIntProvider.create(5, 16);

    public static Brain<?> create(Brain<PassiveEntity> brain) {
        ToadBrain.addCoreActivities(brain);
        ToadBrain.addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void setCurrentPosAsHome(PassiveEntity toad) {
        GlobalPos globalPos = GlobalPos.create(toad.world.getRegistryKey(), toad.getBlockPos());
        toad.getBrain().remember(MemoryModuleType.HOME, globalPos);
    }

    private static void addCoreActivities(Brain<PassiveEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), OpenDoorsTask.create(), new WalkTask(2.0f), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<PassiveEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, FollowMobWithIntervalTask.follow(EntityType.PLAYER, 6.0f, UniformIntProvider.create(30, 60))), Pair.of(1, new FollowPrincessTask(UniformIntProvider.create(2, 10), 1.5F)), Pair.of(3, WalkTowardClosestAdultTask.create(WALK_TOWARD_ADULT_RANGE, 1.5f)), Pair.of(4, new RandomLookAroundTask(UniformIntProvider.create(150, 250), 30.0f, 0.0f, 0.0f)), Pair.of(5, new RandomTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(Pair.of(StrollTask.create(1.0f), 1), Pair.of(GoTowardsLookTargetTask.create(1.0f, 3), 1), Pair.of(new WaitTask(30, 60), 1), Pair.of(WalkHomeTask.create(1.0F), 1))))));
    }

    public static void updateActivities(PassiveEntity toad) {
        toad.getBrain().resetPossibleActivities(ImmutableList.of(Activity.AVOID, Activity.IDLE));
    }
}

package com.dayofpi.super_block_world.common.entities.brains;

import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.common.entities.tasks.FollowMagnetTask;
import com.dayofpi.super_block_world.common.entities.tasks.StickToFuzzballTask;
import com.dayofpi.super_block_world.common.entities.tasks.StickTogetherTask;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.NoPenaltyStrollTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class FuzzyBrain {
    public static Brain<?> create(Brain<FuzzyEntity> brain) {
        FuzzyBrain.addCoreActivities(brain);
        FuzzyBrain.addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<FuzzyEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<FuzzyEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new StickToFuzzballTask(15,1.2f)), Pair.of(1, new FollowMagnetTask(UniformIntProvider.create(5, 16), 1.4f)), Pair.of(2, new StickTogetherTask(UniformIntProvider.create(5, 16), 1.0f)), Pair.of(2, new NoPenaltyStrollTask(1.0f))));
    }

    public static void updateActivities(FuzzyEntity fuzzy) {
        fuzzy.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }
}

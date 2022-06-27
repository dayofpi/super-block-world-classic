package com.dayofpi.super_block_world.common.entities.brains;

import com.dayofpi.super_block_world.common.entities.Toad;
import com.dayofpi.super_block_world.common.entities.tasks.FollowPrincessTask;
import com.dayofpi.super_block_world.common.entities.tasks.ToadLookTask;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ToadBrain {
    public static Brain<?> create(Brain<PassiveEntity> brain) {
        ToadBrain.addCoreActivities(brain);
        ToadBrain.addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<PassiveEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new OpenDoorsTask(), new WalkTask(2.0f), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<PassiveEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask<>(new ToadLookTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))), Pair.of(1, new WalkTowardClosestAdultTask<>(UniformIntProvider.create(5, 16), 0.8f)), Pair.of(2, new FollowPrincessTask(UniformIntProvider.create(5, 16), 0.8f)), Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(new ConditionalTask<>(entity -> entity instanceof Toad && ((Toad) entity).getAttentionCooldown() == 0, new StrollTask(0.7f)), 2), Pair.of(new GoTowardsLookTarget(1.0f, 3), 2), Pair.of(new WaitTask(30, 60), 1))))));
    }

    public static void updateActivities(PassiveEntity toad) {
        toad.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }
}

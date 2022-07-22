package com.dayofpi.super_block_world.common.entities.brains;

import com.dayofpi.super_block_world.common.entities.Toad;
import com.dayofpi.super_block_world.common.entities.tasks.CowardTask;
import com.dayofpi.super_block_world.common.entities.tasks.FollowPrincessTask;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ToadBrain {
    public static Brain<?> create(Brain<PassiveEntity> brain) {
        ToadBrain.addCoreActivities(brain);
        ToadBrain.addIdleActivities(brain);
        ToadBrain.addAvoidActivities(brain);
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
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new OpenDoorsTask(), new WalkTask(2.0f), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<PassiveEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask<>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))), Pair.of(1, new WalkTowardClosestAdultTask<>(UniformIntProvider.create(5, 16), 0.8f)), Pair.of(2, new FollowPrincessTask(UniformIntProvider.create(5, 16), 0.8f)), Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(new ConditionalTask<>(entity -> entity instanceof Toad && ((Toad) entity).getAttentionCooldown() == 0, new StrollTask(0.7f)), 2), Pair.of(new GoTowardsLookTarget(1.0f, 3), 2), Pair.of(new GoToNearbyPositionTask(MemoryModuleType.HOME, 0.6f, 2, 100), 1), Pair.of(new WaitTask(30, 60), 1))))));
    }

    private static void addAvoidActivities(Brain<PassiveEntity> brain) {
        brain.setTaskList(Activity.AVOID, 0, ImmutableList.of(GoToRememberedPositionTask.toEntity(MemoryModuleType.AVOID_TARGET, 1.3f, 15, false), ToadBrain.makeRandomWalkTask(), new TimeLimitedTask<>(new FollowMobTask(8.0f), UniformIntProvider.create(30, 60)), new CowardTask(UniformIntProvider.create(5, 16))), MemoryModuleType.AVOID_TARGET);
    }

    private static RandomTask<PassiveEntity> makeRandomWalkTask() {
        return new RandomTask<>(ImmutableList.of(Pair.of(new StrollTask(0.4f), 2), Pair.of(new GoTowardsLookTarget(0.4f, 3), 2), Pair.of(new WaitTask(30, 60), 1)));
    }

    public static void updateActivities(PassiveEntity toad) {
        toad.getBrain().resetPossibleActivities(ImmutableList.of(Activity.AVOID, Activity.IDLE));
    }
}

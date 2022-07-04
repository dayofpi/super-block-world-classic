package com.dayofpi.super_block_world.common.entities.brains;

import com.dayofpi.super_block_world.common.entities.passive.AbstractKoopa;
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

public class KoopaBrain {
    public static Brain<?> create(Brain<AbstractKoopa> brain) {
        KoopaBrain.addCoreActivities(brain);
        KoopaBrain.addIdleActivities(brain);
        KoopaBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<AbstractKoopa> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<AbstractKoopa> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))), Pair.of(2, new CompositeTask<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(Pair.of(new StrollTask(0.5f), 2), Pair.of(new GoTowardsLookTarget(0.5f, 3), 3), Pair.of(new WaitTask(30, 60), 5))))));
    }

    private static void addFightActivities(Brain<AbstractKoopa> brain) {
        brain.setTaskList(Activity.FIGHT, 0, ImmutableList.of(new ForgetAttackTargetTask<>(), new RangedApproachTask(1.0f), new MeleeAttackTask(20)), MemoryModuleType.ATTACK_TARGET);
    }

    public static void updateActivities(AbstractKoopa koopaTroopa) {
        koopaTroopa.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }
}

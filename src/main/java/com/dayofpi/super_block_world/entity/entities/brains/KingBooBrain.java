package com.dayofpi.super_block_world.entity.entities.brains;

import com.dayofpi.super_block_world.entity.entities.boss.KingBooEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.tasks.DarkLightningTask;
import com.dayofpi.super_block_world.entity.entities.tasks.FireCircleTask;
import com.dayofpi.super_block_world.entity.entities.tasks.HomingFlameTask;
import com.dayofpi.super_block_world.entity.entities.tasks.MeleeAttackFixTask;
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

public class KingBooBrain {
    public static Brain<?> create(Brain<KingBooEntity> brain) {
        KingBooBrain.addCoreActivities(brain);
        KingBooBrain.addIdleActivities(brain);
        KingBooBrain.addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void updateActivities(KingBooEntity kingBoo) {
        Brain<KingBooEntity> brain = kingBoo.getBrain();
        brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    private static void addCoreActivities(Brain<KingBooEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new LookAroundTask(45, 90), new WanderAroundTask()));
    }

    private static void addIdleActivities(Brain<KingBooEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new UpdateAttackTargetTask<>(ModBossEntity::getAttackTarget)), Pair.of(1, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))), Pair.of(2, new CompositeTask<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(Pair.of(new NoPenaltyStrollTask(1.0F), 2), Pair.of(new GoTowardsLookTarget(1.0F, 3), 3), Pair.of(new GoToNearbyPositionTask(MemoryModuleType.HOME, 1.0f, 2, 100), 1), Pair.of(new WaitTask(30, 60), 5))))));
    }

    private static void addFightActivities(Brain<KingBooEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 0, ImmutableList.of(new ForgetAttackTargetTask<>(), new RangedApproachTask(1.6f), new FireCircleTask(), new DarkLightningTask(), new HomingFlameTask(), new MeleeAttackFixTask(20)), MemoryModuleType.ATTACK_TARGET);
    }
}

package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.common.entities.Toad;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;
import java.util.function.Predicate;

public class ToadLookTask extends Task<PassiveEntity> {
    private final Predicate<LivingEntity> predicate;
    private final float maxDistanceSquared;
    private Optional<LivingEntity> target;

    public ToadLookTask(EntityType<?> entityType, float maxDistance) {
        this((entity) -> entityType.equals(entity.getType()), maxDistance);
    }

    public ToadLookTask(Predicate<LivingEntity> predicate, float maxDistance) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT));
        this.target = Optional.empty();
        this.predicate = predicate;
        this.maxDistanceSquared = maxDistance * maxDistance;
    }

    protected boolean shouldRun(ServerWorld world, PassiveEntity entity) {
        LivingTargetCache livingTargetCache = entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get();
        this.target = livingTargetCache.findFirst(this.predicate.and((livingEntity2) -> livingEntity2.squaredDistanceTo(entity) <= (double)this.maxDistanceSquared));
        return this.target.isPresent();
    }

    protected void run(ServerWorld world, PassiveEntity entity, long time) {
        if (entity instanceof Toad)
            if (world.random.nextInt(5) == 0)
                ((Toad) entity).setToadState(1);
        entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.target.get(), true));
        this.target = Optional.empty();
    }

    @Override
    protected void finishRunning(ServerWorld world, PassiveEntity entity, long time) {
        super.finishRunning(world, entity, time);
        if (entity instanceof Toad)
            ((Toad) entity).setToadState(0);
    }
}

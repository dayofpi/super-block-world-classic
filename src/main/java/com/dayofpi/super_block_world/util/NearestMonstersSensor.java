package com.dayofpi.super_block_world.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class NearestMonstersSensor<T extends LivingEntity> extends Sensor<T> {
    @Override
    protected void sense(ServerWorld world, T entity) {
        Box box = entity.getBoundingBox().expand(this.getHorizontalExpansion(), this.getHeightExpansion(), this.getHorizontalExpansion());
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, e -> e != entity && e.isAlive() && entity instanceof Monster);
        list.sort(Comparator.comparingDouble(entity::squaredDistanceTo));
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.MOBS, list);
        brain.remember(MemoryModuleType.VISIBLE_MOBS, new LivingTargetCache(entity, list));
    }

    private int getHorizontalExpansion() {
        return 16;
    }

    private int getHeightExpansion() {
        return 16;
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS);
    }
}

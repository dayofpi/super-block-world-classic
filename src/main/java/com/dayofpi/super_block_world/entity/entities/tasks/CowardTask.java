package com.dayofpi.super_block_world.entity.entities.tasks;

import com.dayofpi.super_block_world.entity.entities.Toad;
import com.dayofpi.super_block_world.item.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class CowardTask extends Task<PassiveEntity> {
    private final UniformIntProvider executionRange;

    public CowardTask(UniformIntProvider executionRange) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleState.VALUE_PRESENT));
        this.executionRange = executionRange;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PassiveEntity entity) {
        MobEntity nemesis = this.getNearestNemesis(entity);
        return entity.isInRange(nemesis, this.executionRange.getMax() + 1) && !entity.isInRange(nemesis, this.executionRange.getMin()) && nemesis.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.PRINCESS_CROWN);
    }

    @Override
    protected void run(ServerWorld world, PassiveEntity entity, long time) {
        if (entity instanceof Toad)
            ((Toad) entity).setToadState(2);
    }

    private MobEntity getNearestNemesis(PassiveEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS).get();
    }
}

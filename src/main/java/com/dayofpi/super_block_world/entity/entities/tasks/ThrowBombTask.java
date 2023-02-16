package com.dayofpi.super_block_world.entity.entities.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.projectile.BombEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;

public class ThrowBombTask extends Task<KingBobOmbEntity> {
    private static final int RUN_TIME = MathHelper.ceil(25.0f);

    public ThrowBombTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBobOmbEntity entity) {
        if (entity.getNextAttack() != 1)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 20.0, 10.0) && !entity.isInRange(livingEntity, 5.0, 5.0)).isPresent();
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBobOmbEntity entity, long l) {
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if (optionalMemory.isEmpty())
            return;
        entity.swingHand(Hand.MAIN_HAND);
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.playSound(Sounds.ITEM_THROW, 3.0F, 1.0F);
        BombEntity bomb = new BombEntity(serverWorld, entity);
        bomb.setVelocity(entity.getX(), entity.getY() + 0.5D, entity.getZ(), 0.6F, 1.0F);
        serverWorld.spawnEntity(bomb);
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, KingBobOmbEntity entity, long l) {
        ModBossEntity.cooldown(entity, 70);
        entity.setNextAttack(entity.getRandom().nextInt(entity.getMaxAttacks()));
    }
}

package com.dayofpi.super_block_world.entity.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.entity.entities.boss.KingBobOmbEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.hostile.BobOmbEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.Optional;

public class ThrowBobOmbTask extends MultiTickTask<KingBobOmbEntity> {
    private static final int RUN_TIME = MathHelper.ceil(10.0f);

    public ThrowBobOmbTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBobOmbEntity entity) {
        if (entity.getNextAttack() != 0)
            return false;
        List<BobOmbEntity> list = serverWorld.getEntitiesByClass(BobOmbEntity.class, entity.getBoundingBox().expand(10.0), LivingEntity::isAlive);
        if (list.size() > 2)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 20.0, 10.0)).isPresent();
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBobOmbEntity entity, long l) {
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if (optionalMemory.isEmpty())
            return;
        entity.swingHand(Hand.MAIN_HAND);
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.playSound(Sounds.ENTITY_KING_BOB_OMB_THROW, 3.0F, 1.0F);
        BobOmbEntity bobOmb = ModEntities.BOB_OMB.create(serverWorld);
        if (bobOmb != null) {
            bobOmb.setTarget(optionalMemory.get());
            bobOmb.updatePositionAndAngles(entity.getX(), entity.getY() + 1, entity.getZ(), entity.getYaw(), 0.0F);
            Vec3i vector = entity.getHorizontalFacing().getVector();
            bobOmb.setVelocity(vector.getX() * 0.5, 1.7, vector.getZ() * 0.5);
            serverWorld.spawnEntity(bobOmb);
        }
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, KingBobOmbEntity entity, long l) {
        ModBossEntity.cooldown(entity, 50);
        entity.setNextAttack(entity.getRandom().nextInt(entity.getMaxAttacks()));
    }
}

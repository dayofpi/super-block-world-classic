package com.dayofpi.super_block_world.entity.tasks;

import com.dayofpi.super_block_world.ModTags;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.boss.KingBooEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Optional;

public class FireCircleTask extends MultiTickTask<KingBooEntity> {
    private static final int SOUND_DELAY = MathHelper.ceil(34.0);
    private static final int RUN_TIME = MathHelper.ceil(60.0f);

    public FireCircleTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBooEntity entity) {
        if (entity.getNextAttack() != 0)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 15.0, 2.0)).isPresent();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, KingBooEntity entity, long l) {
        return true;
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBooEntity entity, long l) {
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);
        serverWorld.sendEntityStatus(entity, EntityStatuses.SONIC_BOOM);
        entity.playSound(Sounds.ENTITY_KING_BOO_CHARGE, 3.0f, 1.0f);
    }

    @Override
    protected void keepRunning(ServerWorld world, KingBooEntity entity, long l) {
        entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> entity.getLookControl().lookAt(target.getPos()));
        if (entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            return;
        }
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - SOUND_DELAY);
        entity.playSound(Sounds.ENTITY_KING_BOO_UNLEASH, 3.0F, 1.0F);
        List<Entity> list = world.getOtherEntities(entity, Box.from(Vec3d.ofCenter(entity.getBlockPos())).expand(10, 1, 10), EntityPredicates.VALID_LIVING_ENTITY);
        for (int i = 0; i < 50; ++i) {
            world.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY(), entity.getZ(), 0, 10, 0.0D, 10, 0.1D);
        }
        for (Entity target : list) {
            target.damage(DamageSource.magic(entity, entity), 5);
        }
        for (BlockPos blockPos : BlockPos.iterateOutwards(entity.getBlockPos(), 10, 2, 10)) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isIn(ModTags.FIRE_CIRCLE_BREAK_TARGETS)) {
                world.breakBlock(blockPos, true);
            } else if (blockState.isIn(ModTags.FIRE_CIRCLE_LIT_TARGETS) && blockState.get(Properties.LIT)) {
                world.setBlockState(blockPos, blockState.with(Properties.LIT, false));
            }
        }
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, KingBooEntity entity, long l) {
        ModBossEntity.cooldown(entity, 80);
        Random random = entity.getRandom();
        if (random.nextInt(5) == 0)
            entity.setNextAttack(2);
        else
            entity.setNextAttack(entity.getRandom().nextInt(entity.getMaxAttacks()));
    }
}

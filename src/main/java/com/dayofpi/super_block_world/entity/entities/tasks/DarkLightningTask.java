package com.dayofpi.super_block_world.entity.entities.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.boss.KingBooEntity;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Optional;

public class DarkLightningTask extends Task<KingBooEntity> {
    private static final int SOUND_DELAY = MathHelper.ceil(34.0);
    private static final int RUN_TIME = MathHelper.ceil(40.0f);
    private BlockPos blockPos;

    public DarkLightningTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, KingBooEntity entity) {
        if (entity.getNextAttack() != 1)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 15.0, 5.0)).isPresent();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, KingBooEntity entity, long l) {
        return true;
    }

    @Override
    protected void run(ServerWorld serverWorld, KingBooEntity entity, long l) {
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);

        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        this.blockPos = optionalMemory.isPresent() ? optionalMemory.get().getBlockPos() : entity.getBlockPos();
        serverWorld.spawnParticles(ParticleTypes.CLOUD, blockPos.getX() + 0.5, blockPos.getY() + 2, blockPos.getZ() + 0.5, 2, 0.2D, 0.2D, 0.2D, 0.0D);
        entity.playSound(Sounds.ENTITY_KING_BOO_THUNDER, 1.0f, entity.getSoundPitch());
    }

    @Override
    protected void keepRunning(ServerWorld world, KingBooEntity entity, long l) {
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        optionalMemory.ifPresent(target -> entity.getLookControl().lookAt(target.getPos()));
        if (entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            return;
        }
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - SOUND_DELAY);
        List<Entity> list = world.getOtherEntities(entity, Box.from(Vec3d.ofCenter(this.blockPos)).expand(0.2), EntityPredicates.VALID_LIVING_ENTITY);

        for (Entity target : list) {
            target.damage(DamageSource.magic(entity, entity), 8);
        }

        for (int i = 0; i < 50; ++i) {
            world.spawnParticles(ParticleTypes.LARGE_SMOKE, blockPos.getX(), blockPos.getY() + i, blockPos.getZ(), 2, 0, 0.0D, 0, 0.0D);
        }

        world.playSound(null, this.blockPos, Sounds.ENTITY_KING_BOO_THUNDER_STRIKE, SoundCategory.HOSTILE, 3.0F, 1.0F);
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, KingBooEntity entity, long l) {
        if (serverWorld.random.nextInt(3) == 0)
            this.run(serverWorld, entity, l);
        else {
            ModBossEntity.cooldown(entity, 60);

            Random random = entity.getRandom();
            if (random.nextInt(2) == 0)
                entity.setNextAttack(2);
            else
                entity.setNextAttack(random.nextInt(entity.getMaxAttacks()));
        }

    }
}

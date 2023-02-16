package com.dayofpi.super_block_world.entity.entities.tasks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.entity.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.ModTags;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Optional;

public class SpinTask extends Task<PeteyPiranhaEntity> {
    private static final int SOUND_DELAY = MathHelper.ceil(20.0);
    private static final int RUN_TIME = MathHelper.ceil(50.0f);

    public SpinTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, PeteyPiranhaEntity entity) {
        if (entity.getNextAttack() != 1)
            return false;
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        return optionalMemory.filter(livingEntity -> entity.isInRange(livingEntity, 10.0, 10.0)).isPresent();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        return true;
    }

    @Override
    protected void run(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);
        entity.setSpinning(true);
        double d = 0.42f + entity.getJumpBoostVelocityModifier();
        Vec3d vec3d = entity.getVelocity();
        entity.setVelocity(vec3d.x, d, vec3d.z);
        entity.playSound(Sounds.ENTITY_PETEY_PIRANHA_SPIN, 1.0F, 1.0F);
    }

    @Override
    protected void keepRunning(ServerWorld world, PeteyPiranhaEntity entity, long l) {
        entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> entity.getLookControl().lookAt(target.getPos()));
        if (entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || entity.getBrain().hasMemoryModule(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
            return;
        }
        List<Entity> list = world.getOtherEntities(entity, Box.from(Vec3d.ofCenter(entity.getBlockPos())).expand(1, 0, 1), EntityPredicates.VALID_LIVING_ENTITY);

        for (Entity target : list) {
            if (target instanceof LivingEntity) {
                target.damage(DamageSource.mob(entity), 5);
                ((LivingEntity) target).takeKnockback(0.5F, entity.getX() - target.getX(), entity.getZ() - target.getZ());
            }
        }
        for (BlockPos blockPos : BlockPos.iterateOutwards(entity.getBlockPos().up(), 2, 3, 2)) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isIn(ModTags.GOOP)) {
                world.breakBlock(blockPos, false);
            }
        }
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, PeteyPiranhaEntity entity, long l) {
        Random random = entity.getRandom();
        ModBossEntity.cooldown(entity, 50);
        entity.setNextAttack(random.nextInt(entity.getMaxAttacks()));
        entity.setSpinning(false);
    }
}

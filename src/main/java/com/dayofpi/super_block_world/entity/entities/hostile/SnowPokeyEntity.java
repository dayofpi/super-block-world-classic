package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SnowPokeyEntity extends AbstractPokey {
    public SnowPokeyEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    public static boolean canSnowPokeySpawn(EntityType<? extends SnowPokeyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean noLight = isThereNoLight(world, pos);
        return noLight && world.getBlockState(pos.down()).isOf(ModBlocks.SNOWY_SHERBET_SOIL);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
    }
}

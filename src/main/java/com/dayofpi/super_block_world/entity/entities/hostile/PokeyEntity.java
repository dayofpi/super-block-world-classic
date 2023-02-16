package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PokeyEntity extends AbstractPokey {
    public PokeyEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    public static boolean canPokeySpawn(EntityType<? extends PokeyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean noLight = isThereNoLight(world, pos);
        return noLight && world.getBlockState(pos.down()).isOf(ModBlocks.GRITZY_SAND);
    }
}

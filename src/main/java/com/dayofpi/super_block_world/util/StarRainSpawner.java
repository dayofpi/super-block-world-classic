package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.WorldInit;
import com.dayofpi.super_block_world.common.entities.misc.StarBitEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.Spawner;

public class StarRainSpawner implements Spawner {
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (world != WorldInit.DIMENSION) {
            return 0;
        }
        Random random = world.random;
        --this.cooldown;
        if (this.cooldown > 0) {
            return 0;
        }
        this.cooldown += (60 + random.nextInt(80)) * 20;
        if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            return 0;
        }
        int i = 0;
        for (ServerPlayerEntity playerEntity : world.getPlayers()) {
            BlockPos blockPos2;
            LocalDifficulty localDifficulty;
            if (playerEntity.isSpectator()) continue;
            BlockPos blockPos = playerEntity.getBlockPos();
            if (world.getDimension().hasSkyLight() && (blockPos.getY() < world.getSeaLevel() || !world.isSkyVisible(blockPos)) || !(localDifficulty = world.getLocalDifficulty(blockPos)).isHarderThan(random.nextFloat() * 3.0f))
                continue;
            if (!SpawnHelper.isClearForSpawn(world, blockPos2 = blockPos.up(20 + random.nextInt(20)).east(-10 + random.nextInt(50)).south(-10 + random.nextInt(50)), world.getBlockState(blockPos2), world.getFluidState(blockPos2), ModEntities.STAR_BIT))
                continue;
            int l = 1 + random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 2);
            for (int m = 0; m < l; ++m) {
                StarBitEntity starBitEntity = ModEntities.STAR_BIT.create(world);
                if (starBitEntity != null) {
                    starBitEntity.refreshPositionAndAngles(blockPos2, 0.0f, 0.0f);
                    starBitEntity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 10.0F, random.nextFloat() + random.nextFloat() - 0.2F);
                    world.spawnEntityAndPassengers(starBitEntity);
                }
            }
            i += l;
        }
        return i;
    }
}

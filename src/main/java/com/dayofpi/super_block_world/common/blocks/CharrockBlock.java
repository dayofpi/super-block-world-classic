package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.entities.VolcanicDebrisEntity;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.world.MushroomKingdom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;
@SuppressWarnings("deprecation")
public class CharrockBlock extends Block {
    public CharrockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int multiplier = random.nextBoolean() ? 1 : -1;
        BlockPos blockPos = pos.add(random.nextInt(5) * multiplier, random.nextInt(15) + 5, random.nextInt(5) * multiplier);
        List<VolcanicDebrisEntity> debrisEntities = world.getEntitiesByClass(VolcanicDebrisEntity.class, Box.from(Vec3d.of(pos)).expand(10), EntityPredicates.VALID_LIVING_ENTITY);
        PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15D, EntityPredicates.VALID_LIVING_ENTITY);

        if (closestPlayer != null && debrisEntities.size() < 3 && world.getRegistryKey().equals(MushroomKingdom.WORLD_KEY) && random.nextInt(16) == 0 && blockPos.getY() < 0 && world.isSpaceEmpty(Box.from(Vec3d.ofCenter(blockPos)).expand(0, 6, 0).offset(0, -5, 0))) {
            VolcanicDebrisEntity volcanicDebris = EntityInit.VOLCANIC_DEBRIS.create(world);
            if (volcanicDebris != null) {
                volcanicDebris.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                volcanicDebris.initialize(world,world.getLocalDifficulty(blockPos), SpawnReason.TRIGGERED, null, null);
                world.spawnEntity(volcanicDebris);
            }
        }
    }
}

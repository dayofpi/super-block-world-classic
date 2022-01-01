package com.dayofpi.super_block_world.main.common.block.hazard;

import com.dayofpi.super_block_world.main.common.entity.mob.FuzzyEntity;
import com.dayofpi.super_block_world.main.registry.misc.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class FuzzbushBlock extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public FuzzbushBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0)
            world.addParticle(ParticleTypes.SQUID_INK, false, pos.getX() + random.nextFloat(), pos.getY() + 1.0D, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(3) == 0) {
            List<FuzzyEntity> list = world.getEntitiesByClass(FuzzyEntity.class, Box.from(Vec3d.of(pos)).expand(16), EntityPredicates.VALID_LIVING_ENTITY);
            if (list.size() < 10) {
                FuzzyEntity fuzzyEntity = EntityRegistry.FUZZY.create(world);
                if (fuzzyEntity != null) {
                    fuzzyEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                    world.spawnEntity(fuzzyEntity);
                }
            }
        }

    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}

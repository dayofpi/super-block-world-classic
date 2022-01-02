package com.dayofpi.super_block_world.main.common.block.building;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BeanstalkBlock extends PillarBlock {
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public BeanstalkBlock(Settings settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (entity instanceof LivingEntity livingEntity)
            if (livingEntity.horizontalCollision) {
                livingEntity.setVelocity(vec3d.x, 0.2, vec3d.z);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }
}

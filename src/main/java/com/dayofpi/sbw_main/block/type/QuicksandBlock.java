package com.dayofpi.sbw_main.block.type;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class QuicksandBlock extends Block {
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 1.0, 0.0, 16.0, 16.0, 16.0);
    public QuicksandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getBlockX() == pos.getX() && entity.getBlockZ() == pos.getZ() && entity.getY() < pos.getY() - 0.55  ) {
            if (entity instanceof LivingEntity) {
                entity.damage(DamageSource.IN_WALL, 2.0F);
            }
        }
        if (entity.getY() + 0.55 > pos.getY())
            entity.slowMovement(state, new Vec3d(0.25, 0.6, 0.25));
    }
}

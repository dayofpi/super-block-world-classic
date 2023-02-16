package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.SandBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class QuicksandBlock extends SandBlock {
    public QuicksandBlock(int color, Settings settings) {
        super(color, settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext && ((EntityShapeContext) context).getEntity() instanceof FallingBlockEntity)
            return VoxelShapes.fullCube();
        return VoxelShapes.empty();
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (shouldDamage(state, pos, entity)) {
            if (entity instanceof LivingEntity) {
                entity.damage(DamageSource.IN_WALL, 2.0F);
            }
        }
        if (entity.getY() + 0.55 > pos.getY())
            entity.slowMovement(state, new Vec3d(0.25, 0.6, 0.25));
    }

    private static boolean shouldDamage(BlockState state, BlockPos pos, Entity entity) {
        if (state.isOf(ModBlocks.BLACK_PAINT_BLOCK))
            return true;
        return entity.getBlockX() == pos.getX() && entity.getBlockZ() == pos.getZ() && entity.getY() < pos.getY() - 0.55;
    }
}

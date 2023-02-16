package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.entity.entities.passive.YoshiEntity;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class MuncherBlock extends PlantBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public MuncherBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof YoshiEntity))
            entity.damage(ModDamageSource.MUNCHER, 1.0F);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext && ((EntityShapeContext) context).getEntity() instanceof YoshiEntity)
            return SHAPE;
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos blockPos) {
        return floor.isSideSolidFullSquare(world, blockPos, Direction.UP);
    }
}

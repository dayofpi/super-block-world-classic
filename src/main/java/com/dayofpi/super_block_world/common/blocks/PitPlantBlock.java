package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class PitPlantBlock extends PlantBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public PitPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof YoshiEntity))
            entity.damage(DamageSource.CACTUS, 1.0F);
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
        return floor.isOf(ModBlocks.QUICKSAND) || floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND);
    }
}

package com.dayofpi.super_block_world.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class CloudSlabBlock extends SlabBlock {
    public CloudSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Entity entity;
        if (context instanceof EntityShapeContext && (entity = ((EntityShapeContext) context).getEntity()) != null) {
            if (CloudBlock.canWalkOnCloud(entity) && (context.isAbove(VoxelShapes.fullCube(), pos, false) || context.isAbove(TOP_SHAPE, pos, false) || context.isAbove(BOTTOM_SHAPE, pos, false))) {
                return super.getCollisionShape(state, world, pos, context);
            }
        }
        return VoxelShapes.empty();
    }
}

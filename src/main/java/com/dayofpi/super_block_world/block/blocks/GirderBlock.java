package com.dayofpi.super_block_world.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class GirderBlock extends SlabBlock {
    public GirderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext) {
            if (context.isDescending())
                return VoxelShapes.empty();
            if (context.isAbove(BOTTOM_SHAPE, pos, false) && !context.isAbove(TOP_SHAPE, pos, false)) {
                return BOTTOM_SHAPE;
            } else if (context.isAbove(VoxelShapes.fullCube(), pos, false) || context.isAbove(TOP_SHAPE, pos, false) || context.isAbove(BOTTOM_SHAPE, pos, false)) {
                return super.getCollisionShape(state, world, pos, context);
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }
}

package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class ModStairsBlock extends StairsBlock {
    private final BlockState baseBlockState;
    public ModStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.baseBlockState = baseBlockState;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.baseBlockState.isOf(BlockInit.CLOUD_BLOCK)) {
            Entity entity;
            if (context instanceof EntityShapeContext && (entity = ((EntityShapeContext)context).getEntity()) != null) {
                if (CloudBlock.canWalkOnCloud(entity) && (context.isAbove(VoxelShapes.fullCube(), pos, false)|| context.isAbove(TOP_SHAPE, pos, false) || context.isAbove(BOTTOM_SHAPE, pos, false))) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
            return VoxelShapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }
}

package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.registry.ModBlocks;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ModStairsBlock extends StairsBlock {
    private final BlockState baseState;

    public ModStairsBlock(Block baseBlock) {
        super(baseBlock.getDefaultState(), ((AbstractBlockAccessor) baseBlock).getSettings());
        this.baseState = baseBlock.getDefaultState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.baseState.isOf(ModBlocks.CLOUD_BLOCK)) {
            Entity entity;
            if (context instanceof EntityShapeContext && (entity = ((EntityShapeContext) context).getEntity()) != null) {
                if (CloudBlock.canWalkOnCloud(entity) && (context.isAbove(VoxelShapes.fullCube(), pos, false) || context.isAbove(TOP_SHAPE, pos, false) || context.isAbove(BOTTOM_SHAPE, pos, false))) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
            return VoxelShapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }
}

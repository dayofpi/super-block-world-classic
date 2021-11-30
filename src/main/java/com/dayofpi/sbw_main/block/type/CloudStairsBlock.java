package com.dayofpi.sbw_main.block.type;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class CloudStairsBlock extends StairsBlock {
    public CloudStairsBlock(Settings settings) {
        super(ModBlocks.CLOUD_BLOCK.getDefaultState(), settings);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext) {
            if (context.isAbove(this.getOutlineShape(state, world, pos, context), pos, false)) {
                return super.getCollisionShape(state, world, pos, context);
            }
        }
        return VoxelShapes.empty();
    }
}

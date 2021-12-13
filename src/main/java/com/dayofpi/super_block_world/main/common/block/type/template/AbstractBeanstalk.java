package com.dayofpi.super_block_world.main.common.block.type.template;

import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public abstract class AbstractBeanstalk extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    protected AbstractBeanstalk(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND) || floor.isOf(PlantBlocks.BEANSTALK_PLANT) || floor.isOf(PlantBlocks.BEANSTALK);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }
}

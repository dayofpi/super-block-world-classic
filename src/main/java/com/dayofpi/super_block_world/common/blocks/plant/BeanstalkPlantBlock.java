package com.dayofpi.super_block_world.common.blocks.plant;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class BeanstalkPlantBlock extends AbstractBeanstalk {
    public BeanstalkPlantBlock(Settings settings) {
        super(settings);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(PlantBlocks.BEANSTALK);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            if (!neighborState.isOf(PlantBlocks.BUDDING_BEANSTALK) && !neighborState.isOf(PlantBlocks.BEANSTALK) && !neighborState.isOf(PlantBlocks.BEANSTALK_PLANT))
                return PlantBlocks.BEANSTALK.getDefaultState();
        } return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
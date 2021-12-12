package com.dayofpi.sbw_main.common.block.type.beanstalk;

import com.dayofpi.sbw_main.registry.block.PlantBlocks;
import com.dayofpi.sbw_main.common.block.type.template.AbstractBeanstalk;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class BeanstalkStemBlock extends AbstractBeanstalk implements Fertilizable {

    public BeanstalkStemBlock(Settings settings) {
        super(settings);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            if (neighborState.isOf(PlantBlocks.BEANSTALK_PLANT) || neighborState.isOf(PlantBlocks.BEANSTALK) || neighborState.isOf(PlantBlocks.BUDDING_BEANSTALK))
                return PlantBlocks.BEANSTALK_PLANT.getDefaultState();
        } return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (world.getBlockState(pos.up()).isAir()) {
            world.setBlockState(pos, PlantBlocks.BEANSTALK_PLANT.getDefaultState());
            world.setBlockState(pos.up(), state);
            if (random.nextInt(6) == 3) {
                grow(world, random, pos.up(), state);
            }
        }
    }
}
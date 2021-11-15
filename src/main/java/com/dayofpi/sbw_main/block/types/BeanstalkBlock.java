package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.types.template.ModPlantStemBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineLogic;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class BeanstalkBlock extends ModPlantStemBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);

    public BeanstalkBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.1D);
    }

    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    protected Block getPlant() {
        return ModBlocks.BEANSTALK_PLANT;
    }

    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.method_39279(pos, this, 1);
        }

        if (direction != this.growthDirection || !neighborState.isOf(this) && !neighborState.isOf(this.getPlant())) {
            if (this.tickWater) {
                world.method_39281(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            return this.copyState(state, this.getPlant().getDefaultState().with(BeanstalkPlantBlock.BUDDING, false));
        }
    }
}
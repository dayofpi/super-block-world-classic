package com.dayofpi.sbw_main.block.type.beanstalk;

import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.block.type.template.ModPlantPartBlock;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Optional;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BeanstalkBodyBlock extends ModPlantPartBlock implements Fertilizable {
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public BeanstalkBodyBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
    }

    protected BlockState copyState(BlockState from, BlockState to) {
        return to;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        AbstractPlantStemBlock abstractPlantStemBlock = this.getStem(state);
        if (direction == this.growthDirection && !neighborState.isOf(this) && !neighborState.isOf(abstractPlantStemBlock)) {
            return this.copyState(state, abstractPlantStemBlock.getRandomGrowthState(world));
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.getStem(state));
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        Optional<BlockPos> optional = this.getStemHeadPos(world, pos, state.getBlock());
        return optional.isPresent() && VineLogic.isValidForWeepingStem(state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Optional<BlockPos> optional = this.getStemHeadPos(world, pos, state.getBlock());
        if (optional.isPresent()) {
            BlockState blockState = world.getBlockState(optional.get());
            ((AbstractPlantStemBlock)blockState.getBlock()).grow(world, random, optional.get(), blockState);
        }

    }

    private Optional<BlockPos> getStemHeadPos(BlockView world, BlockPos pos, Block block) {
        return BlockLocating.findColumnEnd(world, pos, block, this.growthDirection, this.getStem(world.getBlockState(pos)));
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        boolean bl = super.canReplace(state, context);
        return (!bl || !context.getStack().isOf(this.getStem(state).asItem())) && bl;
    }

    protected Block getPlant() {
        return this;
    }

    protected AbstractPlantStemBlock getStem(BlockState state) {
        return (AbstractPlantStemBlock) PlantBlocks.BEANSTALK;
    }
}
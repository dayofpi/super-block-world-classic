package com.dayofpi.sbw_main.block.types.template;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public abstract class ModPlantStemBlock extends AbstractPlantStemBlock implements Fertilizable {
	public static final IntProperty AGE;

	protected ModPlantStemBlock(Settings settings, Direction direction, VoxelShape voxelShape, boolean bl, double d) {
		super(settings, direction, voxelShape, bl, d);
	}

	protected BlockState copyState(BlockState from, BlockState to) {
		return to;
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}

		if (direction != this.growthDirection || !neighborState.isOf(this) && !neighborState.isOf(this.getPlant())) {
			return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		} else {
			return this.copyState(state, this.getPlant().getDefaultState());
		}
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return this.chooseStemState(world.getBlockState(pos.offset(this.growthDirection)));
	}

	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockPos blockPos = pos.offset(this.growthDirection);
		int i = Math.min(state.get(AGE) + 1, 25);
		int j = this.getGrowthLength(random);

		for(int k = 0; k < j && this.chooseStemState(world.getBlockState(blockPos)); ++k) {
			world.setBlockState(blockPos, state.with(AGE, i));
			blockPos = blockPos.offset(this.growthDirection);
			i = Math.min(i + 1, 25);
		}

	}

	protected abstract int getGrowthLength(Random random);

	protected abstract boolean chooseStemState(BlockState state);

	protected ModPlantStemBlock getStem() {
		return this;
	}

	static {
		AGE = Properties.AGE_25;
	}
}

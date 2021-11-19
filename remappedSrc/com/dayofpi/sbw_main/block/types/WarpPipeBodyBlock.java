package com.dayofpi.sbw_main.block.types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class WarpPipeBodyBlock extends AbstractWarpPipeBlock{
    public static final BooleanProperty DOWN;
    protected static final BooleanProperty NORTH;
    protected static final BooleanProperty EAST;
    protected static final BooleanProperty SOUTH;
    protected static final BooleanProperty WEST;
    protected static final BooleanProperty UP;

    static {
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        UP = ConnectingBlock.UP;
        DOWN = ConnectingBlock.DOWN;
    }

    public WarpPipeBodyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) {
            boolean isPipe = neighborState.getBlock() instanceof AbstractWarpPipeBlock;
            boolean northSouth = isPipe && (neighborState.get(FACING) == Direction.NORTH || neighborState.get(FACING) == Direction.SOUTH);
            boolean eastWest = isPipe && (neighborState.get(FACING) == Direction.EAST || neighborState.get(FACING) == Direction.WEST);
            boolean upDown = neighborState.isOf(this) && (neighborState.get(FACING) == Direction.UP || neighborState.get(FACING) == Direction.DOWN);
            if (northSouth && direction == Direction.NORTH) {
                return state.with(NORTH, true);
            } else if (!northSouth && direction == Direction.NORTH) {
                return state.with(NORTH, false);
            }
            if (northSouth && direction == Direction.SOUTH) {
                return state.with(SOUTH, true);
            } else if (!northSouth && direction == Direction.SOUTH) {
                return state.with(SOUTH, false);
            }
            if (eastWest && direction == Direction.EAST) {
                return state.with(EAST, true);
            } else if (!eastWest && direction == Direction.EAST) {
                return state.with(EAST, false);
            }
            if (eastWest && direction == Direction.WEST) {
                return state.with(WEST, true);
            } else if (!eastWest && direction == Direction.WEST) {
                return state.with(WEST, false);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }
}

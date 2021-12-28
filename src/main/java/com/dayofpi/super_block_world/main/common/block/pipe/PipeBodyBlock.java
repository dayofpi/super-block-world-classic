package com.dayofpi.super_block_world.main.common.block.pipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class PipeBodyBlock extends AbstractPipeBlock {
    public static final BooleanProperty DOWN;
    protected static final BooleanProperty NORTH;
    protected static final BooleanProperty EAST;
    protected static final BooleanProperty SOUTH;
    protected static final BooleanProperty WEST;
    protected static final BooleanProperty UP;

    private static final VoxelShape Y_SHAPE = Block.createCuboidShape(1.0D,0.0D,1.0D, 15.0D, 16.0D,15.0D);
    private static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
    private static final VoxelShape Z_SHAPE = Block.createCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 16.0D);

    static {
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        UP = ConnectingBlock.UP;
        DOWN = ConnectingBlock.DOWN;
    }

    public PipeBodyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(WATERLOGGED, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) {
            return Y_SHAPE;
        } else if (state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST) {
            return X_SHAPE;
        } else if (state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Z_SHAPE;
        } else return super.getOutlineShape(state, world, pos, context);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbor, WorldAccess world, BlockPos pos, BlockPos neighborPos) {


        if (state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) {
            return this.upDownPredicates(state, direction, neighbor);
        }
        else if (state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return this.northSouthPredicates(state, direction, neighbor);
        }
        else if (state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST) {
            return this.eastWestPredicates(state, direction, neighbor);
        }
        return super.getStateForNeighborUpdate(state, direction, neighbor, world, pos, neighborPos);
    }

    private BlockState northSouthPredicates(BlockState state, Direction direction, BlockState neighbor) {
        boolean isPipe = neighbor.getBlock() instanceof AbstractPipeBlock;
        boolean upDown = neighbor.isOf(this) && (neighbor.get(FACING) == Direction.UP || neighbor.get(FACING) == Direction.DOWN);
        boolean eastWest = isPipe && (neighbor.get(FACING) == Direction.EAST || neighbor.get(FACING) == Direction.WEST);

        if (upDown && direction == Direction.UP) {
            return state.with(UP, true);
        } else if (!upDown && direction == Direction.UP) {
            return state.with(UP, false);
        }
        if (upDown && direction == Direction.DOWN) {
            return state.with(DOWN, true);
        } else if (!upDown && direction == Direction.DOWN) {
            return state.with(DOWN, false);
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
        } else return state;
    }

    private BlockState upDownPredicates(BlockState state, Direction direction, BlockState neighbor) {
        boolean isPipe = neighbor.getBlock() instanceof AbstractPipeBlock;
        boolean northSouth = isPipe && (neighbor.get(FACING) == Direction.NORTH || neighbor.get(FACING) == Direction.SOUTH);
        boolean eastWest = isPipe && (neighbor.get(FACING) == Direction.EAST || neighbor.get(FACING) == Direction.WEST);

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
        } else return state;
    }

    private BlockState eastWestPredicates(BlockState state, Direction direction, BlockState neighbor) {
        boolean isPipe = neighbor.getBlock() instanceof AbstractPipeBlock;
        boolean northSouth = isPipe && (neighbor.get(FACING) == Direction.NORTH || neighbor.get(FACING) == Direction.SOUTH);
        boolean upDown = neighbor.isOf(this) && (neighbor.get(FACING) == Direction.UP || neighbor.get(FACING) == Direction.DOWN);

        if (upDown && direction == Direction.UP) {
            return state.with(UP, true);
        } else if (!upDown && direction == Direction.UP) {
            return state.with(UP, false);
        }
        if (upDown && direction == Direction.DOWN) {
            return state.with(DOWN, true);
        } else if (!upDown && direction == Direction.DOWN) {
            return state.with(DOWN, false);
        }

        if (northSouth && direction == Direction.NORTH) {
            return state.with(NORTH, true);
        } else if (!northSouth && direction == Direction.NORTH) {
            return state.with(NORTH, false);
        }
        if (northSouth && direction == Direction.SOUTH) {
            return state.with(SOUTH, true);
        } else if (!northSouth && direction == Direction.SOUTH) {
            return state.with(SOUTH, false);
        } else return state;

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }
}

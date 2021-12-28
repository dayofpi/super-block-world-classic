package com.dayofpi.super_block_world.main.common.block.pipe;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
@SuppressWarnings("deprecation")
public abstract class AbstractPipeBlock extends FacingBlock implements Waterloggable {
    protected static final BooleanProperty WATERLOGGED;
    private static final VoxelShape UP_SHAPE = VoxelShapes.union(Block.createCuboidShape(1.0D,0.0D,1.0D, 15.0D, 13.0D,15.0D), Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 3.0D), Block.createCuboidShape(0.0D, 13.0D, 13.0D, 16.0D, 16.0D, 16.0D), Block.createCuboidShape(13.0D, 13.0D, 3.0D, 16.0D, 16.0D, 13.0D), Block.createCuboidShape(0.0D, 13.0D, 3.0D, 3.0D, 16.0D, 13.0D));
    private static final VoxelShape DOWN_SHAPE = VoxelShapes.union(Block.createCuboidShape(1.0D,3.0D,1.0D, 15.0D, 16.0D,15.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D), Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D), Block.createCuboidShape(13.0D, 0.0D, 3.0D, 16.0D, 3.0D, 13.0D), Block.createCuboidShape(0.0D, 0.0D, 3.0D, 3.0D, 3.0D, 13.0D));
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(1.0D, 1.0D, 3.0D, 15.0D, 15.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D), Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 3.0D), Block.createCuboidShape(0.0D, 3.0D, 0.0D, 3.0D, 13.0D, 3.0D), Block.createCuboidShape(13.0D, 3.0D, 0.0D, 16.0D, 13.0D, 3.0D));
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 13.0D), Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D), Block.createCuboidShape(0.0D, 13.0D, 13.0D, 16.0D, 16.0D, 16.0D), Block.createCuboidShape(0.0D, 3.0D, 13.0D, 3.0D, 13.0D, 16.0D), Block.createCuboidShape(13.0D, 3.0D, 13.0D, 16.0D, 13.0D, 16.0D));

    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 1.0D, 1.0D, 13.0D, 15.0D, 15.0D),
            Block.createCuboidShape(13.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.createCuboidShape(13.0D, 3.0D, 13.0D, 16.0D, 13.0D, 16.0D),
            Block.createCuboidShape(13.0D, 3.0D, 0.0D, 16.0D, 13.0D, 3.0D));

    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D),
            Block.createCuboidShape(0.0D, 13.0D, 0.0D, 3.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 16.0D),
            Block.createCuboidShape(0.0D, 3.0D, 13.0D, 3.0D, 13.0D, 16.0D),
            Block.createCuboidShape(0.0D, 3.0D, 0.0D, 3.0D, 13.0D, 3.0D));

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }

    protected AbstractPipeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(FACING) == Direction.UP) {
            return UP_SHAPE;
        } else if (state.get(FACING) == Direction.DOWN) {
            return DOWN_SHAPE;
        } else if (state.get(FACING) == Direction.NORTH) {
            return NORTH_SHAPE;
        } else if (state.get(FACING) == Direction.SOUTH) {
            return SOUTH_SHAPE;
        } else if (state.get(FACING) == Direction.EAST) {
            return EAST_SHAPE;
        } else
            return WEST_SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean isInFluid = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, isInFluid);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }
}

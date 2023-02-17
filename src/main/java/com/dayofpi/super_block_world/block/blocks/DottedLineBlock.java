package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class DottedLineBlock extends Block {
    private static final IntProperty DISTANCE = Properties.DISTANCE_1_7;

    public DottedLineBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 7));
    }

    private static BlockState updateDistanceFromSwitches(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            i = Math.min(i, DottedLineBlock.getDistanceFromSwitch(world.getBlockState(mutable)) + 1);
            if (i == 1) break;
        }
        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromSwitch(BlockState state) {
        if (state.isOf(ModBlocks.ON_OFF_SWITCH) && state.get(Properties.POWERED)) {
            return 0;
        }
        if (state.getBlock() instanceof DottedLineBlock) {
            return state.get(DISTANCE);
        }
        return 7;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (isOff(state)) return VoxelShapes.empty();
        return VoxelShapes.fullCube();
    }

    private boolean isOff(BlockState state) {
        Block block = state.getBlock();
        if (block == ModBlocks.DOTTED_LINE_BLOCK) return state.get(DISTANCE) == 7;
        return state.get(DISTANCE) != 7;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return isOff(state);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this) && isOff(state) && isOff(stateFrom)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, DottedLineBlock.updateDistanceFromSwitches(state, world, pos), Block.NOTIFY_ALL);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i = DottedLineBlock.getDistanceFromSwitch(neighborState) + 1;
        if (i != 1 || state.get(DISTANCE) != i) {
            world.scheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return DottedLineBlock.updateDistanceFromSwitches(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }
}

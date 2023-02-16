package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class SmileyStemBlock extends PlantBlock implements Fertilizable {
    private static final BooleanProperty IS_BUD;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    static {
        IS_BUD = BooleanProperty.of("is_bud");
    }

    public SmileyStemBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(this) || floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.FARMLAND);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, blockPos))
            return Blocks.AIR.getDefaultState();
        if (!world.getBlockState(blockPos.up()).isOf(this) && !world.getBlockState(blockPos.up()).isOf(ModBlocks.SMILEY_SUNFLOWER))
            return state.with(IS_BUD, true);
        return state.with(IS_BUD, false);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos blockPos, BlockState state, boolean isClient) {
        BlockState blockState = world.getBlockState(blockPos.up());
        return blockState.isAir() || blockState.isOf(this) && isFertilizable(world, blockPos.up(), blockState, isClient);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos blockPos, BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        BlockPos ceiling = blockPos.up();
        if (state.get(IS_BUD) && random.nextInt(6) == 0) {
            world.setBlockState(blockPos, ModBlocks.SMILEY_SUNFLOWER.getDefaultState());
        } else if (random.nextInt(5) == 0 && (world.getBlockState(ceiling).isAir() || world.getBlockState(ceiling).isOf(this))) {
            this.grow(world, random, blockPos, state);
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos blockPos, BlockState state) {
        if (state.get(IS_BUD) && random.nextInt(5) == 0) {
            world.setBlockState(blockPos, ModBlocks.SMILEY_SUNFLOWER.getDefaultState());
            return;
        }

        BlockPos ceiling = blockPos.up();
        if (world.getBlockState(ceiling).isAir()) {
            world.setBlockState(ceiling, state);
        } else if (world.getBlockState(ceiling).isOf(this))
            grow(world, random, ceiling, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_BUD);
    }
}

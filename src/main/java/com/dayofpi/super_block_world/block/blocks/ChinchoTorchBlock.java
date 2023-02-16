package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.block_entities.ChinchoTorchBE;
import com.dayofpi.super_block_world.block.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ChinchoTorchBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
    private static final BooleanProperty LIT;
    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape BONE_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 10.0, 9.0);
    private static final VoxelShape ORB_SHAPE = Block.createCuboidShape(6.0, 11.0, 6.0, 10.0, 15.0, 10.0);
    private static final VoxelShape SHAPE = VoxelShapes.union(BONE_SHAPE, ORB_SHAPE);

    static {
        LIT = Properties.LIT;
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public ChinchoTorchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos floor = pos.down();
        return Block.sideCoversSmallSquare(world, floor, Direction.UP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(LIT, !bl);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChinchoTorchBE(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.CHINCHO_TORCH, ChinchoTorchBE::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}

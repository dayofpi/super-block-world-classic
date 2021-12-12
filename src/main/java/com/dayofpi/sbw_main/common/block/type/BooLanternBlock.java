package com.dayofpi.sbw_main.common.block.type;

import com.dayofpi.sbw_main.common.block_entity.type.BooLanternBE;
import com.dayofpi.sbw_main.registry.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
@SuppressWarnings("deprecation")
public class BooLanternBlock extends BlockWithEntity {
    public static final BooleanProperty LIT;
    public static final BooleanProperty HANGING;
    protected static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    static {
        LIT = Properties.LIT;
        HANGING = Properties.HANGING;
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public BooLanternBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, true).with(HANGING, false).with(WATERLOGGED, false));
    }

    protected static Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BooLanternBE(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LIT) ? 15 : 0;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BOO_LANTERN, BooLanternBE::tick);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction[] var3 = ctx.getPlacementDirections();

        for (Direction direction : var3) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, HANGING, WATERLOGGED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = (double) pos.getX() + random.nextFloat();
            double e = (double) pos.getY() + random.nextFloat();
            double f = (double) pos.getZ() + random.nextFloat();
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }
}

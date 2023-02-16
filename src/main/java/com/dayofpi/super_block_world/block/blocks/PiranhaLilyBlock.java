package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.ModTags;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class PiranhaLilyBlock extends PlantBlock {
    private static final VoxelShape LAND_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    private static final VoxelShape FLOATING_SHAPE = VoxelShapes.union(Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D), Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0));
    private static final BooleanProperty FLOATING;

    static {
        FLOATING = BooleanProperty.of("floating");
    }

    public PiranhaLilyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FLOATING, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FLOATING, ctx.getWorld().getFluidState(ctx.getBlockPos().down()).isIn(FluidTags.WATER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOATING);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());

        return super.canPlantOnTop(floor, world, pos) || floor.isIn(ModTags.WARP_PIPES) || (fluidState.getFluid() == Fluids.WATER || floor.getMaterial() == Material.ICE) && fluidState2.getFluid() == Fluids.EMPTY;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape PAD_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

        if (context instanceof EntityShapeContext) {
            if (context.isAbove(PAD_SHAPE, pos, false) && state.get(FLOATING)) {
                return PAD_SHAPE;
            }
        } return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FLOATING) ? FLOATING_SHAPE : LAND_SHAPE;
    }
}

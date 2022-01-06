package com.dayofpi.super_block_world.main.common.block.plant;

import com.dayofpi.super_block_world.main.registry.main.TagInit;
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

    protected static final VoxelShape LAND_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    protected static final VoxelShape FLOATING_SHAPE = VoxelShapes.union(Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D), Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0));
    public static final BooleanProperty FLOATING;

    static {
        FLOATING = BooleanProperty.of("floating");
    }

    public PiranhaLilyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FLOATING, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FLOATING, ctx.getWorld().getFluidState(ctx.getBlockPos().down()).isIn(FluidTags.WATER));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOATING);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());

        return super.canPlantOnTop(floor, world, pos) || floor.isIn(TagInit.WARP_PIPES) || (fluidState.getFluid() == Fluids.WATER || floor.getMaterial() == Material.ICE) && fluidState2.getFluid() == Fluids.EMPTY;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape PAD_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

        if (context instanceof EntityShapeContext) {
            if (context.isAbove(PAD_SHAPE, pos, false) && state.get(FLOATING)) {
                return PAD_SHAPE;
            }
        } return VoxelShapes.empty();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FLOATING) ? FLOATING_SHAPE : LAND_SHAPE;
    }
}

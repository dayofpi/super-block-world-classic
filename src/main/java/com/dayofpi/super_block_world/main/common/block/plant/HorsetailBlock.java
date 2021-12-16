package com.dayofpi.super_block_world.main.common.block.plant;

import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HorsetailBlock extends PlantBlock {
    public static final IntProperty PART;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    static {
        PART = IntProperty.of("part", 0, 2);
    }

    public HorsetailBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, 0));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) && world.getBlockState(blockPos.up(2)).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState floor = world.getBlockState(blockPos);
        if (state.get(PART) == 0) {
            return floor.isIn(BlockTags.DIRT);
        } else if (state.get(PART) == 1) {
            return floor.isOf(PlantBlocks.HORSETAIL) && floor.get(PART) == 0;
        } else return floor.isOf(PlantBlocks.HORSETAIL) && floor.get(PART) == 1;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState breakState = Blocks.AIR.getDefaultState();
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos) || state.get(PART) < 2 && !world.getBlockState(pos.up()).isOf(PlantBlocks.HORSETAIL)) {
            return breakState;
        } else return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (state.get(PART) == 0){
            BlockPos blockPos = pos.up();
            BlockPos blockPos2 = pos.up(2);
            world.setBlockState(blockPos, PlantBlocks.HORSETAIL.getDefaultState().with(PART, 1));
            world.setBlockState(blockPos2, PlantBlocks.HORSETAIL.getDefaultState().with(PART, 2));
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}

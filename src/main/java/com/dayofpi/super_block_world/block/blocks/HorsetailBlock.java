package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.util.HorsetailPart;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
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
    private static final EnumProperty<HorsetailPart> PART;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    static {
        PART = EnumProperty.of("part", HorsetailPart.class);
    }

    public HorsetailBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, HorsetailPart.BOTTOM));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) && world.getBlockState(blockPos.up(2)).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState floor = world.getBlockState(blockPos);
        if (state.get(PART) == HorsetailPart.BOTTOM) {
            return floor.isIn(BlockTags.DIRT);
        } else if (state.get(PART) == HorsetailPart.MIDDLE) {
            return floor.isOf(ModBlocks.HORSETAIL) && floor.get(PART) == HorsetailPart.BOTTOM;
        } else return floor.isOf(ModBlocks.HORSETAIL) && floor.get(PART) == HorsetailPart.MIDDLE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState breakState = Blocks.AIR.getDefaultState();
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos) || state.get(PART) != HorsetailPart.TOP && !world.getBlockState(pos.up()).isOf(ModBlocks.HORSETAIL)) {
            return breakState;
        } else return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (state.get(PART) == HorsetailPart.BOTTOM) {
            BlockPos middle = pos.up();
            BlockPos top = pos.up(2);
            world.setBlockState(middle, ModBlocks.HORSETAIL.getDefaultState().with(PART, HorsetailPart.MIDDLE));
            world.setBlockState(top, ModBlocks.HORSETAIL.getDefaultState().with(PART, HorsetailPart.TOP));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }
}

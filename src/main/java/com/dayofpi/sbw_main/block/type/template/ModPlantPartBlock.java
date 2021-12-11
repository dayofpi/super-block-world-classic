package com.dayofpi.sbw_main.block.type.template;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class ModPlantPartBlock extends Block {
    protected final Direction growthDirection;
    protected final boolean tickWater;
    protected final VoxelShape outlineShape;

    protected ModPlantPartBlock(Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater) {
        super(settings);
        this.growthDirection = growthDirection;
        this.outlineShape = outlineShape;
        this.tickWater = tickWater;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState selfState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(this.growthDirection));
        return !blockState.isOf(this.getStem(selfState)) && !blockState.isOf(this.getPlant()) ? this.getRandomGrowthState(ctx.getWorld()) : this.getPlant().getDefaultState();
    }

    public BlockState getRandomGrowthState(WorldAccess world) {
        return this.getDefaultState();
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(this.growthDirection.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        if (!this.canAttachTo(blockState)) {
            return false;
        } else {
            return blockState.isIn(BlockTags.LEAVES) || blockState.isOf(this.getStem(state)) || blockState.isOf(this.getPlant()) || blockState.isSideSolidFullSquare(world, blockPos, this.growthDirection);
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }

    }

    protected boolean canAttachTo(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.outlineShape;
    }

    protected abstract AbstractPlantStemBlock getStem(BlockState state);

    protected abstract Block getPlant();
}
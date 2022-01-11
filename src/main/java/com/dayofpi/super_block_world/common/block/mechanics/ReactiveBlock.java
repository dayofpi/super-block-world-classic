package com.dayofpi.super_block_world.common.block.mechanics;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class ReactiveBlock extends Block implements Waterloggable {
    protected static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public ReactiveBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(blockPos)) {
            this.activate(world.getBlockState(blockPos), world, blockPos);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.2D, 0.0D, 16.0D, 16D, 16.0D);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos blockPos, PlayerEntity player) {
        if (!world.isClient) {
            this.activate(state, world, blockPos);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        boolean jumpUnder = entity.getBlockY() < blockPos.getY() && entity.fallDistance <= 0;
        boolean isValid = entity.isPlayer() || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        if (jumpUnder && isValid) {
            this.activate(state, world, blockPos);
            if (world.getBlockState(blockPos.up()) == Blocks.SNOW.getDefaultState().with(Properties.LAYERS, 1)) {
                world.breakBlock(blockPos.up(), true);
            }
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        this.activate(state, world, hit.getBlockPos());
    }

    public void activate(BlockState state, World world, BlockPos blockPos) {}
}

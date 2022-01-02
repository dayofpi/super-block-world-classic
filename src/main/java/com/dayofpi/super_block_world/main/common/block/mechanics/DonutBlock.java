package com.dayofpi.super_block_world.main.common.block.mechanics;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("deprecation")
public class DonutBlock extends FallingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final IntProperty INSTABILITY;
    public static final BooleanProperty WILL_FALL;
    protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        INSTABILITY = IntProperty.of("instability", 0, 2);
        WILL_FALL = BooleanProperty.of("will_fall");
    }

    public DonutBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(INSTABILITY, 0).with(WILL_FALL, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(INSTABILITY).add(WILL_FALL).add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean isInFluid = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, isInFluid);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        if (isEntityAbove(blockPos, entity) && !state.get(WILL_FALL) && !world.isReceivingRedstonePower(blockPos)) {
            world.createAndScheduleBlockTick(blockPos, this, this.getFallDelay());
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        if (canFallThrough(world.getBlockState(blockPos.down())) && blockPos.getY() >= world.getBottomY()) {
            if (state.get(INSTABILITY) == 0) {
                world.setBlockState(blockPos, state.with(INSTABILITY, 1));
            } else if (state.get(INSTABILITY) == 1 && (!state.get(WILL_FALL))) {
                world.setBlockState(blockPos, state.with(INSTABILITY, 2));
                world.setBlockState(blockPos, state.with(WILL_FALL, true));
                world.playSound(null, blockPos, ModSounds.BLOCK_DONUT_BLOCK_TRIGGER, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5, 3, 0.0D, 0.0D, 0.0D, 0.0D);
                world.createAndScheduleBlockTick(blockPos, this, 12);
            } else if (state.get(WILL_FALL)) {
                FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) blockPos.getX() + 0.5D, blockPos.getY(), (double) blockPos.getZ() + 0.5D, world.getBlockState(blockPos));
                this.configureFallingBlockEntity(fallingBlockEntity);
                world.spawnEntity(fallingBlockEntity);
            }
        }
    }

    @Override
    protected int getFallDelay() {
        return 5;
    }

    @Override
    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return 13734458;
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        world.breakBlock(pos, true);
    }

    private static boolean isEntityAbove(BlockPos pos, Entity entity) {
        return entity.isOnGround() && entity.getPos().y > (double) ((float) pos.getY() + 0.6875F);
    }
}

package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FuzzballBlock extends PlantBlock {
    private static final VoxelShape SHAPE;
    private static final BooleanProperty POWERED;

    static {
        POWERED = Properties.POWERED;
        SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);
    }

    public FuzzballBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.isClient)
            return;
        BlockState floor = world.getBlockState(pos.down());
        if (floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND)) {
            world.syncWorldEvent(WorldEvents.PLANT_FERTILIZED, pos, 0);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState floor = world.getBlockState(pos.down());
        if (random.nextInt(7) == 0 && (floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND))) {
            world.setBlockState(pos, ModBlocks.FUZZBUSH.getDefaultState(), NOTIFY_LISTENERS);
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos floor = ctx.getBlockPos().down();
        BlockState neighborState = world.getBlockState(floor);
        if (world.getBlockState(floor).isOf(ModBlocks.PULL_BLOCK)) {
            if (neighborState.get(Properties.POWERED))
                return this.getDefaultState().with(POWERED, true);
            else return this.getDefaultState().with(POWERED, false);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && neighborState.isOf(ModBlocks.PULL_BLOCK)) {
            if (neighborState.get(Properties.POWERED))
                return state.with(POWERED, true);
            else return state.with(POWERED, false);
        } else return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof FuzzyEntity))
            entity.slowMovement(state, new Vec3d(0.5D, 0.5D, 0.5D));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            if (state.get(POWERED))
                world.addParticle(ParticleTypes.GLOW_SQUID_INK, false, pos.getX() + random.nextFloat(), pos.getY() + 1.0D, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
            else
                world.addParticle(ParticleTypes.SQUID_INK, false, pos.getX() + random.nextFloat(), pos.getY() + 1.0D, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}

package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.block_entities.WarpPipeBE;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WarpPipeBlock extends AbstractPipe implements BlockEntityProvider {
    private static final BooleanProperty LINKED;

    static {
        LINKED = BooleanProperty.of("linked");
    }

    public WarpPipeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(WATERLOGGED, false).with(LINKED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LINKED);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos blockPos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, blockPos, oldState, notify);
        if (state.get(FACING) == Direction.UP) {
            if (state.get(WATERLOGGED))
                world.createAndScheduleBlockTick(blockPos, this, 20);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.isOf(Blocks.WATER)) {
            world.createAndScheduleBlockTick(blockPos, this, 20);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, blockPos, neighborPos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        BubbleColumnBlock.update(world, blockPos.up(), state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LINKED)) {
            BlockPos pos1 = pos.offset(state.get(FACING));
            int i = random.nextInt(2) * 2 - 1;

            double vx = random.nextFloat() * (float)i;
            double vy = ((double)random.nextFloat() - 0.5) * 0.125;
            double vz = random.nextFloat() * (float)i;
            world.addParticle(ParticleTypes.PORTAL, pos1.getX() + random.nextDouble(), pos1.getY(), pos1.getZ() + random.nextDouble(), vx, vy, vz);
        }
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) {
            return false;
        }
        return blockEntity.onSyncedBlockEvent(type, data);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WarpPipeBE(pos, state);
    }
}

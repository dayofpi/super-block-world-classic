package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.block_entities.WarpPipeBE;
import com.dayofpi.super_block_world.criterion.ModCriteria;
import com.dayofpi.super_block_world.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WarpPipeBlock extends AbstractPipe implements BlockEntityProvider {
    public static final BooleanProperty LINKED;

    static {
        LINKED = BooleanProperty.of("linked");
    }

    public WarpPipeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(WATERLOGGED, false).with(LINKED, false));
    }

    public static boolean canEnterWarpPipe(World world, BlockPos blockPos) {
        if (!world.getBlockState(blockPos).isIn(ModTags.WARP_PIPES))
            return false;
        return world.getBlockState(blockPos).get(LINKED) && world.getBlockState(blockPos).get(FACING) == Direction.UP;
    }

    public static boolean isLinkableWarpPipe(World world, BlockPos blockPos) {
        if (!world.getBlockState(blockPos).isIn(ModTags.WARP_PIPES))
            return false;
        return !world.getBlockState(blockPos).get(LINKED) && world.getBlockState(blockPos).get(FACING) == Direction.UP;
    }

    public static void warp(PlayerEntity player, BlockPos blockPos, World world) {
        player.requestTeleport(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5);
        world.emitGameEvent(GameEvent.TELEPORT, blockPos, GameEvent.Emitter.of(player));
        world.sendEntityStatus(player, EntityStatuses.ADD_PORTAL_PARTICLES);
        world.playSound(null, blockPos, Sounds.BLOCK_WARP_PIPE_TELEPORT, SoundCategory.BLOCKS, 2.0F, 1.0F);
        if (player instanceof ServerPlayerEntity)
            ModCriteria.USE_WARP_PIPE.trigger((ServerPlayerEntity) player);
    }

    public void checkConnection(World world, BlockPos blockPos, BlockState state) {
        if (world.getBlockEntity(blockPos) instanceof WarpPipeBE warpPipeBE) {
            if (warpPipeBE.destinPos == null || !world.getBlockState(warpPipeBE.destinPos).isIn(ModTags.WARP_PIPES)) {
                world.setBlockState(blockPos, state.with(LINKED, false));
                warpPipeBE.destinPos = null;
            }
        }
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
            if (state.get(LINKED)) {
                this.checkConnection(world, blockPos, state);
            }
            if (state.get(WATERLOGGED)) {
                world.scheduleBlockTick(blockPos, this, 20);
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.isOf(Blocks.WATER)) {
            world.scheduleBlockTick(blockPos, this, 20);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, blockPos, neighborPos);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.get(FACING) == Direction.UP && state.get(LINKED)) {
            this.checkConnection(world, blockPos, state);
        }
        super.neighborUpdate(state, world, blockPos, sourceBlock, sourcePos, notify);
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

            double vx = random.nextFloat() * (float) i;
            double vy = ((double) random.nextFloat() - 0.5) * 0.125;
            double vz = random.nextFloat() * (float) i;
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

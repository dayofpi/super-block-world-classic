package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
public class SpikeTrapBlock extends Block {
    private static final BooleanProperty POWERED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    static {
        POWERED = Properties.POWERED;
    }

    public SpikeTrapBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(POWERED)) {
            if (entity instanceof LivingEntity || entity instanceof ItemEntity) {
                entity.damage(ModDamageSource.SPIKES, 4F);
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block block, BlockPos fromPos, boolean notify) {
        boolean isPowered = state.get(POWERED);
        if (isPowered != world.isReceivingRedstonePower(blockPos)) {
            if (isPowered) {
                world.createAndScheduleBlockTick(blockPos, this, 4);
            } else activate(state, world, blockPos);
        }
    }

    public void activate(BlockState state, World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound(null, pos, Sounds.BLOCK_SPIKE_TRAP_EXTEND, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
        IntStream.range(0, 4).forEach(i ->((ServerWorld)world).spawnParticles(ParticleTypes.CRIT, pos.getX() + random.nextFloat(), pos.getY() + 1, pos.getZ() + random.nextFloat(), 1, 0, 0, 0, 0));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
            world.playSound(null, pos, Sounds.BLOCK_SPIKE_TRAP_RETRACT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

    }
}

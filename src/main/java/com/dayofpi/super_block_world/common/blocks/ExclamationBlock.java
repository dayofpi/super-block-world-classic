package com.dayofpi.super_block_world.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ExclamationBlock extends ReactiveBlock {
    private static final BooleanProperty POWERED;

    static {
        POWERED = Properties.POWERED;
    }

    public ExclamationBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    @Override
    public void react(World world, BlockPos blockPos, LivingEntity entity) {
        world.createAndScheduleBlockTick(blockPos, this, 4);
        if (!world.isClient()) {
            Random random = world.getRandom();
            ((ServerWorld) world).spawnParticles(new DustParticleEffect(DustParticleEffect.RED, 1.0F), blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            ((ServerWorld) world).spawnParticles(new DustParticleEffect(DustParticleEffect.RED, 1.0F), blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        if (!state.get(POWERED) && !world.isReceivingRedstonePower(blockPos))
            world.createAndScheduleBlockTick(blockPos, this, 2);
        world.setBlockState(blockPos, state.cycle(POWERED));
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos blockPos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}

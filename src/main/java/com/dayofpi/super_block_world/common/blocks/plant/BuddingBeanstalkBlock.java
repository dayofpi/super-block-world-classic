package com.dayofpi.super_block_world.common.blocks.plant;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
@SuppressWarnings("deprecation")
public class BuddingBeanstalkBlock extends AbstractBeanstalk implements Fertilizable {
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    public static final BooleanProperty OPEN;

    static {
        OPEN = Properties.OPEN;
    }

    public BuddingBeanstalkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, false));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isSkyVisible(pos) && random.nextInt(2) == 0) {
            world.setBlockState(pos, state.cycle(OPEN));
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(OPEN)) {
            for (int i = 0; i < 2; ++i)
                world.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, pos.getX() + 0.5 + random.nextDouble() - random.nextDouble(), pos.getY(), pos.getZ() + 0.5 + random.nextDouble() - random.nextDouble(), 0.0D, -0.3D * (random.nextDouble(1)), 0.0D);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (world.getBlockState(pos.up()).isAir()) {
            world.setBlockState(pos, PlantBlocks.BEANSTALK_PLANT.getDefaultState());
            world.setBlockState(pos.up(), state);
            if (random.nextInt(6) == 3) {
                grow(world, random, pos.up(), state);
            }
        }
    }
}
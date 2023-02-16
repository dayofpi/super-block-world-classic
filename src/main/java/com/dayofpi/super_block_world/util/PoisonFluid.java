package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.world.ModFluids;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public abstract class PoisonFluid extends FlowableFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_POISON;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.POISON;
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.POISON || fluid == ModFluids.FLOWING_POISON;
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 1;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 2;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.POISON_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !state.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.POISON.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Nullable
    @Override
    protected ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_OBSIDIAN_TEAR;
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        BlockPos blockPos = pos.up();
        if (world.getBlockState(blockPos).isAir() && !world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
            if (random.nextInt(60) == 0) {
                double x = (double) pos.getX() + random.nextDouble();
                double y = (double) pos.getY() + state.getLevel() / 7.0D;
                double z = (double) pos.getZ() + random.nextDouble();
                world.addParticle(ModParticles.POISON_BUBBLE, x, y, z, 0.0, 0.0, 0.0);
            }
            if (random.nextInt(200) == 0) {
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), Sounds.BLOCK_POISON_BUBBLE, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
        }
    }

    @Override
    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN) {
            FluidState fluidState2 = world.getFluidState(pos);
            if (fluidState2.isIn(FluidTags.WATER)) {
                if (state.getBlock() instanceof FluidBlock) {
                    world.setBlockState(pos, ModBlocks.VANILLATE.getDefaultState(), Block.NOTIFY_ALL);
                }
                world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
                return;
            }
            else if (fluidState2.isIn(FluidTags.LAVA)) {
                if (state.getBlock() instanceof FluidBlock) {
                    world.setBlockState(pos, ModBlocks.HARDSTONE.getDefaultState(), Block.NOTIFY_ALL);
                }
                world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
                return;
            }
        }
        super.flow(world, pos, state, direction, fluidState);
    }

    public static class Flowing extends PoisonFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends PoisonFluid {
        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
}

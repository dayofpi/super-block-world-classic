package com.dayofpi.super_block_world.common.fluids;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.more.FluidInit;
import com.dayofpi.super_block_world.registry.more.ParticleInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

public abstract class PoisonFluid extends AbstractFluid {
    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (world.getBlockState(pos.up()).isAir()) {
            if (random.nextInt(25) == 0) {
                double d = (double)pos.getX() + random.nextDouble();
                double e = (double)pos.getY() + 0.9D  + (random.nextDouble() * 0.2);
                double f = (double)pos.getZ() + random.nextDouble();
                world.addParticle(ParticleInit.POISON_BUBBLE, d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }
        if (random.nextInt(190) == 0) {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundInit.FLUID_POISON_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 1.0F + random.nextFloat() * 0.15F, true);
        }
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return BlockInit.POISON.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(fluidState));
    }

    public Fluid getFlowing() {
        return FluidInit.FLOWING_POISON;
    }

    public Fluid getStill() {
        return FluidInit.STILL_POISON;
    }

    public Item getBucketItem() {
        return ItemInit.POISON_BUCKET;
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundInit.ITEM_POISON_BUCKET_FILL);
    }

    public ParticleEffect getParticle() {
        return ParticleTypes.FALLING_OBSIDIAN_TEAR;
    }

    public static class Flowing extends PoisonFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends PoisonFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
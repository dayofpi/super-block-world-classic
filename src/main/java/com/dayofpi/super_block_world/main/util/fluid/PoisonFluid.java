package com.dayofpi.super_block_world.main.util.fluid;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.registry.block.ModBlocks;
import com.dayofpi.super_block_world.main.registry.ModItems;
import com.dayofpi.super_block_world.main.registry.ModFluid;
import com.dayofpi.super_block_world.main.registry.ModParticle;
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
    public Fluid getFlowing() {
        return ModFluid.FLOWING_POISON;
    }

    public Fluid getStill() {
        return ModFluid.STILL_POISON;
    }

    public Item getBucketItem() {
        return ModItems.POISON_BUCKET;
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        BlockPos abovePos = pos.up();
        if (world.getBlockState(abovePos).isAir()) {
            if (random.nextInt(25) == 0) {
                double d = (double)pos.getX() + random.nextDouble();
                double e = (double)pos.getY() + 0.4D  + (random.nextDouble() * 0.2);
                double f = (double)pos.getZ() + random.nextDouble();
                world.addParticle(ModParticle.POISON_BUBBLE, d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }
        if (random.nextInt(192) == 0) {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BLOCK_POISON_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 1.0F + random.nextFloat() * 0.15F, true);
        }
    }

    public ParticleEffect getParticle() {
        return ParticleTypes.FALLING_OBSIDIAN_TEAR;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.POISON.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(fluidState));
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(ModSounds.BLOCK_POISON_FILL);
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
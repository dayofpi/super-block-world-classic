package com.dayofpi.super_block_world.common.block.decoration;

import com.dayofpi.super_block_world.registry.other.ParticleInit;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class StarCrystal extends AmethystClusterBlock {
    public StarCrystal(int height, int xzOffset, Settings settings) {
        super(height, xzOffset, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.96F) {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, random.nextFloat(), 0.6F + (random.nextFloat() * 0.4F), true);
            world.addParticle(ParticleInit.STAR_BIT, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0D, 0.02D, 0D);
        }
    }
}

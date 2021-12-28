package com.dayofpi.super_block_world.main.common.block.building;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Random;

public class AmethystOreBlock extends OreBlock {
    public AmethystOreBlock(Settings settings, UniformIntProvider experienceDropped) {
        super(settings, experienceDropped);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.95F)
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, random.nextFloat(), 0.6F, false);
    }
}

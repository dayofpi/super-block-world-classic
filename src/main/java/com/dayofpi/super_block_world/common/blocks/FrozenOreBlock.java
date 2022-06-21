package com.dayofpi.super_block_world.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Random;

public class FrozenOreBlock extends OreBlock {
    public FrozenOreBlock(Settings settings, UniformIntProvider experienceDropped) {
        super(settings, experienceDropped);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            world.addParticle(ParticleTypes.SNOWFLAKE, true, pos.getX() + random.nextFloat(), pos.getY() + 1.0D, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }
}

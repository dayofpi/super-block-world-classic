package com.dayofpi.sbw_main.block.types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class AmethystOreBlock extends Block {
    public AmethystOreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(4) == 0) {
            double x = (double) pos.getX() + random.nextDouble() * 2;
            double y = (double) pos.getY() + random.nextDouble() * 2;
            double z = (double) pos.getZ() + random.nextDouble() * 2;
            DefaultParticleType particle = ParticleTypes.WAX_OFF;
            world.addParticle(particle, x, y, z , 0.0D, 0.0D, 0.0D);
        }
    }
}

package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.entity.types.projectiles.FlowerFireballEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class FrozenMuncherBlock extends Block {
    public FrozenMuncherBlock(Settings settings) {
        super(settings);
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        boolean isProjectileFiery = projectile.isOnFire() || projectile instanceof FlowerFireballEntity;
        if (!world.isClient && isProjectileFiery && projectile.canModifyAt(world, hit.getBlockPos())) {
            projectile.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F);
            world.setBlockState(hit.getBlockPos(), PlantBlocks.MUNCHER.getDefaultState());
        }
        if (world.isClient) {
            world.addParticle(ParticleTypes.SPLASH, projectile.getParticleX(1), projectile.getY(), projectile.getParticleZ(1), 0, 0, 0);
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
            world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.2F);
            world.setBlockState(pos, PlantBlocks.MUNCHER.getDefaultState());
            world.addParticle(ParticleTypes.SPLASH, pos.getX() + (2.0D * random.nextDouble() - 1.0D), pos.getY(), pos.getZ() + (2.0D * random.nextDouble() - 1.0D), 0, 0, 0);
        }
    }
}
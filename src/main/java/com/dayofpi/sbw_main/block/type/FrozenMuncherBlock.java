package com.dayofpi.sbw_main.block.type;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.entity.type.projectiles.FlowerFireballEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
        if (isProjectileFiery && projectile.canModifyAt(world, hit.getBlockPos()) && !world.isClient) {
            this.thaw((ServerWorld) world, hit.getBlockPos());
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos) && !world.isClient) {
            this.thaw(world, pos);
        }
    }

    private void thaw(ServerWorld world, BlockPos pos) {
        world.playSound(null, pos, ModSounds.ENTITY_ICEBALL_HIT, SoundCategory.BLOCKS, 1.0F, 1.2F);
        world.setBlockState(pos, PlantBlocks.MUNCHER.getDefaultState());
        world.spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 3, 0.0D, 0.0D, 0.0D, 0.0D);
}
}
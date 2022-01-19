package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.common.entities.FireballEntity;
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
        boolean isProjectileFiery = projectile.isOnFire() || projectile instanceof FireballEntity;
        if (isProjectileFiery && projectile.canModifyAt(world, hit.getBlockPos()) && !world.isClient) {
            this.thaw((ServerWorld) world, hit.getBlockPos());
            projectile.discard();
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos) && !world.isClient) {
            this.thaw(world, pos);
        }
    }

    private void thaw(ServerWorld world, BlockPos pos) {
        world.playSound(null, pos, SoundInit.ENTITY_ICEBALL_HIT, SoundCategory.BLOCKS, 1.0F, 1.2F);
        world.setBlockState(pos, PlantBlocks.MUNCHER.getDefaultState());
        world.spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 3, 0.0D, 0.0D, 0.0D, 0.0D);
    }
}
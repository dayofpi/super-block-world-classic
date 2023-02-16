package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.entity.entities.projectile.ModFireballEntity;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class FrozenMuncherBlock extends Block {
    public FrozenMuncherBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (world instanceof ServerWorld) {
            if (projectile.isOnFire() || projectile instanceof ModFireballEntity) {
                this.thaw((ServerWorld) world, hit.getBlockPos());
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
            this.thaw(world, pos);
        }
    }

    private void thaw(ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, ModBlocks.MUNCHER.getDefaultState());
        world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.1F);
        world.spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 3, 0.0D, 0.0D, 0.0D, 0.0D);
    }
}

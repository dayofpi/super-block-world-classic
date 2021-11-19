package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
@SuppressWarnings("deprecation")
public class FrozenMuncherBlock extends Block {
    public FrozenMuncherBlock(Settings settings) {
        super(settings);
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        projectile.playSound(SoundEvents.BLOCK_CANDLE_EXTINGUISH, 1.0F, 1.2F);
        world.setBlockState(hit.getBlockPos(), PlantBlocks.MUNCHER.getDefaultState());
    }
}

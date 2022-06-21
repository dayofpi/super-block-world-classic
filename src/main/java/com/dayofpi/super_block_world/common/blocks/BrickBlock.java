package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.entities.projectile.HammerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BrickBlock extends Block {
    public BrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient)
            if (projectile instanceof HammerEntity || projectile instanceof ExplosiveProjectileEntity)
                world.breakBlock(hit.getBlockPos(), true);
    }
}

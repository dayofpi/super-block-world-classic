package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.common.entities.projectile.BoomerangEntity;
import com.dayofpi.super_block_world.common.entities.projectile.HammerEntity;
import net.minecraft.block.AmethystBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterBlockMixin extends AmethystBlock {
    public AmethystClusterBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);
        if (projectile instanceof HammerEntity || projectile instanceof BoomerangEntity)
            world.breakBlock(hit.getBlockPos(), true, projectile.getOwner());
    }
}

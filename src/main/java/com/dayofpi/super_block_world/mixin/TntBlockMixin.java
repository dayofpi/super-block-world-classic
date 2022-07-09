package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.common.entities.projectile.ModFireballEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntBlock.class)
public class TntBlockMixin {
    @Inject(at=@At("HEAD"), method = "onProjectileHit")
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            Entity entity = projectile.getOwner();
            if (projectile instanceof ModFireballEntity && projectile.canModifyAt(world, blockPos)) {
                TntBlockInterface.primeTnt(world, blockPos, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                projectile.discard();
                world.removeBlock(blockPos, false);
            }
        }
    }
}

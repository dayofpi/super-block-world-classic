package com.dayofpi.super_block_world.mixin.main.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class AllowCacti {

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        BlockState floor = world.getBlockState(pos.down());
        info.setReturnValue(floor.isOf(Blocks.CACTUS) || floor.isIn(BlockTags.SAND) && !world.getBlockState(pos.up()).getMaterial().isLiquid());
        info.cancel();
    }
}

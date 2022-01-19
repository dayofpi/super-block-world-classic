package com.dayofpi.super_block_world.mixin.main.block;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StemBlock.class)
public class AllowStem {
    @Inject(at = @At("HEAD"), method = ("canPlantOnTop(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z"), cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(floor.isOf(Blocks.FARMLAND) || floor.isOf(BlockInit.TOADSTOOL_FARMLAND));
        info.cancel();
    }
}
package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Carver.class)
public class AllowCarvers {
    // Allows carvers (caves, ravines, etc.) to go through mod blocks
    @Inject(at = @At("HEAD"), method = "canAlwaysCarveBlock(Lnet/minecraft/block/BlockState;)Z", cancellable = true)
    private void canAlwaysCarveBlock(BlockState state, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(state.isIn(ModTags.ALWAYS_CARVABLE));
        info.cancel();
    }
}
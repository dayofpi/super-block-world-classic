package com.dayofpi.super_block_world.mixin.main.world;

import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Carver.class)
public class AllowCarvers {
    @Shadow protected Set<Block> alwaysCarvableBlocks;

    // Allows carvers (caves, ravines, etc.) to go through mod blocks
    @Inject(at = @At("HEAD"), method = "canAlwaysCarveBlock(Lnet/minecraft/block/BlockState;)Z", cancellable = true)
    private void canAlwaysCarveBlock(BlockState state, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(this.alwaysCarvableBlocks.contains((state.getBlock())) || state.isIn(TagInit.ALWAYS_CARVABLE));
        info.cancel();
    }
}
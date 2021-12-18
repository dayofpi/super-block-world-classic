package com.dayofpi.super_block_world.mixin.main.world;

import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.random.RandomSeed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalLong;

@Mixin(MoreOptionsDialog.class)
public class RandomizeSeed {

    @Shadow private OptionalLong seed;

    @Inject(at=@At("TAIL"), method = "importOptions", cancellable = true)
    private void importOptions(DynamicRegistryManager.Impl registryManager, GeneratorOptions generatorOptions, CallbackInfo info) {
        this.seed = OptionalLong.of(RandomSeed.getSeed());
        info.cancel();
    }
}
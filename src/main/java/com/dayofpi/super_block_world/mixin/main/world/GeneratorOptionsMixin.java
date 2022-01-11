package com.dayofpi.super_block_world.mixin.main.world;

import com.dayofpi.super_block_world.common.util.mixin_aid.SeedSupplier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(GeneratorOptions.class)
public abstract class GeneratorOptionsMixin {
    // source: https://github.com/Haven-King/seedy-behavior
    @Redirect(method = "method_28606(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/PrimitiveCodec;fieldOf(Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;", ordinal = 0))
    private static MapCodec<Long> giveUsRandomSeeds(PrimitiveCodec<Long> codec, final String name) {
        return codec.fieldOf(name).orElseGet(SeedSupplier::getSeed);
    }

    @Inject(method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V",
            at = @At(value = "RETURN"))
    private void giveUsTrueWorldSeed(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> options, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        SeedSupplier.setSeed(seed);
    }
}

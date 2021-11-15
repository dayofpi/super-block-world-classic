package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(SignType.class)
public class MixinSignType {
    @Accessor
    static Set<SignType> getVALUES() {
        throw new UnsupportedOperationException();
    }

    @Inject(at=@At("HEAD"), method = "register")
    private static void register(SignType type, CallbackInfoReturnable<SignType> cir) {
        getVALUES().add(ModBlocks.AMANITA);
    }
}

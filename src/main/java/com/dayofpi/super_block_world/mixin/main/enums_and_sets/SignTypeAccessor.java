package com.dayofpi.super_block_world.mixin.main.enums_and_sets;

import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignType.class)
public interface SignTypeAccessor {
    @Invoker("<init>")
    static SignType newSignType(String name) {
        throw new AssertionError();
    }

    @Invoker("register")
    static SignType registerNew(SignType type) {
        throw new AssertionError();
    }
}

package com.dayofpi.sbw_mixin.interfaces;

import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerEntity.class)
public interface InterfaceClientPlayer {
    @Accessor("input")
    Input input();

    @Accessor("riding")
    boolean riding();

}

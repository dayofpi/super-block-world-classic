package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.ModTags;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayer extends AbstractClientPlayerEntity {
    public ClientPlayer(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("HEAD"), method = "updateWaterSubmersionState()Z", cancellable = true)
    private void updateWaterSubmersionState(CallbackInfoReturnable<Boolean> info) {
        if (this.updateMovementInFluid(ModTags.POISON, 0.014D)) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}

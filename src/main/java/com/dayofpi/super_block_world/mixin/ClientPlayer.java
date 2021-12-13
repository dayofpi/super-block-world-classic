package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.main.registry.ModTags;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Environment(value= EnvType.CLIENT)
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

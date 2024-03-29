package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.audio.KartSoundInstance;
import com.dayofpi.super_block_world.entity.entities.misc.GoKartEntity;
import com.dayofpi.super_block_world.entity.renderers.GooMeRenderer;
import com.dayofpi.super_block_world.util.FormManager;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayerEntity {
    @Shadow private boolean riding;
    @Shadow public Input input;
    @Shadow @Final protected MinecraftClient client;

    public ClientPlayerMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public Identifier getSkinTexture() {
        if (this.dataTracker.get(FormManager.GOO_ME_UUID).isPresent()) {
            return GooMeRenderer.TEXTURE;
        }
        return super.getSkinTexture();
    }

    @Inject(at=@At("HEAD"), method = "startRiding")
    public void startRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (super.startRiding(entity, force) && entity instanceof GoKartEntity) {
            this.client.getSoundManager().play(new KartSoundInstance(this, (GoKartEntity)entity, true));
            this.client.getSoundManager().play(new KartSoundInstance(this, (GoKartEntity)entity, false));
        }
    }

    @Inject(at=@At("TAIL"), method = "tickRiding")
    public void tickRiding(CallbackInfo ci) {
        if (this.getVehicle() instanceof GoKartEntity kartEntity) {
            kartEntity.setInputs(this.input.pressingLeft, this.input.pressingRight, this.input.pressingForward, this.input.pressingBack);
            this.riding |= this.input.pressingLeft || this.input.pressingRight || this.input.pressingForward || this.input.pressingBack;
        }
    }
}

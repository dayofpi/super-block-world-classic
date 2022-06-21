package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value = EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class NetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

    private void playSound(Entity entity, SoundEvent soundEvent) {
        this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), soundEvent, SoundCategory.PLAYERS, (float) 0.2, (float) 1.0, false);
    }

    @Inject(at = @At("HEAD"), method = "onItemPickupAnimation")
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity == null)
            return;
        if (entity instanceof ItemEntity item) {
            if (item.getStack().isOf(ModItems.COIN)) {
                this.playSound(entity, Sounds.ITEM_COIN);
            } else if (item.getStack().isOf(ModItems.STAR_COIN)) {
                this.playSound(entity, Sounds.ITEM_STAR_COIN);
            } else if (item.getStack().isIn(ModTags.STAR_BITS)) {
                this.playSound(entity, Sounds.ITEM_STAR_BIT);
            }
        }
    }
}

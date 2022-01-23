package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value= EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class PickupSound {

    @Shadow private ClientWorld world;

    private void playSound(Entity entity, SoundEvent soundEvent, float volume, float pitch) {
        this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), soundEvent, SoundCategory.PLAYERS, volume, pitch, false);
    }

    @Inject(at=@At("HEAD"), method = "onItemPickupAnimation")
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity != null) {
            if (entity instanceof ItemEntity item) {
                if (item.getStack().isOf(ItemInit.COIN)) {
                    this.playSound(entity, SoundInit.ITEM_COLLECT_COIN, 0.2F, 1.0F);
                } else if (item.getStack().isOf(ItemInit.STAR_COIN)) {
                    this.playSound(entity, SoundInit.ITEM_COLLECT_STAR_COIN, 0.2F, 1.0F);
                } else if (item.getStack().isIn(TagInit.STAR_BITS)) {
                    this.playSound(entity, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.7f);
                }
            }
        }
    }
}

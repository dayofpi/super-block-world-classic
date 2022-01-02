package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.sound.SoundCategory;
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

    @Inject(at=@At("HEAD"), method = "onItemPickupAnimation")
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity != null) {
            if (entity instanceof ItemEntity item) {
                if (item.getStack().isOf(ItemRegistry.COIN)) {
                    this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.ITEM_COIN_COLLECT, SoundCategory.PLAYERS, 0.2f, 1.0f, false);
                } else if (item.getStack().isOf(ItemRegistry.STAR_COIN)) {
                    this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.ITEM_STAR_COIN_COLLECT, SoundCategory.PLAYERS, 0.2f, 1.0f, false);
                } else if (item.getStack().isOf(ItemRegistry.BLUE_STAR_BIT) || item.getStack().isOf(ItemRegistry.PURPLE_STAR_BIT) || item.getStack().isOf(ItemRegistry.GREEN_STAR_BIT)) {
                    this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 1.0f, 1.7f, false);
                }
            }
        }
    }
}

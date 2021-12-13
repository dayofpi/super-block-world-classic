package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Environment(value= EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class CoinPickupSound {

    @Shadow private ClientWorld world;

    @Shadow @Final private Random random;

    @Inject(at=@At("HEAD"), method = "onItemPickupAnimation")
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity != null) {
            if (entity instanceof ItemEntity item) {
                if (item.getStack().isOf(ModItems.COIN)) {
                    this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.ITEM_COLLECT_COIN, SoundCategory.PLAYERS, 0.2f, 1.0f, false);
                } else if (item.getStack().isOf(ModItems.STAR_COIN)) {
                    this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.ITEM_COLLECT_STAR_COIN, SoundCategory.PLAYERS, 0.2f, 1.0f, false);
                }
            }
        }
    }
}

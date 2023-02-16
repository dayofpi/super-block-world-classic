package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.ModParticles;
import com.dayofpi.super_block_world.ModTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class NetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

    @Final
    @Shadow
    private MinecraftClient client;

    private void playSound(Entity entity, SoundEvent soundEvent) {
        this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), soundEvent, SoundCategory.PLAYERS, (float) 0.2, (float) 1.0, false);
    }

    @Inject(at = @At(value = "TAIL"), method = "onGameStateChange")
    public void onGameStateChange(GameStateChangeS2CPacket packet, CallbackInfo info) {
        ClientPlayerEntity playerEntity = this.client.player;
        GameStateChangeS2CPacket.Reason reason = packet.getReason();
        float f = packet.getValue();
        int i = MathHelper.floor(f + 0.5f);
        if (reason == Main.KING_BOO_CURSE && playerEntity != null) {
            this.world.addParticle(ModParticles.KING_BOO_CURSE, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), 0.0, 0.0, 0.0);
            if (i == 1) {
                this.world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), Sounds.ENTITY_KING_BOO_CURSE, SoundCategory.HOSTILE, 1.0f, 1.0f);
            }
        }
    }

    @Inject(at=@At("HEAD"), method="getActiveTotemOfUndying", cancellable = true)
    private static void getActiveTotemOfUndying(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isOf(ModItems.LIFE_MUSHROOM)) continue;
            cir.setReturnValue(itemStack);
        }
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

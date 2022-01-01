package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.client.sound.ModMusic;
import com.dayofpi.super_block_world.main.common.world.dimension.MushroomKingdom;
import com.dayofpi.super_block_world.main.registry.misc.StatusEffectRegistry;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Environment(value= EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class CustomMusic {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientWorld world;

    // The code for Iris Music
    @Inject(at = @At("HEAD"), method = "getMusicType", cancellable = true)
    public void getMusicType(CallbackInfoReturnable<MusicSound> info) {
        if (this.player != null && this.world != null) {
            if (this.world.getRegistryKey() == MushroomKingdom.WORLD_KEY) {
                World world = this.player.world;
                BlockPos pos = this.player.getBlockPos();
                if (world.getLightLevel(pos) <= 7 && !world.isSkyVisible(pos) && world.getFluidState(pos).isEmpty()) {
                    info.setReturnValue(ModMusic.CAVE);
                } else {
                    if (this.world != null) {
                        info.setReturnValue(this.world.getBiomeAccess().getBiomeForNoiseGen(pos).getMusic().orElse(ModMusic.GRASSLAND));
                    }
                }
                info.cancel();
            }
        } else {
            info.setReturnValue(ModMusic.MENU);
            info.cancel();
        }
        if (this.player != null && this.player.getActiveStatusEffects().containsKey(StatusEffectRegistry.STAR_POWER)) {
            info.setReturnValue(ModMusic.STAR);
            info.cancel();
        } else if (this.player != null && !this.player.isUsingItem() && this.player.getActiveHand() == Hand.MAIN_HAND && this.player.handSwinging && this.player.getStackInHand(Hand.MAIN_HAND).isOf(ItemRegistry.SUPER_PICKAX)) {
            info.setReturnValue(ModMusic.PICKAX);
            info.cancel();
        }
    }
}

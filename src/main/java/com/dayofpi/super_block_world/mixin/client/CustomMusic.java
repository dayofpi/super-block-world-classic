package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.client.sound.ModMusic;
import com.dayofpi.super_block_world.registry.more.StatusEffectInit;
import com.dayofpi.super_block_world.world.MushroomKingdom;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value= EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class CustomMusic {
    @Shadow @Nullable public ClientPlayerEntity player;
    @Shadow @Nullable public ClientWorld world;
    @Shadow public abstract MusicTracker getMusicTracker();

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo info) {
        MusicTracker musicTracker = this.getMusicTracker();
        if (this.player != null) {
            StatusEffectInstance starPower = this.player.getStatusEffect(StatusEffectInit.STAR_POWER);
            if  (starPower != null && starPower.getDuration() < 490) {
                if (!musicTracker.isPlayingType(ModMusic.STAR))
                    musicTracker.play(ModMusic.STAR);
            } else if (musicTracker.isPlayingType(ModMusic.STAR)) {
                musicTracker.stop();
            }
        }
    }

    // The code for Iris Music
    @Inject(at = @At("HEAD"), method = "getMusicType", cancellable = true)
    public void getMusicType(CallbackInfoReturnable<MusicSound> info) {
        if (this.player != null && this.world != null) {
            if (this.world.getRegistryKey() == MushroomKingdom.WORLD_KEY) {
                World world = this.player.world;
                BlockPos blockPos = this.player.getBlockPos();
                if ((world.getTimeOfDay() % 24000L) > 12000 && world.isSkyVisibleAllowingSea(blockPos)) {
                    info.setReturnValue(ModMusic.NIGHT);
                } else if (world.getLightLevel(blockPos) <= 7 && !world.isSkyVisible(blockPos)) {
                    info.setReturnValue(ModMusic.CAVE);
                } else {
                    info.setReturnValue(this.world.getBiomeAccess().getBiomeForNoiseGen(blockPos).getMusic().orElse(ModMusic.DEFAULT));
                }
                info.cancel();
            }
        } else {
            info.setReturnValue(ModMusic.MENU);
            info.cancel();
        }
    }
}

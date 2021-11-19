package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.TagList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class CustomMusic {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientWorld world;

    // The code for Iris Music
    @Inject(at = @At("HEAD"), method = "getMusicType", cancellable = true)
    public void getMusicType(CallbackInfoReturnable<MusicSound> info) {
        if (this.player != null) {
            if (TagList.SURFACE.contains(this.player.world.getBiome(this.player.getBlockPos()))) {
                World world = this.player.world;
                BlockPos pos = this.player.getBlockPos();
                if (pos.getY() < world.getSeaLevel() && world.getLightLevel(pos) <= 7 && !world.isSkyVisible(pos)) {
                    info.setReturnValue(Main.CAVE);
                } else {
                    if (this.world != null) {
                        info.setReturnValue(this.world.getBiomeAccess().getBiomeForNoiseGen(pos).getMusic().orElse(Main.GRASSLAND));
                    }
                }
                info.cancel();
            }
        }

    }
}

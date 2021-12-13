package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.world.dimension.MushroomKingdom;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
@Environment(value= EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class CustomMusic {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientWorld world;

    private static final MusicSound GRASSLAND = new MusicSound(ModSounds.MUSIC_GRASSLAND, 12000, 24000, false);
    private static final MusicSound CAVE = new MusicSound(ModSounds.MUSIC_CAVE, 10000, 20000, false);

    // The code for Iris Music
    @Inject(at = @At("HEAD"), method = "getMusicType", cancellable = true)
    public void getMusicType(CallbackInfoReturnable<MusicSound> info) {
        if (this.player != null && this.world != null) {
            if (this.world.getRegistryKey() == MushroomKingdom.WORLD_KEY) {
                World world = this.player.world;
                BlockPos pos = this.player.getBlockPos();
                if (pos.getY() < world.getSeaLevel() && world.getLightLevel(pos) <= 7 && !world.isSkyVisible(pos)) {
                    info.setReturnValue(CAVE);
                } else {
                    if (this.world != null) {
                        info.setReturnValue(this.world.getBiomeAccess().getBiomeForNoiseGen(pos).getMusic().orElse(GRASSLAND));
                    }
                }
                info.cancel();
            }
        }
    }
}

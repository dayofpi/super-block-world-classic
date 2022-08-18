package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.common.entities.boss.ModBossEntity;
import com.dayofpi.super_block_world.common.entities.misc.GoKartEntity;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import com.dayofpi.super_block_world.registry.ModTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class ClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Nullable
    public ClientPlayerInteractionManager interactionManager;

    @Shadow
    public abstract MusicTracker getMusicTracker();

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo info) {
        MusicTracker musicTracker = this.getMusicTracker();
        if (this.player != null) {
            StatusEffectInstance starPower = this.player.getStatusEffect(Main.STAR_POWER);
            if (starPower != null && starPower.getDuration() < 490 && starPower.getDuration() > 10) {
                if (!musicTracker.isPlayingType(ModMusic.STAR))
                    musicTracker.play(ModMusic.STAR);
            } else if (musicTracker.isPlayingType(ModMusic.STAR)) {
                musicTracker.stop();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getMusicType", cancellable = true)
    public void getMusicType(CallbackInfoReturnable<MusicSound> cir) {
        if (this.player == null)
            return;
        World world = this.player.world;
        BlockPos pos = this.player.getBlockPos();

        List<Entity> bossList = world.getOtherEntities(this.player, new Box(pos).expand(16, 16, 16), entity -> entity instanceof ModBossEntity);

        if (!bossList.isEmpty()) {
            ModBossEntity modBossEntity = (ModBossEntity) bossList.get(0);
            MusicSound bossMusic = modBossEntity.getBossMusic();
            if (modBossEntity.canTarget(this.player) && bossMusic != null) {
                cir.setReturnValue(modBossEntity.getBossMusic());
            }
        }

        if (world.getServer() != null) {
            ServerWorld serverWorld = world.getServer().getWorld(player.world.getRegistryKey());
            if (serverWorld == null)
                return;
            BlockPos structurePos = serverWorld.locateStructure(ModTags.GHOST_HOUSE, pos, 0, false);
            if (structurePos != null) {
                cir.setReturnValue(ModMusic.GHOST_HOUSE);
            }
        }

        RegistryEntry<Biome> registryEntry = this.player.world.getBiome(pos);
        if (world.getBiome(pos).isIn(ModTags.SURFACE_BIOMES)) {
            cir.setReturnValue(registryEntry.value().getMusic().orElse(getEnvironmentMusic(world, pos)));
        }
    }

    private MusicSound getEnvironmentMusic(World world, BlockPos pos) {
        if (!world.isSkyVisibleAllowingSea(pos))
            return ModMusic.CAVE;
        if (world.getLightLevel(pos) < 8)
            return ModMusic.NIGHTTIME;
        return ModMusic.DAYTIME;
    }

    @Inject(at = @At("HEAD"), method = "doAttack")
    private void doAttack(CallbackInfoReturnable<Boolean> cir) {
        if (this.player == null || this.interactionManager == null)
            return;
        Entity entity = this.player.getVehicle();
        if (entity instanceof YoshiEntity && !((YoshiEntity) entity).isTongueOut())
            this.interactionManager.interactEntity(player, entity, player.getActiveHand());
        if (entity instanceof GoKartEntity)
            this.interactionManager.interactEntity(player, entity, player.getActiveHand());
    }

}

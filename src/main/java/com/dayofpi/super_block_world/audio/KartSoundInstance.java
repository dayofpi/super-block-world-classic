package com.dayofpi.super_block_world.audio;

import com.dayofpi.super_block_world.common.entities.misc.GoKartEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class KartSoundInstance extends MovingSoundInstance {
    private final PlayerEntity player;
    private final GoKartEntity kart;
    private final boolean underwater;

    public KartSoundInstance(PlayerEntity player, GoKartEntity kart, boolean underwater) {
        super(underwater ? Sounds.ENTITY_GO_KART_LOOP_UNDERWATER : Sounds.ENTITY_GO_KART_LOOP, SoundCategory.NEUTRAL, SoundInstance.createRandom());
        this.player = player;
        this.kart = kart;
        this.underwater = underwater;
        this.attenuationType = SoundInstance.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0f;
    }

    @Override
    public boolean canPlay() {
        return !this.kart.isSilent();
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }

    @Override
    public void tick() {
        if (this.kart.isRemoved() || !this.player.hasVehicle() || this.player.getVehicle() != this.kart) {
            this.setDone();
            return;
        }
        if (this.underwater != this.player.isSubmergedInWater()) {
            this.volume = 0.0f;
            return;
        }
        float f = (float)this.kart.getVelocity().horizontalLength();
        this.volume = f >= 0.01f ? MathHelper.clampedLerp(0.0f, 0.75f, f) : 0.0f;
    }
}

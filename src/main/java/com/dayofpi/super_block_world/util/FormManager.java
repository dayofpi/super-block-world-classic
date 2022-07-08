package com.dayofpi.super_block_world.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;
import java.util.UUID;

public class FormManager {
    public static final TrackedData<Optional<UUID>> GOO_ME_UUID = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public static boolean isGooMeImmuneTo(DamageSource source) {
        return source == DamageSource.CACTUS || source == DamageSource.SWEET_BERRY_BUSH || source == ModDamageSource.SPIKES;
    }
}

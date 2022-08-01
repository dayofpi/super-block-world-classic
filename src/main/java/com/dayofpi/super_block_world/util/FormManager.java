package com.dayofpi.super_block_world.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;
import java.util.UUID;

public interface FormManager {
    String POWER_LEVEL_KEY = "PowerLevel";
    String POWER_HEALTH_KEY = "PowerHealth";
    String POWER_TICK_TIMER_KEY = "PowerTickTimer";
    String POWER_UP_KEY = "PowerUp";
    String GOO_ME_KEY = "GooMe";
    TrackedData<String> POWER_UP = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.STRING);
    TrackedData<Integer> POWER_LEVEL = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    TrackedData<Integer> POWER_HEALTH = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    TrackedData<Optional<UUID>> GOO_ME_UUID = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    UUID CAT_SPEED_BOOST_ID = UUID.randomUUID();

    static boolean isGooMeImmuneTo(DamageSource source) {
        return source == DamageSource.CACTUS || source == DamageSource.SWEET_BERRY_BUSH || source == ModDamageSource.SPIKES;
    }
}

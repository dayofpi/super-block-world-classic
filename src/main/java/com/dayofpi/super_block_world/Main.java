package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.registry.other.FluidInit;
import com.dayofpi.super_block_world.registry.other.ParticleInit;
import com.dayofpi.super_block_world.registry.other.StatusEffectInit;
import com.dayofpi.super_block_world.registry.other.WorldInit;
import com.dayofpi.super_block_world.common.util.block_entity.DispenserBehaviorInit;
import com.dayofpi.super_block_world.common.util.entity.ModDamageSource;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class Main implements ModInitializer {
    public static final String MOD_ID = "super_block_world";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        GeckoLib.initialize();
        FluidInit.register();
        ModDamageSource.register();
        BlockInit.register();
        TagInit.register();
        SoundInit.register();
        ItemInit.register();
        EntityInit.register();
        StatusEffectInit.register();
        ParticleInit.register();
        WorldInit.register();
        DispenserBehaviorInit.register();
        LOGGER.info("Super Block World successfully initialized");
    }
}

package com.dayofpi.super_block_world.main;

import com.dayofpi.super_block_world.main.common.block_entity.behavior.ModDispenserBehavior;
import com.dayofpi.super_block_world.main.registry.*;
import com.dayofpi.super_block_world.main.registry.block.ModBlocks;
import com.dayofpi.super_block_world.main.registry.world.ModConfiguredFeature;
import com.dayofpi.super_block_world.main.registry.world.ModFeature;
import com.dayofpi.super_block_world.main.registry.world.ModPlacedFeature;
import com.dayofpi.super_block_world.main.registry.world.ModStructure;
import com.dayofpi.super_block_world.main.registry.world.biome.ModBiomes;
import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.world.dimension.MushroomKingdom;
import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.CPASoundEventData;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "super_block_world";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Identifier DIMENSION_ID = new Identifier(MOD_ID, "mushroom_kingdom");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Super Block World successfully initialized");
        MushroomKingdom.initializeDimension();
        ModTags.registerTags();
        ModSounds.registerSounds();
        ModBlocks.registerBlocks();
        ModFluid.registerFluids();
        ModEntities.registerEntities();
        ModItems.registerItems();
        ModFeature.registerFeatures();
        ModPlacedFeature.registerPlacedFeatures();
        ModConfiguredFeature.registerConfiguredFeatures();
        ModStructure.registerStructures();
        ModBiomes.registerBiomes();
        ModStatusEffects.registerEffects();
        ModParticle.registerParticles();
        ModDispenserBehavior.addDispenserBehaviors();
        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.WARP_FRAME)
                .tintColor(188, 112, 255).destDimID(DIMENSION_ID)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(ModSounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(ModSounds.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
    }
}

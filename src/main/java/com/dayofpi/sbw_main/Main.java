package com.dayofpi.sbw_main;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.ModFluid;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.ModDispenserBehavior;
import com.dayofpi.sbw_main.world.registry.*;
import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.CPASoundEventData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "super_block_world";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Identifier DIMENSION_ID = new Identifier(MOD_ID, "mushroom_kingdom");
    public static RegistryKey<DimensionType> DIMENSION_KEY;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Super Block World successfully initialized");
        DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, DIMENSION_ID);
        ModTags.registerTags();
        ModKeys.registerKeys();
        ModBlocks.registerBlocks();
        ModFluid.registerFluids();
        ModEntities.registerEntities();
        ModItems.registerItems();
        ModFeature.registerFeatures();
        ModPlacedFeature.registerPlacedFeatures();
        ModConfiguredFeature.registerConfiguredFeatures();
        ModStructure.registerStructures();
        ModBiome.registerBiomes();
        ModEffects.registerEffects();
        ModParticle.registerParticles();
        ModSounds.registerSounds();
        ModDispenserBehavior.addDispenserBehaviors();
        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.WARP_FRAME)
                .tintColor(188, 112, 255).destDimID(DIMENSION_ID)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(ModSounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(ModSounds.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
    }
}

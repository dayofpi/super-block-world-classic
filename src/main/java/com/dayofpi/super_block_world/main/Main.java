package com.dayofpi.super_block_world.main;

import com.dayofpi.super_block_world.main.util.ModDispenserBehavior;
import com.dayofpi.super_block_world.main.registry.*;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureConfiguration;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureType;
import com.dayofpi.super_block_world.main.registry.world.feature.FeaturePlacement;
import com.dayofpi.super_block_world.main.registry.world.feature.StructureRegistry;
import com.dayofpi.super_block_world.main.registry.world.biome.BiomeRegistry;
import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.common.world.dimension.MushroomKingdom;
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
        BiomeRegistry.registerBiomes();
        MushroomKingdom.initializeDimension();
        TagRegistry.registerTags();
        ModSounds.registerSounds();
        BlockRegistry.registerBlocks();
        FluidRegistry.registerFluids();
        EntityRegistry.registerEntities();
        ItemRegistry.registerItems();
        FeatureType.registerFeatures();
        FeatureConfiguration.registerConfiguredFeatures();
        FeaturePlacement.registerPlacedFeatures();
        StructureRegistry.registerStructures();
        StatusEffectReg.registerEffects();
        ParticleReg.registerParticles();
        ModDispenserBehavior.addDispenserBehaviors();
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockRegistry.WARP_FRAME)
                .tintColor(188, 112, 255).destDimID(DIMENSION_ID)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(ModSounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(ModSounds.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
    }
}

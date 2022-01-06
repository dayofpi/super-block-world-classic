package com.dayofpi.super_block_world.main.registry.other;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.world.dimension.MushroomKingdom;
import com.dayofpi.super_block_world.main.registry.main.BlockInit;
import com.dayofpi.super_block_world.main.registry.world.biome.BiomeRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.ConfiguredFeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.PlacedFeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.StructureRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.CPASoundEventData;
import net.minecraft.util.Identifier;

public class WorldInit {
    public static final Identifier DIMENSION_ID = new Identifier(Main.MOD_ID, "mushroom_kingdom");

    public static void register() {
        FeatureRegistry.registerFeatures();
        ConfiguredFeatureRegistry.registerConfiguredFeatures();
        PlacedFeatureRegistry.registerPlacedFeatures();
        BiomeRegistry.registerBiomes();
        StructureRegistry.registerStructures();
        MushroomKingdom.initializeDimension();
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.WARP_FRAME)
                .tintColor(188, 112, 255).destDimID(DIMENSION_ID)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(SoundInit.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(SoundInit.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
    }
}

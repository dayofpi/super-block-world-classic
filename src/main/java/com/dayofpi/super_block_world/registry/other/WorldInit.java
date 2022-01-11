package com.dayofpi.super_block_world.registry.other;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.world.dimension.MushroomKingdom;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.world.biome.BiomeInit;
import com.dayofpi.super_block_world.registry.world.feature.ConfiguredFeatureInit;
import com.dayofpi.super_block_world.registry.world.feature.FeatureInit;
import com.dayofpi.super_block_world.registry.world.feature.PlacedFeatureInit;
import com.dayofpi.super_block_world.registry.world.feature.StructureInit;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.CPASoundEventData;
import net.minecraft.util.Identifier;

public class WorldInit {
    public static final Identifier DIMENSION_ID = new Identifier(Main.MOD_ID, "mushroom_kingdom");

    public static void register() {
        FeatureInit.registerFeatures();
        ConfiguredFeatureInit.registerConfiguredFeatures();
        PlacedFeatureInit.registerPlacedFeatures();
        BiomeInit.registerBiomes();
        StructureInit.registerStructures();
        MushroomKingdom.initializeDimension();
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.WARP_FRAME)
                .tintColor(188, 112, 255).destDimID(DIMENSION_ID)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(SoundInit.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(SoundInit.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
    }
}

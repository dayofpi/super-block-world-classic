package com.dayofpi.super_block_world.world;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.world.biome.BiomeInit;
import com.dayofpi.super_block_world.world.feature.ConfiguredFeatureInit;
import com.dayofpi.super_block_world.world.feature.FeatureInit;
import com.dayofpi.super_block_world.world.feature.PlacedFeatureInit;
import com.dayofpi.super_block_world.world.structure.StructureInit;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.CPASoundEventData;
import net.minecraft.util.Identifier;

public class WorldInit {
    public static final Identifier DIMENSION_ID = new Identifier(Main.MOD_ID, "mushroom_kingdom");
    public static final Identifier WARP_FRAME_TESTER = new Identifier(Main.MOD_ID, "warp_frame_tester");

    public static void register() {
        CustomPortalApiRegistry.registerPortalFrameTester(WARP_FRAME_TESTER, WarpPortalAreaHelper::new);
        FeatureInit.registerFeatures();
        ConfiguredFeatureInit.registerConfiguredFeatures();
        PlacedFeatureInit.registerPlacedFeatures();
        BiomeInit.registerBiomes();
        StructureInit.registerStructures();
        MushroomKingdom.initializeDimension();
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.WARP_FRAME)
                .tintColor(215, 123, 186).destDimID(DIMENSION_ID)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(SoundInit.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(SoundInit.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .customFrameTester(WARP_FRAME_TESTER)
                .registerPortal();
    }
}

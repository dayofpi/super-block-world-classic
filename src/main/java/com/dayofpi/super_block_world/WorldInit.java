package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ConfiguredFeatures;
import com.dayofpi.super_block_world.registry.Features;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.ModFrameTester;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.CPASoundEventData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class WorldInit {
    private static final Identifier MUSHROOM_KINGDOM = new Identifier(Main.MOD_ID, "mushroom_kingdom");
    private static final Identifier BOWSERS_KINGDOM = new Identifier(Main.MOD_ID, "bowsers_kingdom");

    private static RegistryKey<World> MUSHROOM_KINGDOM_WORLD;
    private static final Identifier PORTAL_FRAME_TESTER = new Identifier(Main.MOD_ID, "warp_portal");

    public static void initialize() {
        Features.register();
        ConfiguredFeatures.register();

        CustomPortalApiRegistry.registerPortalFrameTester(PORTAL_FRAME_TESTER, ModFrameTester::new);
        CustomPortalBuilder.beginPortal().frameBlock(ModBlocks.WARP_FRAME).lightWithItem(ModItems.POWER_STAR).destDimID(WorldInit.MUSHROOM_KINGDOM).tintColor(16744174).customFrameTester(PORTAL_FRAME_TESTER).registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(Sounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F)).registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(Sounds.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F)).registerPortal();
        CustomPortalBuilder.beginPortal().frameBlock(ModBlocks.CORRUPTED_WARP_FRAME).lightWithItem(ModItems.ZTAR).destDimID(WorldInit.BOWSERS_KINGDOM).tintColor(9967931).customFrameTester(PORTAL_FRAME_TESTER).registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(Sounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F)).registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(Sounds.BLOCK_DARK_PORTAL_TRAVEL, 1.0F, 1.0F)).registerPortal();

        MUSHROOM_KINGDOM_WORLD = RegistryKey.of(Registry.WORLD_KEY, MUSHROOM_KINGDOM);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Main.LOGGER.info("Preparing dimension for server");

            if (server.getWorld(MUSHROOM_KINGDOM_WORLD) == null) throw new AssertionError("Mario does not exist.");
        });
    }
}

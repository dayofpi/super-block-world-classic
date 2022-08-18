package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ConfiguredFeatures;
import com.dayofpi.super_block_world.registry.Features;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.ModFrameTester;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.event.CPASoundEventData;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class WorldInit {
    private static final Identifier MUSHROOM_KINGDOM = new Identifier(Main.MOD_ID, "mushroom_kingdom");
    private static final Identifier BOWSERS_KINGDOM = new Identifier(Main.MOD_ID, "bowsers_kingdom");

    public static final RegistryKey<World> MUSHROOM_KINGDOM_WORLD = RegistryKey.of(Registry.WORLD_KEY, MUSHROOM_KINGDOM);
    public static final RegistryKey<World> BOWSERS_KINGDOM_WORLD = RegistryKey.of(Registry.WORLD_KEY, BOWSERS_KINGDOM);
    public static ServerWorld DIMENSION;
    private static final Identifier PORTAL_FRAME_TESTER = new Identifier(Main.MOD_ID, "warp_portal");

    public static void initialize() {
        CustomPortalApiRegistry.registerPortalFrameTester(PORTAL_FRAME_TESTER, ModFrameTester::new);

        Features.register();
        ConfiguredFeatures.register();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DIMENSION = server.getWorld(MUSHROOM_KINGDOM_WORLD);
            Main.LOGGER.info("Preparing dimension for server");

            if (DIMENSION == null) throw new AssertionError("Mario doesn't exist.");
        });

        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.WARP_FRAME)
                .customPortalBlock((CustomPortalBlock) ModBlocks.WARP_PORTAL)
                .lightWithItem(ModItems.POWER_STAR)
                .destDimID(WorldInit.MUSHROOM_KINGDOM)
                .tintColor(16744174)
                .customFrameTester(PORTAL_FRAME_TESTER)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(Sounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(Sounds.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
        CustomPortalBuilder.beginPortal().
                frameBlock(ModBlocks.CORRUPTED_WARP_FRAME)
                .customPortalBlock((CustomPortalBlock) ModBlocks.WARP_PORTAL)
                .lightWithItem(ModItems.ZTAR).destDimID(WorldInit.BOWSERS_KINGDOM)
                .tintColor(9967931)
                .customFrameTester(PORTAL_FRAME_TESTER)
                .registerInPortalAmbienceSound(playerEntity -> new CPASoundEventData(Sounds.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F))
                .registerPostTPPortalAmbience(playerEntity -> new CPASoundEventData(Sounds.BLOCK_DARK_PORTAL_TRAVEL, 1.0F, 1.0F))
                .registerPortal();
    }
}

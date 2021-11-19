package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class StructureKeys {
        public static RegistryKey<Biome> FOSSIL_FALLS;
        public static RegistryKey<ConfiguredStructureFeature<?, ?>> FOSSIL;
        public static RegistryKey<ConfiguredStructureFeature<?, ?>> SHIPWRECK;
        public static RegistryKey<ConfiguredStructureFeature<?, ?>> BEACHED_SHIPWRECK;
        public static RegistryKey<ConfiguredStructureFeature<?, ?>> WARP_PORTAL;
        public static RegistryKey<ConfiguredStructureFeature<?, ?>> TOAD_HOUSE;

        public static void registerStructureKeys() {
                FOSSIL_FALLS = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, "fossil_falls"));
                FOSSIL = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("nether_fossil"));
                SHIPWRECK = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck"));
                BEACHED_SHIPWRECK = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck_beached"));
                WARP_PORTAL = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "warp_portal"));
                TOAD_HOUSE = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "toad_house"));
        }
}

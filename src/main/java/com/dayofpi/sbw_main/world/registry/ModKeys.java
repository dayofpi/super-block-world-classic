package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class ModKeys {
    public static RegistryKey<Biome> BIOME_FOSSIL_FALLS;
    public static RegistryKey<Biome> BIOME_CHEEP_CHEEP_REEF;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_FOSSIL;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_SHIPWRECK;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_SHIPWRECK_BEACHED;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_WARP_PORTAL;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_TOAD_HOUSE;

    public static void registerKeys() {
        BIOME_FOSSIL_FALLS = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, "fossil_falls"));
        BIOME_CHEEP_CHEEP_REEF = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, "cheep_cheep_reef"));
        STRUCTURE_FOSSIL = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("nether_fossil"));
        STRUCTURE_SHIPWRECK = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck"));
        STRUCTURE_SHIPWRECK_BEACHED = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck_beached"));
        STRUCTURE_WARP_PORTAL = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "warp_portal"));
        STRUCTURE_TOAD_HOUSE = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "toad_house"));
    }
}

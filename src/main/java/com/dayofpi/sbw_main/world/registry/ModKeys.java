package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class ModKeys {
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_WARP_PORTAL;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_TOAD_HOUSE;

    public static void registerKeys() {
        STRUCTURE_WARP_PORTAL = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier(Main.MOD_ID, "warp_portal"));
        STRUCTURE_TOAD_HOUSE = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier(Main.MOD_ID, "toad_house"));
    }
}

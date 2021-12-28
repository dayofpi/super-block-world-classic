package com.dayofpi.super_block_world.main.registry.key;

import com.dayofpi.super_block_world.main.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class ModStructureKeys {
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> WARP_PORTAL;
    public static RegistryKey<ConfiguredStructureFeature<?, ?>> TOAD_HOUSE;

    public static void registerKeys() {
        WARP_PORTAL = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier(Main.MOD_ID, "warp_portal"));
        TOAD_HOUSE = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier(Main.MOD_ID, "toad_house"));
    }
}

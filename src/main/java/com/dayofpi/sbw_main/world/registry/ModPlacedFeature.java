package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeature {
    public static PlacedFeature register(String id, PlacedFeature feature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Main.MOD_ID, id), feature);
    }

    //public static final PlacedFeature DISK_SAND = register("disk_sand", FabricRegistryBuilder.(CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of()));
}

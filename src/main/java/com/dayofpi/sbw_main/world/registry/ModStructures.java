package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.world.structure_feature.ToadHouseFeature;
import com.dayofpi.sbw_main.world.structure_feature.WarpPortalFeature;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModStructures {
    private static final StructureFeature<DefaultFeatureConfig> WARP_PORTAL_FEATURE = new WarpPortalFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> WARP_PORTAL_CONFIGURED = WARP_PORTAL_FEATURE.configure(DefaultFeatureConfig.DEFAULT);

    private static final StructureFeature<DefaultFeatureConfig> TOAD_HOUSE_FEATURE = new ToadHouseFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> TOAD_HOUSE_CONFIGURED = TOAD_HOUSE_FEATURE.configure(DefaultFeatureConfig.DEFAULT);


    public static void registerStructures() {
        ModStructurePieces.registerStructurePiece();

        RegistryKey<ConfiguredStructureFeature<?, ?>> SHIPWRECK_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> FOSSIL_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("nether_fossil"));
        RegistryKey<Biome> FOSSIL_FALLS_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, "fossil_falls"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> BEACHED_SHIPWRECK_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck_beached"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> WARP_PORTAL_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "warp_portal"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> TOAD_HOUSE_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "toad_house"));

        // Warp Portal
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "warp_portal"), WARP_PORTAL_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(40, 11, 12345).register();
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, WARP_PORTAL_KEY.getValue(), WARP_PORTAL_CONFIGURED);

        // Toad House
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "toad_house"), TOAD_HOUSE_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(30, 10, 0).adjustsSurface().register();
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, TOAD_HOUSE_KEY.getValue(), TOAD_HOUSE_CONFIGURED);

        /*
        TODO: Wait for Biome API to update!
        BiomeModifications.addStructure(BiomeSelectors.foundInOverworld(), WARP_PORTAL_KEY);
        BiomeModifications.addStructure(BiomeSelectors.includeByKey(FOSSIL_FALLS_KEY), FOSSIL_KEY);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagList.AQUATIC), SHIPWRECK_KEY);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagList.AQUATIC), BEACHED_SHIPWRECK_KEY);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagList.SURFACE), TOAD_HOUSE_KEY);*/
    }
}

package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.TagList;
import com.dayofpi.sbw_main.world.structure_feature.ToadHouseFeature;
import com.dayofpi.sbw_main.world.structure_feature.ToadHouseGenerator;
import com.dayofpi.sbw_main.world.structure_feature.WarpPortalFeature;
import com.dayofpi.sbw_main.world.structure_feature.WarpPortalGenerator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

@SuppressWarnings("deprecation")
public class ModStructures {
    public static final StructurePieceType WARP_PORTAL_PIECE = WarpPortalGenerator.Piece::new;
    private static final StructureFeature<DefaultFeatureConfig> WARP_PORTAL_FEATURE = new WarpPortalFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> WARP_PORTAL_CONFIGURED = WARP_PORTAL_FEATURE.configure(DefaultFeatureConfig.DEFAULT);

    public static final StructurePieceType TOAD_HOUSE_PIECE = ToadHouseGenerator.Piece::new;
    private static final StructureFeature<DefaultFeatureConfig> TOAD_HOUSE_FEATURE = new ToadHouseFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> TOAD_HOUSE_CONFIGURED = TOAD_HOUSE_FEATURE.configure(DefaultFeatureConfig.DEFAULT);


    public static void registerStructures() {
        RegistryKey<ConfiguredStructureFeature<?, ?>> SHIPWRECK_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> BEACHED_SHIPWRECK_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("shipwreck_beached"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> WARP_PORTAL_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "warp_portal"));
        RegistryKey<ConfiguredStructureFeature<?, ?>> TOAD_HOUSE_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,new Identifier(Main.MOD_ID, "toad_house"));

        // Warp Portal
        Registry.register(Registry.STRUCTURE_PIECE, new Identifier(Main.MOD_ID, "warp_portal"), WARP_PORTAL_PIECE);
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "warp_portal"), WARP_PORTAL_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(40, 11, 12345).adjustsSurface().register();
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, WARP_PORTAL_KEY.getValue(), WARP_PORTAL_CONFIGURED);

        // Toad House
        Registry.register(Registry.STRUCTURE_PIECE, new Identifier(Main.MOD_ID, "toad_house"), TOAD_HOUSE_PIECE);
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "toad_house"), TOAD_HOUSE_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(30, 10, 0).adjustsSurface().register();
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, TOAD_HOUSE_KEY.getValue(), TOAD_HOUSE_CONFIGURED);


        BiomeModifications.addStructure(BiomeSelectors.foundInOverworld(), WARP_PORTAL_KEY);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagList.AQUATIC), SHIPWRECK_KEY);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagList.AQUATIC), BEACHED_SHIPWRECK_KEY);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagList.SURFACE), TOAD_HOUSE_KEY);

    }
}

package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.ModTags;
import com.dayofpi.sbw_main.world.structure_feature.ToadHouseFeature;
import com.dayofpi.sbw_main.world.structure_feature.WarpPortalFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import static net.fabricmc.fabric.api.biome.v1.BiomeModifications.addStructure;

public class ModStructures {
    private static final StructureFeature<StructurePoolFeatureConfig> WARP_PORTAL_FEATURE = new WarpPortalFeature(StructurePoolFeatureConfig.CODEC, 0, true, true, (arg) -> true);
    private static final StructureFeature<StructurePoolFeatureConfig> TOAD_HOUSE_FEATURE = new ToadHouseFeature(StructurePoolFeatureConfig.CODEC);

    public static void registerStructures() {
        StructureKeys.registerStructureKeys();
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "warp_portal"), WARP_PORTAL_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(30, 10, 12345).register();
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "toad_house"), TOAD_HOUSE_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(30, 10, 12345).adjustsSurface().register();

        addStructure(BiomeSelectors.foundInOverworld(), StructureKeys.WARP_PORTAL);
        addStructure(BiomeSelectors.includeByKey(StructureKeys.FOSSIL_FALLS), StructureKeys.FOSSIL);
        addStructure(BiomeSelectors.tag(ModTags.AQUATIC), StructureKeys.SHIPWRECK);
        addStructure(BiomeSelectors.tag(ModTags.AQUATIC), StructureKeys.BEACHED_SHIPWRECK);
        addStructure(BiomeSelectors.tag(ModTags.SURFACE), StructureKeys.TOAD_HOUSE);
    }
}

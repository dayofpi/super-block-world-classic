package com.dayofpi.super_block_world.main.registry.world.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.world.structure.types.ToadHouseFeature;
import com.dayofpi.super_block_world.main.common.world.structure.types.WarpPortalFeature;
import com.dayofpi.super_block_world.main.registry.misc.TagRegistry;
import com.dayofpi.super_block_world.main.registry.key.ModStructureKeys;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class StructureRegistry {
    private static final StructureFeature<StructurePoolFeatureConfig> WARP_PORTAL_FEATURE = new WarpPortalFeature(StructurePoolFeatureConfig.CODEC, true, false, (arg) -> true);
    private static final StructureFeature<StructurePoolFeatureConfig> TOAD_HOUSE_FEATURE = new ToadHouseFeature(StructurePoolFeatureConfig.CODEC);

    public static void registerStructures() {
        ModStructureKeys.registerKeys();
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "warp_portal"), WARP_PORTAL_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(30, 10, 12345).register();
        FabricStructureBuilder.create(new Identifier(Main.MOD_ID, "toad_house"), TOAD_HOUSE_FEATURE).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(30, 10, 12345).adjustsSurface().register();

        BiomeModifications.addStructure(BiomeSelectors.categories(Biome.Category.OCEAN).negate().and(BiomeSelectors.foundInOverworld()), ModStructureKeys.WARP_PORTAL);
        BiomeModifications.addStructure(BiomeSelectors.tag(TagRegistry.ALL_BIOMES), ModStructureKeys.TOAD_HOUSE);
    }
}

package com.dayofpi.super_block_world.world.biome;

import com.dayofpi.super_block_world.world.CustomCarvers;
import com.dayofpi.super_block_world.world.feature.placed.*;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class DefaultTemplates {

    static void addBasicFeatures(GenerationSettings.Builder builder) {
        addFluids(builder);
        addLandCarvers(builder);
        addOres(builder, false);
        addCaveContent(builder, false);
    }

    static void addIcyFeatures(GenerationSettings.Builder builder) {
        addFluids(builder);
        addLandCarvers(builder);
        addOres(builder, true);
        addCaveContent(builder, true);
    }

    static void addLandCarvers(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.CAVE);
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.CAVE_EXTRA_UNDERGROUND);
        builder.carver(GenerationStep.Carver.AIR, CustomCarvers.CANYON);
    }

    static void addFluids(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_LAVA);
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_POISON);
        builder.feature(GenerationStep.Feature.FLUID_SPRINGS, PlacedDecoration.SPRING_HIGH);
        builder.feature(GenerationStep.Feature.FLUID_SPRINGS, PlacedDecoration.SPRING_LOW);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.CORAL_FEW);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.SEAGRASS);
    }

    static void addPipes(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.GREEN_PIPE_PATCH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.RED_PIPE_PATCH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.UNDERWATER_PIPE);
    }

    static void addCaveContent(GenerationSettings.Builder builder, boolean icy) {
        addPipes(builder);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedDecoration.CAVE_DECORATION_COMMON);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedDecoration.CAVE_DECORATION_LOW);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedDecoration.CAVE_DECORATION_RARE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedDecoration.STAR_CRYSTAL);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedVegetation.VEGETATION_UNDERGROUND);
        if (icy) {
            builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_ICE);
            builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.ICICLE);
            builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedDecoration.FREEZIE);
            builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedPlants.FROZEN_MUNCHER);
        }
        else {
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.MUNCHER_FEW);
        }
    }

    static void addBlocks(GenerationSettings.Builder builder, boolean crystalDeep, boolean crystalSurface) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_PILE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, crystalSurface ? PlacedBlocks.BLOCK_LINE_SURFACE_CRYSTAL : PlacedBlocks.BLOCK_LINE_SURFACE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, crystalDeep ? PlacedBlocks.BLOCK_LINE_DEEP_CRYSTAL : PlacedBlocks.BLOCK_LINE_DEEP);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_LAVA);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_SINGLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, PlacedBlocks.JELLYBEAM_CAVES);
    }

    static void addOres(GenerationSettings.Builder builder, boolean icy) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SAND);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SEASTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CRUMBLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CERISE);
        if (icy) {
            builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedDecoration.FROSTING);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_HARDSTONE);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_FROZEN);
        } else {
            builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedDecoration.TOPPING);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_BRONZE);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_VANILLATE_COAL);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.TOPPING_AMETHYST);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_TOADSTONE);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_GLOOMSTONE);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_VANILLATE_IRON);
            builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.TOPPING_GOLD);
        }

    }
}

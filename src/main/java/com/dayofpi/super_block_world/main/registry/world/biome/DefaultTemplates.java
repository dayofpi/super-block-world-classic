package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.main.EntityInit;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;

public class DefaultTemplates {
    static void addCaveMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 1, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityInit.BUZZY_BEETLE, 80, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityInit.SPIKE_TOP, 40, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityInit.BOB_OMB, 40, 1, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityInit.THWOMP, 2, 1, 2));
        builder.spawnCost(EntityInit.THWOMP, 0.8, 0.12);

    }

    static void addBasicFeatures(GenerationSettings.Builder builder) {
        addFluids(builder);
        addLandCarvers(builder);
        addDefaultOres(builder);
        addDefaultBlocks(builder);
        addCaveContent(builder);
    }

    static void addIcyFeatures(GenerationSettings.Builder builder) {
        addFluids(builder);
        addLandCarvers(builder);
        addIcyBlocks(builder);
        addIcyOres(builder);
        addIcyCaveContent(builder);
    }

    static void addLandCarvers(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
    }

    static void addFluids(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_LAVA);
        builder.feature(GenerationStep.Feature.LAKES, PlacedDecoration.LAKE_POISON);
        builder.feature(GenerationStep.Feature.FLUID_SPRINGS, PlacedDecoration.SPRING);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.CORAL_FEW);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.SEAGRASS);
    }

    static void addPipes(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.PIPE_PATCH_HIGH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.PIPE_PATCH_LOW);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipes.UNDERWATER_PIPE);
    }

    static void addCaveContent(GenerationSettings.Builder builder) {
        addPipes(builder);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.STAR_CRYSTAL);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_COMMON);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_RARE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedVegetation.CAVE_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.MUNCHER_FEW);
    }

    static void addIcyCaveContent(GenerationSettings.Builder builder) {
        addPipes(builder);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.ICICLE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_COMMON);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_RARE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedVegetation.CAVE_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedPlants.FROZEN_MUNCHER);
    }

    static void addDefaultBlocks(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_PILE_PATCH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_SURFACE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_DEEP);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_SINGLE);
    }

    static void addIcyBlocks(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_PILE_PATCH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_SURFACE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_CRYSTAL);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_SINGLE);
    }

    static void addDefaultOres(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedDecoration.TOPPING);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SAND);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SEASTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CRUMBLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_BRONZE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_VANILLATE_COAL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_AMETHYST);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CERISE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_TOADSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_GLOOMSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.VANILLATE_IRON_ORE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.TOPPING_GOLD);
    }

    static void addIcyOres(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedDecoration.FROSTING);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SAND);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SEASTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CRUMBLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CERISE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_HARDSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_FROZEN);
    }
}

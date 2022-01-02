package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.misc.EntityRegistry;
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
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.BUZZY_BEETLE, 100, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.SPIKE_TOP, 50, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.BOB_OMB, 30, 1, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.THWOMP, 2, 1, 2));
        builder.spawnCost(EntityRegistry.THWOMP, 0.8, 0.12);

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
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.CORAL_FEW);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.SEAGRASS);
    }

    static void addPipes(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipe.PIPE_PATCH_HIGH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipe.PIPE_PATCH_LOW);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedPipe.UNDERWATER_PIPE);
    }

    static void addCaveContent(GenerationSettings.Builder builder) {
        addPipes(builder);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.STAR_CRYSTAL);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_COMMON);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_RARE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedVegetation.CAVE_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.MUNCHER_FEW);
    }

    static void addIcyCaveContent(GenerationSettings.Builder builder) {
        addPipes(builder);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.AMETHYST);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.ICICLE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_COMMON);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedDecoration.CAVE_DECORATION_RARE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedVegetation.CAVE_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.FROZEN_MUNCHER);
    }

    static void addDefaultBlocks(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_PILE_PATCH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_LINE_SURFACE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_LINE_DEEP);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_SINGLE);
    }

    static void addIcyBlocks(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_PILE_PATCH);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_LINE_SURFACE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_LINE_CRYSTAL);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlock.BLOCK_SINGLE);
    }

    static void addDefaultOres(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedDecoration.TOPPING);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.DISK_SAND);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.DISK_SEASTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_CRUMBLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_BRONZE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_VANILLATE_COAL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_AMETHYST);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_CERISE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_TOADSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_GLOOMSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.VANILLATE_IRON_ORE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.TOPPING_GOLD);
    }

    static void addIcyOres(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedDecoration.FROSTING);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.DISK_SAND);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.DISK_SEASTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_CRUMBLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_CERISE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_HARDSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOre.ORE_FROZEN);
    }
}

package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.EntityRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedBlocks;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedMisc;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedOres;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedVegetation;
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
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.HAMMER_BRO, 24, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.FIRE_BRO, 9, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.THWOMP, 1, 1, 2));
    }

    static void addBasicFeatures(GenerationSettings.Builder builder) {
        addFluids(builder);
        addLandCarvers(builder);
        addDefaultOres(builder);
        addCaveFeatures(builder);
    }

    static void addLandCarvers(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
    }

    static void addFluids(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LAKES, PlacedMisc.LAKE_LAVA);
        builder.feature(GenerationStep.Feature.LAKES, PlacedMisc.LAKE_POISON);
        builder.feature(GenerationStep.Feature.FLUID_SPRINGS, PlacedMisc.SPRING);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.CORAL_FEW);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.SEAGRASS);
    }

    static void addCaveFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedMisc.WARP_PIPE_WATER);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_SURFACE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_LINE_DEEP);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedBlocks.BLOCK_SINGLE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedMisc.AMETHYST);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedMisc.CAVE_DECORATION_COMMON);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedMisc.CAVE_DECORATION_RARE);
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedVegetation.CAVE_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedVegetation.MUNCHER_FEW);
    }

    static void addDefaultOres(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.RAW_GENERATION, PlacedMisc.VANILLATE_TOPPING);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.DISK_SAND);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CRUMBLE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_BRONZE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_AMETHYST);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_CERISE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.ORE_HARDSTONE);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.TOPPING_COAL);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.TOPPING_IRON);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, PlacedOres.TOPPING_GOLD);
    }
}

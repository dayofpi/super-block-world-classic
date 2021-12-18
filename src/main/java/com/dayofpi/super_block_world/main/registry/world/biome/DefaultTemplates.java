package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.EntityRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedMisc;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedOres;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.PlacedVegetation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

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
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    static void addLandCarvers(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
    }

    static void addFluids(GenerationSettings.Builder builder) {
        builder.feature(9, () -> PlacedMisc.SPRING);
        builder.feature(1, () -> PlacedMisc.LAKE_LAVA);
        builder.feature(1, () -> PlacedMisc.LAKE_POISON);
        builder.feature(10, () -> PlacedVegetation.CORAL_FEW);
        builder.feature(10, () -> PlacedVegetation.SEAGRASS);
    }

    static void addCaveFeatures(GenerationSettings.Builder builder) {
        builder.feature(2, () -> PlacedMisc.WARP_PIPE_WATER);
        //builder.feature(2, () -> PlacedBlocks.BLOCK_LINE_SURFACE);
        //builder.feature(2, () -> PlacedBlocks.BLOCK_LINE_DEEP);
        //  builder.feature(2, () -> PlacedBlocks.BLOCK_SINGLE);
        builder.feature(2, () -> PlacedMisc.AMETHYST);
        builder.feature(2, () -> PlacedMisc.CAVE_DECORATION);
        builder.feature(2, () -> PlacedVegetation.CAVE_VEGETATION);
        builder.feature(10, () -> PlacedVegetation.MUNCHER_FEW);
    }

    static void addDefaultOres(GenerationSettings.Builder builder) {
        builder.feature(6, () -> PlacedMisc.VANILLATE_TOPPING);
        builder.feature(7, () -> PlacedOres.DISK_SAND);
        builder.feature(7, () -> PlacedOres.ORE_CRUMBLE);
        builder.feature(7, () -> PlacedOres.ORE_BRONZE);
        builder.feature(7, () -> PlacedOres.ORE_AMETHYST);
        builder.feature(7, () -> PlacedOres.ORE_CERISE);
        builder.feature(7, () -> PlacedOres.ORE_HARDSTONE);
        builder.feature(7, () -> PlacedOres.TOPPING_COAL);
        builder.feature(7, () -> PlacedOres.TOPPING_IRON);
        builder.feature(7, () -> PlacedOres.TOPPING_GOLD);
    }
}

package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.entity.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;

public class ModBiomeBuilder {

    public static void addLandCarvers(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
        builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
        builder.feature(GenerationStep.Feature.LAKES, ModPlacedFeature.LAKE_LAVA);
        builder.feature(GenerationStep.Feature.LAKES, ModPlacedFeature.LAKE_POISON);
    }

    static void addCaveMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.BUZZY_BEETLE, 100, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.SPIKE_TOP, 50, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.BOB_OMB, 30, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.HAMMER_BRO, 25, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.THWOMP, 1, 1, 1));
    }

    static void addMushroomGrasslandMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 90, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.MOO_MOO, 10, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
    }
}

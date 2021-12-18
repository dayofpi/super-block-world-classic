package com.dayofpi.super_block_world.main.registry.world.biome;

import com.dayofpi.super_block_world.main.registry.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;

public class SpawnTemplates {
    static void addMushroomGrasslandMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.MOO_MOO, 10, 2, 2));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARAGOOMBA, 5, 2, 4));
        builder.spawnCost(EntityRegistry.GOOMBA, 1.0, 0.12);
    }

    static void addAmanitaForestMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.ROTTEN_MUSHROOM, 10, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARAGOOMBA, 10, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 2, 2, 2));
        builder.spawnCost(EntityRegistry.GOOMBA, 1.0, 0.12);
    }

    static void addAutumnForestMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.STINGBY, 40, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARAGOOMBA, 5, 2, 4));
        builder.spawnCost(EntityRegistry.GOOMBA, 1.0, 0.12);
        builder.spawnCost(EntityRegistry.STINGBY, 1.0, 0.12);
    }

    static void addForestOfIllusionMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.ROTTEN_MUSHROOM, 10, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.BOO, 10, 1, 1));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARAGOOMBA, 10, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 2, 2, 2));
        builder.spawnCost(EntityRegistry.GOOMBA, 1.0, 0.12);
        builder.spawnCost(EntityRegistry.BOO, 1.0, 0.12);
    }

    static void addCheepCheepReefMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 1, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 18, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.PARAGOOMBA, 5, 2, 4));
        builder.spawnCost(EntityRegistry.GOOMBA, 1.0, 0.12);
    }

    static void addMushroomGorgeMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
    }

    static void addMooMooMeadowMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.MOO_MOO, 10, 2, 2));
    }
}

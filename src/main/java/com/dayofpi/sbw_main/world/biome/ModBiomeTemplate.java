package com.dayofpi.sbw_main.world.biome;

import com.dayofpi.sbw_main.registry.entity.ModEntities;
import com.dayofpi.sbw_main.world.feature.registry.ModPlacedFeature;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class ModBiomeTemplate {

    public static final ConfiguredCarver<RavineCarverConfig> WIDE_CANYON = registerCarver("super_block_world:wide_canyon", Carver.RAVINE.configure(new RavineCarverConfig(
            0.05f,
            UniformHeightProvider.create(YOffset.fixed(63), YOffset.fixed(90)),
            ConstantFloatProvider.create(3.0f), YOffset.aboveBottom(8),
            CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()),
            UniformFloatProvider.create(-0.125f, 0.125f),
            new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75f, 1.0f),
                    UniformFloatProvider.create(6.0f, 8.0f),
                    3, UniformFloatProvider.create(0.75f, 1.0f),
                    1.0f, 0.0f))));

    private static <WC extends CarverConfig> ConfiguredCarver<WC> registerCarver(String id, ConfiguredCarver<WC> configuredCarver) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, id, configuredCarver);
    }

    static void addMushroomGrasslandMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.MOO_MOO, 10, 2, 2));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 5, 2, 4));
        builder.spawnCost(ModEntities.GOOMBA, 1.0, 0.12);
    }

    static void addAmanitaForestMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.ROTTEN_MUSHROOM, 10, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 10, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 2, 2, 2));
        builder.spawnCost(ModEntities.GOOMBA, 1.0, 0.12);
    }

    static void addAutumnForestMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.STINGBY, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 5, 2, 4));
        builder.spawnCost(ModEntities.GOOMBA, 1.0, 0.12);
    }

    static void addForestOfIllusionMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.ROTTEN_MUSHROOM, 10, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.BOO, 10, 1, 1));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 10, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 2, 2, 2));
        builder.spawnCost(ModEntities.GOOMBA, 1.0, 0.12);
        builder.spawnCost(ModEntities.BOO, 1.0, 0.12);
    }

    static void addCheepCheeReefMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 18, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.KOOPA_TROOPA, 18, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARATROOPA, 5, 2, 4));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.GOOMBA, 40, 1, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.PARAGOOMBA, 5, 2, 4));
        builder.spawnCost(ModEntities.GOOMBA, 1.0, 0.12);
    }

    static void addMushroomGorgeMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 80, 2, 4));
    }

    static void addMooMooMeadowMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.MOO_MOO, 10, 2, 2));
    }

    static void addCaveMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 1, 2, 2));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.BUZZY_BEETLE, 100, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.SPIKE_TOP, 50, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.BOB_OMB, 30, 2, 3));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.HAMMER_BRO, 25, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.FIRE_BRO, 10, 2, 2));
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.THWOMP, 2, 1, 2));
    }

    static void addMushroomGrasslandsFeatures(GenerationSettings.Builder builder) {
        builder.feature(10, () -> ModPlacedFeature.HORSETAIL);
        builder.feature(10, () -> ModPlacedFeature.STUMP);
        builder.feature(10, () -> ModPlacedFeature.PLANT_GRASSLAND);
        builder.feature(10, () -> ModPlacedFeature.FLOWER_GRASSLAND);
        builder.feature(10, () -> ModPlacedFeature.MUSHROOM_GRASSLAND);
        builder.feature(10, () -> ModPlacedFeature.VEGETATION_GRASSLAND);
    }

    static void addMushroomGorgeFeatures(GenerationSettings.Builder builder) {
        builder.carver(GenerationStep.Carver.AIR, WIDE_CANYON);
        builder.feature(2, () -> ModPlacedFeature.AMETHYST_EXTRA);
        builder.feature(10, () -> ModPlacedFeature.STUMP);
        builder.feature(10, () -> ModPlacedFeature.PLANT_GORGE);
        builder.feature(10, () -> ModPlacedFeature.FLOWER_GORGE);
        builder.feature(10, () -> ModPlacedFeature.VEGETATION_GORGE);
        builder.feature(10, () -> ModPlacedFeature.VEGETATION_GORGE_TALL);
    }

    static void addDryDryDesertFeatures(GenerationSettings.Builder builder) {
        builder.feature(6, () -> ModPlacedFeature.QUICKSAND);
        builder.feature(10, () -> ModPlacedFeature.CACTUS);
        builder.feature(10, () -> ModPlacedFeature.PLANT_DESERT);
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
        builder.feature(9, () -> ModPlacedFeature.SPRING);
        builder.feature(1, () -> ModPlacedFeature.LAKE_LAVA);
        builder.feature(1, () -> ModPlacedFeature.LAKE_POISON);
        builder.feature(10, () -> ModPlacedFeature.CORAL_FEW);
        builder.feature(10, () -> ModPlacedFeature.SEAGRASS);
    }

    static void addCaveFeatures(GenerationSettings.Builder builder) {
        builder.feature(2, () -> ModPlacedFeature.WARP_PIPE_WATER);
        builder.feature(2, () -> ModPlacedFeature.BLOCK_LINE_SURFACE);
        builder.feature(2, () -> ModPlacedFeature.BLOCK_LINE_DEEP);
        builder.feature(2, () -> ModPlacedFeature.BLOCK_SINGLE);
        builder.feature(2, () -> ModPlacedFeature.AMETHYST);
        builder.feature(2, () -> ModPlacedFeature.CAVE_DECORATION);
        builder.feature(2, () -> ModPlacedFeature.GIANT_CAVE_MUSHROOM);
        builder.feature(10, () -> ModPlacedFeature.MUNCHER_FEW);
    }

    static void addDefaultOres(GenerationSettings.Builder builder) {
        builder.feature(6, () -> ModPlacedFeature.VANILLATE_TOPPING);
        builder.feature(7, () -> ModPlacedFeature.DISK_SAND);
        builder.feature(7, () -> ModPlacedFeature.ORE_CRUMBLE);
        builder.feature(7, () -> ModPlacedFeature.ORE_BRONZE);
        builder.feature(7, () -> ModPlacedFeature.ORE_AMETHYST);
        builder.feature(7, () -> ModPlacedFeature.ORE_CERISE);
        builder.feature(7, () -> ModPlacedFeature.ORE_HARDSTONE);
        builder.feature(7, () -> ModPlacedFeature.TOPPING_COAL);
        builder.feature(7, () -> ModPlacedFeature.TOPPING_IRON);
        builder.feature(7, () -> ModPlacedFeature.TOPPING_GOLD);
    }




}

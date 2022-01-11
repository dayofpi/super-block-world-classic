package com.dayofpi.super_block_world.mixin.main.world;

import com.dayofpi.super_block_world.registry.world.feature.configured.ConfiguredTrees;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@SuppressWarnings("SameParameterValue")
@Mixin(OverworldBiomeCreator.class)
public class ReplaceBiomeFeatures {
    @Invoker("getSkyColor")
    private static int getSkyColor(float temperature) {
        throw new AssertionError();
    }

    @Invoker("createBiome")
    private static Biome createBiome(Biome.Precipitation precipitation, Biome.Category category, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
        throw new AssertionError();
    }

    private static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    private static final ConfiguredFeature<RandomBooleanFeatureConfig, ?> MUSHROOM_ISLAND_SELECTOR = ConfiguredFeatures.register("super_bock_world:mushroom_island_vegetation", Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(ConfiguredTrees.HUGE_RED_MUSHROOM::withPlacement, ConfiguredTrees.HUGE_BROWN_MUSHROOM::withPlacement)));
    private static final ConfiguredFeature<RandomFeatureConfig, ?> DARK_FOREST_SELECTOR = ConfiguredFeatures.register("super_bock_world:dark_forest_vegetation", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredTrees.HUGE_BROWN_MUSHROOM.withPlacement(), 0.025f), new RandomFeatureEntry(ConfiguredTrees.HUGE_RED_MUSHROOM.withPlacement(), 0.05f), new RandomFeatureEntry(TreePlacedFeatures.DARK_OAK_CHECKED, 0.6666667f), new RandomFeatureEntry(TreePlacedFeatures.BIRCH_CHECKED, 0.2f), new RandomFeatureEntry(TreePlacedFeatures.FANCY_OAK_CHECKED, 0.1f)), TreePlacedFeatures.OAK_CHECKED)));

    private static final PlacedFeature MUSHROOM_ISLAND_VEGETATION = PlacedFeatures.register("super_bock_world:mushroom_island_vegetation", MUSHROOM_ISLAND_SELECTOR.withPlacement(SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    private static final PlacedFeature DARK_FOREST_VEGETATION = PlacedFeatures.register("super_bock_world:dark_forest_vegetation", DARK_FOREST_SELECTOR.withPlacement(CountPlacementModifier.of(16), SquarePlacementModifier.of(), NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));

    @Inject(at=@At("HEAD"), method = "createDarkForest", cancellable = true)
    private static void createDarkForest(CallbackInfoReturnable<Biome> info) {
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawn);
        DefaultBiomeFeatures.addBatsAndMonsters(spawn);
        GenerationSettings.Builder gen = new GenerationSettings.Builder();
        addBasicFeatures(gen);
        gen.feature(GenerationStep.Feature.VEGETAL_DECORATION, DARK_FOREST_VEGETATION);
        DefaultBiomeFeatures.addForestFlowers(gen);
        DefaultBiomeFeatures.addDefaultOres(gen);
        DefaultBiomeFeatures.addDefaultDisks(gen);
        DefaultBiomeFeatures.addDefaultFlowers(gen);
        DefaultBiomeFeatures.addForestGrass(gen);
        DefaultBiomeFeatures.addDefaultMushrooms(gen);
        DefaultBiomeFeatures.addDefaultVegetation(gen);
        info.setReturnValue(new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).temperature(0.7f).downfall(0.8f).effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.7f)).grassColorModifier(BiomeEffects.GrassColorModifier.DARK_FOREST).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawn.build()).generationSettings(gen.build()).build());
    }

    @Inject(at=@At("HEAD"), method = "createMushroomFields", cancellable = true)
    private static void createMushroomFields(CallbackInfoReturnable<Biome> info) {
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addMushroomMobs(spawn);
        GenerationSettings.Builder gen = new GenerationSettings.Builder();
        addBasicFeatures(gen);
        DefaultBiomeFeatures.addDefaultOres(gen);
        DefaultBiomeFeatures.addDefaultDisks(gen);
        addMushroomFieldsFeatures(gen);
        DefaultBiomeFeatures.addDefaultVegetation(gen);
        info.setReturnValue(createBiome(Biome.Precipitation.RAIN, Biome.Category.MUSHROOM, 0.9f, 1.0f, spawn, gen, null));
    }

    private static void addMushroomFieldsFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, MUSHROOM_ISLAND_VEGETATION);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.BROWN_MUSHROOM_TAIGA);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.RED_MUSHROOM_TAIGA);
    }

    private static void addBasicFeatures(GenerationSettings.Builder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }
}

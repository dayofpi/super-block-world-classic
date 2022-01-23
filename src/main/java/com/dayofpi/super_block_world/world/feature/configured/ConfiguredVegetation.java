package com.dayofpi.super_block_world.world.feature.configured;

import com.dayofpi.super_block_world.world.feature.placed.PlacedTrees;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureEntry;

import java.util.List;

public class ConfiguredVegetation {
    public static final ConfiguredFeature<?, ?> VEGETATION_GRASSLAND = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredPlants.FLOWERBED.withPlacement(), 0.42F), new RandomFeatureEntry(ConfiguredTrees.BEANSTALK_BUDDING.withPlacement(),0.05F), new RandomFeatureEntry(PlacedTrees.HUGE_BROWN_MUSHROOM, 0.075F),new RandomFeatureEntry(PlacedTrees.HUGE_RED_MUSHROOM, 0.075F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.AMANITA));
    public static final ConfiguredFeature<?, ?> VEGETATION_MEADOW = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredPlants.FLOWERBED.withPlacement(), 0.42F), new RandomFeatureEntry(ConfiguredTrees.BEANSTALK_BUDDING.withPlacement(),0.05F), new RandomFeatureEntry(PlacedTrees.HUGE_BROWN_MUSHROOM, 0.15F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.AMANITA));
    public static final ConfiguredFeature<?, ?> VEGETATION_SHERBET = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.AMANITA_MOUNTAIN, 0.42F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.AMANITA));
    public static final ConfiguredFeature<?, ?> VEGETATION_GORGE = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.HUGE_GREEN_MUSHROOM_FLAT, 0.5F), new RandomFeatureEntry(PlacedTrees.HUGE_RED_MUSHROOM_FLAT, 0.5F)), PlacedTrees.AMANITA_MOUNTAIN));
    public static final ConfiguredFeature<?, ?> VEGETATION_GORGE_CANYON = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.HUGE_GREEN_MUSHROOM_CANYON, 0.5F)),PlacedTrees.HUGE_RED_MUSHROOM_CANYON));
    public static final ConfiguredFeature<?, ?> VEGETATION_REEF = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(ConfiguredPlants.FLOWERBED.withPlacement(), 0.45F), new RandomFeatureEntry(PlacedTrees.AMANITA, 0.4F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.BELL_PINE));
    public static final ConfiguredFeature<?, ?> VEGETATION_AMANITA = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.BEANSTALK_BUDDING, 0.03F), new RandomFeatureEntry(PlacedTrees.BEANSTALK,0.025F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.5F), new RandomFeatureEntry(PlacedTrees.AMANITA, 0.2F)), PlacedTrees.AMANITA));
    public static final ConfiguredFeature<?, ?> VEGETATION_ILLUSION = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.BEANSTALK_BUDDING, 0.03F), new RandomFeatureEntry(PlacedTrees.HUGE_PURPLE_MUSHROOM,0.025F), new RandomFeatureEntry(PlacedTrees.DARK_AMANITA, 0.3F)), PlacedTrees.DARK_AMANITA_TALL));
    public static final ConfiguredFeature<?, ?> VEGETATION_AUTUMN = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.AMANITA_LARGE, 0.4F), new RandomFeatureEntry(PlacedTrees.AMANITA_OAKY, 0.4F), new RandomFeatureEntry(PlacedTrees.AMANITA_FRUIT, 0.4F)), PlacedTrees.AMANITA));
    public static final ConfiguredFeature<?, ?> VEGETATION_AUTUMN_MOUNTAIN = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.HUGE_ORANGE_MUSHROOM, 0.4F)), PlacedTrees.DARK_AMANITA_MOUNTAIN));
    public static final ConfiguredFeature<?, ?> VEGETATION_UNDERGROUND = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedTrees.BEANSTALK_UNDERGROUND, 0.25F), new RandomFeatureEntry(PlacedTrees.BEANSTALK_BUDDING_UNDERGROUND, 0.25F), new RandomFeatureEntry(PlacedTrees.HUGE_GREEN_MUSHROOM_FLAT, 0.5F)), PlacedTrees.HUGE_ORANGE_MUSHROOM));
}

package com.dayofpi.super_block_world.world.feature;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.world.feature.configured.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredFeatureInit {
    private static void registerConfiguredFeature(String id, ConfiguredFeature<?, ?> type) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, id), type);
    }

    public static void registerConfiguredFeatures() {
        registerConfiguredFeature("spring_high", ConfiguredDecoration.  SPRING_HIGH);
        registerConfiguredFeature("spring_low", ConfiguredDecoration.SPRING_LOW);
        registerConfiguredFeature("lake_lava", ConfiguredDecoration.LAKE_LAVA);
        registerConfiguredFeature("lake_ice", ConfiguredDecoration.LAKE_ICE);
        registerConfiguredFeature("lake_poison", ConfiguredDecoration.LAKE_POISON);
        registerConfiguredFeature("amethyst_ceiling", ConfiguredDecoration.AMETHYST_CEILING);
        registerConfiguredFeature("amethyst_floor", ConfiguredDecoration.AMETHYST_FLOOR);
        registerConfiguredFeature("star_crystal_ceiling", ConfiguredDecoration.STAR_CRYSTAL_CEILING);
        registerConfiguredFeature("star_crystal_floor", ConfiguredDecoration.STAR_CRYSTAL_FLOOR);
        registerConfiguredFeature("icicle", ConfiguredDecoration.ICICLE);
        registerConfiguredFeature("freezie", ConfiguredDecoration.FREEZIE);
        registerConfiguredFeature("cave_decoration_common", ConfiguredDecoration.CAVE_DECORATION_COMMON);
        registerConfiguredFeature("cave_decoration_rare", ConfiguredDecoration.CAVE_DECORATION_RARE);
        registerConfiguredFeature("cave_decoration_low", ConfiguredDecoration.CAVE_DECORATION_LOW);
        registerConfiguredFeature("jellybeam", ConfiguredBlocks.JELLYBEAM);
        registerConfiguredFeature("block_single", ConfiguredBlocks.BLOCK_SINGLE);
        registerConfiguredFeature("block_line", ConfiguredBlocks.BLOCK_LINE_SURFACE);
        registerConfiguredFeature("block_line_deep", ConfiguredBlocks.BLOCK_LINE_DEEP);
        registerConfiguredFeature("block_line_crystal", ConfiguredBlocks.BLOCK_LINE_CRYSTAL);
        registerConfiguredFeature("block_line_lava", ConfiguredBlocks.BLOCK_LINE_LAVA);
        registerConfiguredFeature("block_pile", ConfiguredBlocks.BLOCK_PILE);
        registerConfiguredFeature("green_pipe_patch", ConfiguredPipes.GREEN_PIPE_PATCH);
        registerConfiguredFeature("red_pipe_patch", ConfiguredPipes.RED_PIPE_PATCH);
        registerConfiguredFeature("topping", ConfiguredDecoration.TOPPING);
        registerConfiguredFeature("frosting", ConfiguredDecoration.FROSTING);
        registerConfiguredFeature("quicksand", ConfiguredDecoration.QUICKSAND);
        registerConfiguredFeature("ore_crumble", ConfiguredOres.ORE_CRUMBLE);
        registerConfiguredFeature("ore_bronze", ConfiguredOres.ORE_BRONZE);
        registerConfiguredFeature("ore_vanillate_coal", ConfiguredOres.ORE_VANILLATE_COAL);
        registerConfiguredFeature("ore_vanillate_iron", ConfiguredOres.ORE_VANILLATE_IRON);
        registerConfiguredFeature("ore_cerise", ConfiguredOres.ORE_CERISE);
        registerConfiguredFeature("ore_toadstone", ConfiguredOres.ORE_TOADSTONE);
        registerConfiguredFeature("ore_chiseled_toadstone", ConfiguredOres.ORE_CHISELED_TOADSTONE);
        registerConfiguredFeature("ore_gloomstone", ConfiguredOres.ORE_GLOOMSTONE);
        registerConfiguredFeature("ore_hardstone", ConfiguredOres.ORE_HARDSTONE);
        registerConfiguredFeature("ore_frozen", ConfiguredOres.ORE_FROZEN);
        registerConfiguredFeature("topping_gold", ConfiguredOres.TOPPING_GOLD);
        registerConfiguredFeature("topping_amethyst", ConfiguredOres.TOPPING_AMETHYST);
        registerConfiguredFeature("disk_sand", ConfiguredOres.DISK_SAND);
        registerConfiguredFeature("disk_seastone", ConfiguredOres.DISK_SEASTONE);
        registerConfiguredFeature("horsetail_patch", ConfiguredPlants.HORSETAIL_PATCH);
        registerConfiguredFeature("stump_patch", ConfiguredPlants.STUMP_PATCH);
        registerConfiguredFeature("beanstalk", ConfiguredTrees.BEANSTALK);
        registerConfiguredFeature("beanstalk_budding", ConfiguredTrees.BEANSTALK_BUDDING);
        registerConfiguredFeature("beanstalk_underground", ConfiguredTrees.BEANSTALK_UNDERGROUND);
        registerConfiguredFeature("beanstalk_budding_underground", ConfiguredTrees.BEANSTALK_BUDDING_UNDERGROUND);
        registerConfiguredFeature("cactus_patch", ConfiguredPlants.CACTUS_PATCH);
        registerConfiguredFeature("muncher", ConfiguredPlants.MUNCHER);
        registerConfiguredFeature("frozen_muncher", ConfiguredPlants.FROZEN_MUNCHER);
        registerConfiguredFeature("flowerbed", ConfiguredPlants.FLOWERBED);
        registerConfiguredFeature("tall_grass_patch", ConfiguredPlants.TALL_GRASS_PATCH);

        registerConfiguredFeature("amanita_carpet", ConfiguredPlants.AMANITA_CARPET);
        registerConfiguredFeature("amanita", ConfiguredTrees.AMANITA);
        registerConfiguredFeature("amanita_fruit", ConfiguredTrees.AMANITA_FRUIT);
        registerConfiguredFeature("amanita_large", ConfiguredTrees.AMANITA_LARGE);
        registerConfiguredFeature("amanita_mountain", ConfiguredTrees.AMANITA_MOUNTAIN);
        registerConfiguredFeature("amanita_oaky", ConfiguredTrees.AMANITA_OAKY);
        registerConfiguredFeature("amanita_oaky_fruit", ConfiguredTrees.AMANITA_OAKY_FRUIT);

        registerConfiguredFeature("dark_amanita", ConfiguredTrees.DARK_AMANITA);
        registerConfiguredFeature("dark_amanita_tall", ConfiguredTrees.DARK_AMANITA_TALL);
        registerConfiguredFeature("dark_amanita_mountain", ConfiguredTrees.DARK_AMANITA_MOUNTAIN);

        registerConfiguredFeature("bell_pine", ConfiguredTrees.BELL_TREE);

        registerConfiguredFeature("huge_red_mushroom_flat", ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_green_mushroom_flat", ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_red_mushroom_canyon", ConfiguredTrees.HUGE_RED_MUSHROOM_CANYON);
        registerConfiguredFeature("huge_green_mushroom_canyon", ConfiguredTrees.HUGE_GREEN_MUSHROOM_CANYON);

        registerConfiguredFeature("huge_yellow_mushroom", ConfiguredTrees.HUGE_YELLOW_MUSHROOM);
        registerConfiguredFeature("huge_green_mushroom", ConfiguredTrees.HUGE_GREEN_MUSHROOM);
        registerConfiguredFeature("huge_pink_mushroom", ConfiguredTrees.HUGE_PINK_MUSHROOM);
        registerConfiguredFeature("huge_purple_mushroom", ConfiguredTrees.HUGE_PURPLE_MUSHROOM);
        registerConfiguredFeature("huge_orange_mushroom", ConfiguredTrees.HUGE_ORANGE_MUSHROOM);
        registerConfiguredFeature("huge_brown_mushroom", ConfiguredTrees.HUGE_BROWN_MUSHROOM);
        registerConfiguredFeature("huge_red_mushroom", ConfiguredTrees.HUGE_RED_MUSHROOM);

        registerConfiguredFeature("piranha_lily", ConfiguredPlants.PIRANHA_LILY);
        registerConfiguredFeature("pit_plant", ConfiguredPlants.PIT_PLANT);
        registerConfiguredFeature("fuzzbush", ConfiguredPlants.FUZZBUSH);
        registerConfiguredFeature("plants_water_surface", ConfiguredPlants.PLANTS_WATER_SURFACE);

        registerConfiguredFeature("plants_grassland", ConfiguredPlants.PLANTS_GRASSLAND);
        registerConfiguredFeature("plants_desert", ConfiguredPlants.PLANTS_DESERT);
        registerConfiguredFeature("plants_autumn", ConfiguredPlants.PLANTS_AUTUMN);
        registerConfiguredFeature("plants_gorge", ConfiguredPlants.PLANTS_GORGE);
        registerConfiguredFeature("plants_fossil", ConfiguredPlants.PLANTS_FOSSIL);

        registerConfiguredFeature("mushrooms_grassland", ConfiguredPlants.MUSHROOMS_GRASSLAND);
        registerConfiguredFeature("mushrooms_mountain", ConfiguredPlants.MUSHROOMS_MOUNTAIN);
        registerConfiguredFeature("mushrooms_amanita", ConfiguredPlants.MUSHROOMS_AMANITA);
        registerConfiguredFeature("mushrooms_autumn", ConfiguredPlants.MUSHROOMS_AUTUMN);

        registerConfiguredFeature("flowers_grassland", ConfiguredPlants.FLOWERS_GRASSLAND);
        registerConfiguredFeature("flowers_illusion", ConfiguredPlants.FLOWERS_ILLUSION);
        registerConfiguredFeature("flowers_reef", ConfiguredPlants.FLOWERS_REEF);
        registerConfiguredFeature("flowers_gorge", ConfiguredPlants.FLOWERS_GORGE);
        registerConfiguredFeature("flowers_fossil", ConfiguredPlants.FLOWERS_FOSSIL);

        registerConfiguredFeature("vegetation_grassland", ConfiguredVegetation.VEGETATION_GRASSLAND);
        registerConfiguredFeature("vegetation_meadow", ConfiguredVegetation.VEGETATION_MEADOW);
        registerConfiguredFeature("vegetation_sherbet", ConfiguredVegetation.VEGETATION_SHERBET);
        registerConfiguredFeature("vegetation_reef", ConfiguredVegetation.VEGETATION_REEF);
        registerConfiguredFeature("vegetation_amanita", ConfiguredVegetation.VEGETATION_AMANITA);
        registerConfiguredFeature("vegetation_illusion", ConfiguredVegetation.VEGETATION_ILLUSION);
        registerConfiguredFeature("vegetation_autumn", ConfiguredVegetation.VEGETATION_AUTUMN);
        registerConfiguredFeature("vegetation_autumn_mountain", ConfiguredVegetation.VEGETATION_AUTUMN_MOUNTAIN);
        registerConfiguredFeature("vegetation_gorge", ConfiguredVegetation.VEGETATION_GORGE);
        registerConfiguredFeature("vegetation_gorge_canyon", ConfiguredVegetation.VEGETATION_GORGE_CANYON);
        registerConfiguredFeature("vegetation_underground", ConfiguredVegetation.VEGETATION_UNDERGROUND);
    }
}

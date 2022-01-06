package com.dayofpi.super_block_world.main.registry.world.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredFeatureRegistry {
    private static void registerConfiguredFeature(String id, ConfiguredFeature<?, ?> type) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, id), type);
    }

    public static void registerConfiguredFeatures() {
        registerConfiguredFeature("spring", ConfiguredDecoration.SPRING);
        registerConfiguredFeature("lake_lava", ConfiguredDecoration.LAKE_LAVA);
        registerConfiguredFeature("lake_poison", ConfiguredDecoration.LAKE_POISON);
        registerConfiguredFeature("amethyst_ceiling", ConfiguredDecoration.AMETHYST_CEILING);
        registerConfiguredFeature("amethyst_floor", ConfiguredDecoration.AMETHYST_FLOOR);
        registerConfiguredFeature("star_crystal_ceiling", ConfiguredDecoration.STAR_CRYSTAL_CEILING);
        registerConfiguredFeature("star_crystal_floor", ConfiguredDecoration.STAR_CRYSTAL_FLOOR);
        registerConfiguredFeature("icicle", ConfiguredDecoration.ICICLE);
        registerConfiguredFeature("cave_decoration_common", ConfiguredDecoration.CAVE_DECORATION_COMMON);
        registerConfiguredFeature("cave_decoration_rare", ConfiguredDecoration.CAVE_DECORATION_RARE);
        registerConfiguredFeature("jellybeam", ConfiguredBlocks.JELLYBEAM);
        registerConfiguredFeature("block_single", ConfiguredBlocks.BLOCK_SINGLE);
        registerConfiguredFeature("block_line", ConfiguredBlocks.BLOCK_LINE_SURFACE);
        registerConfiguredFeature("block_line_deep", ConfiguredBlocks.BLOCK_LINE_DEEP);
        registerConfiguredFeature("block_line_crystal", ConfiguredBlocks.BLOCK_LINE_CRYSTAL);
        registerConfiguredFeature("block_pile", ConfiguredBlocks.BLOCK_PILE);
        registerConfiguredFeature("pipe_patch", ConfiguredPipes.PIPE_PATCH);
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
        registerConfiguredFeature("horsetail", ConfiguredPlants.HORSETAIL);
        registerConfiguredFeature("horsetail_patch", ConfiguredPlants.HORSETAIL_PATCH);
        registerConfiguredFeature("stump", ConfiguredPlants.STUMP);
        registerConfiguredFeature("stump_patch", ConfiguredPlants.STUMP_PATCH);
        registerConfiguredFeature("beanstalk", ConfiguredTrees.BEANSTALK);
        registerConfiguredFeature("beanstalk_budding", ConfiguredTrees.BEANSTALK_BUDDING);
        registerConfiguredFeature("beanstalk_underground", ConfiguredTrees.BEANSTALK_UNDERGROUND);
        registerConfiguredFeature("beanstalk_budding_underground", ConfiguredTrees.BEANSTALK_BUDDING_UNDERGROUND);
        registerConfiguredFeature("cactus", ConfiguredPlants.CACTUS);
        registerConfiguredFeature("cactus_patch", ConfiguredPlants.CACTUS_PATCH);
        registerConfiguredFeature("muncher", ConfiguredPlants.MUNCHER);
        registerConfiguredFeature("frozen_muncher", ConfiguredPlants.FROZEN_MUNCHER);
        registerConfiguredFeature("flowerbed", ConfiguredPlants.FLOWERBED);
        registerConfiguredFeature("fuzzbush", ConfiguredPlants.FUZZBUSH);
        registerConfiguredFeature("piranha_lily", ConfiguredPlants.PIRANHA_LILY);
        registerConfiguredFeature("water_plants", ConfiguredPlants.WATER_PLANTS);
        registerConfiguredFeature("amanita_carpet", ConfiguredPlants.AMANITA_CARPET);
        registerConfiguredFeature("amanita", ConfiguredTrees.AMANITA);
        registerConfiguredFeature("amanita_fruit", ConfiguredTrees.AMANITA_FRUIT);
        registerConfiguredFeature("amanita_large", ConfiguredTrees.AMANITA_LARGE);
        registerConfiguredFeature("amanita_mountain", ConfiguredTrees.AMANITA_MOUNTAIN);
        registerConfiguredFeature("amanita_oaky", ConfiguredTrees.AMANITA_OAKY);
        registerConfiguredFeature("amanita_oaky_fruit", ConfiguredTrees.AMANITA_OAKY_FRUIT);
        registerConfiguredFeature("dark_amanita", ConfiguredTrees.DARK_AMANITA);
        registerConfiguredFeature("dark_amanita_tall", ConfiguredTrees.DARK_AMANITA_TALL);
        registerConfiguredFeature("huge_red_mushroom_flat", ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_green_mushroom_flat", ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_red_mushroom_tall", ConfiguredTrees.HUGE_RED_MUSHROOM_TALL);
        registerConfiguredFeature("huge_green_mushroom_tall", ConfiguredTrees.HUGE_GREEN_MUSHROOM_TALL);

        registerConfiguredFeature("huge_yellow_mushroom", ConfiguredTrees.HUGE_YELLOW_MUSHROOM);
        registerConfiguredFeature("huge_green_mushroom", ConfiguredTrees.HUGE_GREEN_MUSHROOM);
        registerConfiguredFeature("huge_pink_mushroom", ConfiguredTrees.HUGE_PINK_MUSHROOM);
        registerConfiguredFeature("huge_purple_mushroom", ConfiguredTrees.HUGE_PURPLE_MUSHROOM);
        registerConfiguredFeature("huge_orange_mushroom", ConfiguredTrees.HUGE_ORANGE_MUSHROOM);
        registerConfiguredFeature("huge_brown_mushroom", ConfiguredTrees.HUGE_BROWN_MUSHROOM);
        registerConfiguredFeature("huge_red_mushroom", ConfiguredTrees.HUGE_RED_MUSHROOM);

        registerConfiguredFeature("grassland_plants", ConfiguredPlants.GRASSLAND_PLANTS);
        registerConfiguredFeature("pit_plant", ConfiguredPlants.PIT_PLANT);
        registerConfiguredFeature("desert_plants", ConfiguredPlants.DESERT_PLANTS);
        registerConfiguredFeature("autumn_forest_plants", ConfiguredPlants.AUTUMN_FOREST_PLANTS);
        registerConfiguredFeature("mushroom_gorge_plants", ConfiguredPlants.MUSHROOM_GORGE_PLANTS);
        registerConfiguredFeature("fossil_falls_plants", ConfiguredPlants.FOSSIL_FALLS_PLANTS);

        registerConfiguredFeature("grassland_mushrooms", ConfiguredPlants.GRASSLAND_MUSHROOMS);
        registerConfiguredFeature("amanita_forest_mushrooms", ConfiguredPlants.AMANITA_FOREST_MUSHROOMS);
        registerConfiguredFeature("autumn_forest_mushrooms", ConfiguredPlants.AUTUMN_FOREST_MUSHROOMS);

        registerConfiguredFeature("grassland_flowers", ConfiguredPlants.GRASSLAND_FLOWERS);
        registerConfiguredFeature("forest_of_illusion_flowers", ConfiguredPlants.FOREST_OF_ILLUSION_FLOWERS);
        registerConfiguredFeature("reef_flowers", ConfiguredPlants.REEF_FLOWERS);
        registerConfiguredFeature("mushroom_gorge_flowers", ConfiguredPlants.MUSHROOM_GORGE_FLOWERS);
        registerConfiguredFeature("fossil_falls_flowers", ConfiguredPlants.FOSSIL_FALLS_FLOWERS);

        registerConfiguredFeature("grassland_vegetation", ConfiguredVegetation.GRASSLAND_VEGETATION);
        registerConfiguredFeature("sherbet_land_vegetation", ConfiguredVegetation.SHERBET_LAND_VEGETATION);
        registerConfiguredFeature("reef_vegetation", ConfiguredVegetation.REEF_VEGETATION);
        registerConfiguredFeature("amanita_forest_vegetation", ConfiguredVegetation.AMANITA_FOREST_VEGETATION);
        registerConfiguredFeature("forest_of_illusion_vegetation", ConfiguredVegetation.FOREST_OF_ILLUSION_VEGETATION);
        registerConfiguredFeature("autumn_forest_vegetation", ConfiguredVegetation.AUTUMN_FOREST_VEGETATION);
        registerConfiguredFeature("mushroom_gorge_vegetation", ConfiguredVegetation.MUSHROOM_GORGE_VEGETATION);
        registerConfiguredFeature("canyon_vegetation", ConfiguredVegetation.CANYON_VEGETATION);
        registerConfiguredFeature("cave_vegetation", ConfiguredVegetation.CAVE_VEGETATION);
    }
}

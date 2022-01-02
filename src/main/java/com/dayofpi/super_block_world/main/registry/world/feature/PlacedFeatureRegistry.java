package com.dayofpi.super_block_world.main.registry.world.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class PlacedFeatureRegistry {

    public static void registerPlacedFeature(String id, PlacedFeature feature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Main.MOD_ID, id), feature);
    }

    public static void registerPlacedFeatures() {
        registerPlacedFeature("seagrass", PlacedVegetation.SEAGRASS);
        registerPlacedFeature("coral_few", PlacedVegetation.CORAL_FEW);
        registerPlacedFeature("coral_many", PlacedVegetation.CORAL_MANY);
        registerPlacedFeature("amanita_carpet", PlacedVegetation.AMANITA_CARPET);
        registerPlacedFeature("spring", PlacedDecoration.SPRING);
        registerPlacedFeature("lake_lava", PlacedDecoration.LAKE_LAVA);
        registerPlacedFeature("lake_poison", PlacedDecoration.LAKE_POISON);
        registerPlacedFeature("lake_poison_forest", PlacedDecoration.LAKE_POISON_FOREST);
        registerPlacedFeature("amethyst", PlacedDecoration.AMETHYST);
        registerPlacedFeature("amethyst_extra", PlacedDecoration.AMETHYST_EXTRA);
        registerPlacedFeature("star_crystal", PlacedDecoration.STAR_CRYSTAL);
        registerPlacedFeature("icicle", PlacedDecoration.ICICLE);
        registerPlacedFeature("cave_decoration_common", PlacedDecoration.CAVE_DECORATION_COMMON);
        registerPlacedFeature("cave_decoration_rare", PlacedDecoration.CAVE_DECORATION_RARE);
        registerPlacedFeature("pipe", PlacedPipe.PIPE);
        registerPlacedFeature("pipe_patch_high", PlacedPipe.PIPE_PATCH_HIGH);
        registerPlacedFeature("pipe_patch_low", PlacedPipe.PIPE_PATCH_LOW);
        registerPlacedFeature("underwater_pipe", PlacedPipe.UNDERWATER_PIPE);
        registerPlacedFeature("ocean_floor_pipe", PlacedPipe.OCEAN_FLOOR_PIPE);
        registerPlacedFeature("jellybeam", PlacedBlock.JELLYBEAM);
        registerPlacedFeature("block_single", PlacedBlock.BLOCK_SINGLE);
        registerPlacedFeature("block_line", PlacedBlock.BLOCK_LINE_SURFACE);
        registerPlacedFeature("block_line_deep", PlacedBlock.BLOCK_LINE_DEEP);
        registerPlacedFeature("block_line_crystal", PlacedBlock.BLOCK_LINE_CRYSTAL);
        registerPlacedFeature("block_pile_patch", PlacedBlock.BLOCK_PILE_PATCH);
        registerPlacedFeature("topping", PlacedDecoration.TOPPING);
        registerPlacedFeature("frosting", PlacedDecoration.FROSTING);
        registerPlacedFeature("ore_crumble", PlacedOre.ORE_CRUMBLE);
        registerPlacedFeature("ore_bronze", PlacedOre.ORE_BRONZE);
        registerPlacedFeature("ore_vanillate_coal", PlacedOre.ORE_VANILLATE_COAL);
        registerPlacedFeature("ore_vanillate_coal_extra", PlacedOre.ORE_VANILLATE_COAL_EXTRA);
        registerPlacedFeature("ore_amethyst", PlacedOre.ORE_AMETHYST);
        registerPlacedFeature("ore_cerise", PlacedOre.ORE_CERISE);
        registerPlacedFeature("ore_toadstone", PlacedOre.ORE_TOADSTONE);
        registerPlacedFeature("ore_chiseled_toadstone", PlacedOre.ORE_CHISELED_TOADSTONE);
        registerPlacedFeature("ore_gloomstone", PlacedOre.ORE_GLOOMSTONE);
        registerPlacedFeature("ore_hardstone", PlacedOre.ORE_HARDSTONE);
        registerPlacedFeature("ore_frozen", PlacedOre.ORE_FROZEN);
        registerPlacedFeature("toppings_iron", PlacedOre.VANILLATE_IRON_ORE);
        registerPlacedFeature("toppings_gold", PlacedOre.TOPPING_GOLD);
        registerPlacedFeature("disk_sand", PlacedOre.DISK_SAND);
        registerPlacedFeature("disk_seastone", PlacedOre.DISK_SEASTONE);
        registerPlacedFeature("huge_red_mushroom_flat", PlacedTrees.HUGE_RED_MUSHROOM_FLAT);
        registerPlacedFeature("huge_green_mushroom_flat", PlacedTrees.HUGE_GREEN_MUSHROOM_FLAT);
        registerPlacedFeature("huge_red_mushroom_tall", PlacedTrees.HUGE_RED_MUSHROOM_TALL);
        registerPlacedFeature("huge_green_mushroom_tall", PlacedTrees.HUGE_GREEN_MUSHROOM_TALL);
        registerPlacedFeature("huge_yellow_mushroom", PlacedTrees.HUGE_YELLOW_MUSHROOM);
        registerPlacedFeature("huge_green_mushroom", PlacedTrees.HUGE_GREEN_MUSHROOM);
        registerPlacedFeature("huge_pink_mushroom", PlacedTrees.HUGE_PINK_MUSHROOM);
        registerPlacedFeature("huge_purple_mushroom", PlacedTrees.HUGE_PURPLE_MUSHROOM);
        registerPlacedFeature("huge_orange_mushroom", PlacedTrees.HUGE_ORANGE_MUSHROOM);
        registerPlacedFeature("huge_brown_mushroom", PlacedTrees.HUGE_BROWN_MUSHROOM);
        registerPlacedFeature("huge_red_mushroom", PlacedTrees.HUGE_RED_MUSHROOM);
        registerPlacedFeature("amanita", PlacedTrees.AMANITA);
        registerPlacedFeature("amanita_fruit", PlacedTrees.AMANITA_FRUIT);
        registerPlacedFeature("amanita_large", PlacedTrees.AMANITA_LARGE);
        registerPlacedFeature("amanita_mountain", PlacedTrees.AMANITA_MOUNTAIN);
        registerPlacedFeature("amanita_oaky", PlacedTrees.AMANITA_OAKY);
        registerPlacedFeature("amanita_oaky_fruit", PlacedTrees.AMANITA_OAKY_FRUIT);
        registerPlacedFeature("dark_amanita", PlacedTrees.DARK_AMANITA);
        registerPlacedFeature("dark_amanita_tall", PlacedTrees.DARK_AMANITA_TALL);
        registerPlacedFeature("horsetail", PlacedVegetation.HORSETAIL);
        registerPlacedFeature("stump", PlacedVegetation.STUMP);
        registerPlacedFeature("beanstalk", PlacedTrees.BEANSTALK);
        registerPlacedFeature("beanstalk_budding", PlacedTrees.BEANSTALK_BUDDING);
        registerPlacedFeature("beanstalk_underground", PlacedTrees.BEANSTALK_UNDERGROUND);
        registerPlacedFeature("beanstalk_budding_underground", PlacedTrees.BEANSTALK_BUDDING_UNDERGROUND);
        registerPlacedFeature("muncher_few", PlacedVegetation.MUNCHER_FEW);
        registerPlacedFeature("frozen_muncher", PlacedVegetation.FROZEN_MUNCHER);
        registerPlacedFeature("muncher_many", PlacedVegetation.MUNCHER_MANY);
        registerPlacedFeature("fuzzbush", PlacedVegetation.FUZZBUSH);
        registerPlacedFeature("piranha_lily", PlacedVegetation.PIRANHA_LILY);
        registerPlacedFeature("water_plants", PlacedVegetation.WATER_PLANTS);

        // Grassland features
        registerPlacedFeature("flowerbed", PlacedVegetation.FLOWERBED);
        registerPlacedFeature("grassland_plants", PlacedVegetation.GRASSLAND_PLANTS);
        registerPlacedFeature("grassland_mushrooms", PlacedVegetation.GRASSLAND_MUSHROOMS);
        registerPlacedFeature("grassland_flowers", PlacedVegetation.GRASSLAND_FLOWERS);
        registerPlacedFeature("grassland_vegetation", PlacedVegetation.GRASSLAND_VEGETATION);

        registerPlacedFeature("sherbet_land_vegetation", PlacedVegetation.SHERBET_LAND_VEGETATION);

        // Meadow features
        registerPlacedFeature("meadow_plants", PlacedVegetation.MEADOW_PLANTS);
        registerPlacedFeature("meadow_flowers", PlacedVegetation.MEADOW_FLOWERS);
        registerPlacedFeature("meadow_vegetation", PlacedVegetation.MEADOW_VEGETATION);

        // Reef features
        registerPlacedFeature("reef_flowers", PlacedVegetation.REEF_FLOWERS);
        registerPlacedFeature("reef_vegetation", PlacedVegetation.REEF_VEGETATION);

        // Gorge features
        registerPlacedFeature("gorge_plants", PlacedVegetation.GORGE_PLANTS);
        registerPlacedFeature("gorge_flowers", PlacedVegetation.GORGE_FLOWERS);
        registerPlacedFeature("gorge_vegetation", PlacedVegetation.GORGE_VEGETATION);
        registerPlacedFeature("canyon_vegetation", PlacedVegetation.CANYON_VEGETATION);

        // Forest features
        registerPlacedFeature("amanita_forest_mushrooms", PlacedVegetation.AMANITA_FOREST_MUSHROOMS);
        registerPlacedFeature("autumn_forest_mushrooms", PlacedVegetation.AUTUMN_FOREST_MUSHROOMS);
        registerPlacedFeature("amanita_forest_vegetation", PlacedVegetation.AMANITA_FOREST_VEGETATION);

        registerPlacedFeature("autumn_forest_plants", PlacedVegetation.AUTUMN_FOREST_PLANTS);
        registerPlacedFeature("autumn_forest_vegetation", PlacedVegetation.AUTUMN_FOREST_VEGETATION);

        registerPlacedFeature("forest_of_illusion_flowers", PlacedVegetation.FOREST_OF_ILLUSION_FLOWERS);
        registerPlacedFeature("forest_of_illusion_vegetation", PlacedVegetation.FOREST_OF_ILLUSION_VEGETATION);

        // Desert features
        registerPlacedFeature("desert_plants", PlacedVegetation.DESERT_PLANTS);
        registerPlacedFeature("quicksand", PlacedDecoration.QUICKSAND);
        registerPlacedFeature("cactus", PlacedVegetation.CACTUS);
        registerPlacedFeature("cactus_patch", PlacedVegetation.CACTUS_PATCH);

        // Fossil falls features
        registerPlacedFeature("fossil_falls_plants", PlacedVegetation.FOSSIL_FALLS_PLANTS);
        registerPlacedFeature("fossil_falls_flowers", PlacedVegetation.FOSSIL_FALLS_FLOWERS);

        // Cave features
        registerPlacedFeature("cave_vegetation", PlacedVegetation.CAVE_VEGETATION);


    }
}

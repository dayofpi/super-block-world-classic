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
        registerPlacedFeature("spring", PlacedMisc.SPRING);
        registerPlacedFeature("lake_lava", PlacedMisc.LAKE_LAVA);
        registerPlacedFeature("lake_poison", PlacedMisc.LAKE_POISON);
        registerPlacedFeature("lake_poison_forest", PlacedMisc.LAKE_POISON_FOREST);
        registerPlacedFeature("patch_cave_crystals", PlacedMisc.AMETHYST);
        registerPlacedFeature("amethyst_extra", PlacedMisc.AMETHYST_EXTRA);
        registerPlacedFeature("icicle", PlacedMisc.ICICLE);
        registerPlacedFeature("cave_decoration_common", PlacedMisc.CAVE_DECORATION_COMMON);
        registerPlacedFeature("cave_decoration_rare", PlacedMisc.CAVE_DECORATION_RARE);
        registerPlacedFeature("pipe", PlacedMisc.PIPE);
        registerPlacedFeature("pipe_patch", PlacedMisc.PIPE_PATCH);
        registerPlacedFeature("underwater_pipe", PlacedMisc.UNDERWATER_PIPE);
        registerPlacedFeature("ocean_floor_pipe", PlacedMisc.OCEAN_FLOOR_PIPE);
        registerPlacedFeature("jellybeam", PlacedBlocks.JELLYBEAM);
        registerPlacedFeature("block_single", PlacedBlocks.BLOCK_SINGLE);
        registerPlacedFeature("block_line", PlacedBlocks.BLOCK_LINE_SURFACE);
        registerPlacedFeature("block_line_deep", PlacedBlocks.BLOCK_LINE_DEEP);
        registerPlacedFeature("block_line_crystal", PlacedBlocks.BLOCK_LINE_CRYSTAL);
        registerPlacedFeature("block_pile_patch", PlacedBlocks.BLOCK_PILE_PATCH);
        registerPlacedFeature("topping", PlacedMisc.TOPPING);
        registerPlacedFeature("frosting", PlacedMisc.FROSTING);
        registerPlacedFeature("ore_crumble", PlacedOres.ORE_CRUMBLE);
        registerPlacedFeature("ore_bronze", PlacedOres.ORE_BRONZE);
        registerPlacedFeature("ore_amethyst", PlacedOres.ORE_AMETHYST);
        registerPlacedFeature("ore_cerise", PlacedOres.ORE_CERISE);
        registerPlacedFeature("ore_hardstone", PlacedOres.ORE_HARDSTONE);
        registerPlacedFeature("ore_frozen", PlacedOres.ORE_FROZEN);
        registerPlacedFeature("toppings_coal", PlacedOres.TOPPING_COAL);
        registerPlacedFeature("toppings_iron", PlacedOres.TOPPING_IRON);
        registerPlacedFeature("toppings_gold", PlacedOres.TOPPING_GOLD);
        registerPlacedFeature("disk_sand", PlacedOres.DISK_SAND);
        registerPlacedFeature("disk_seastone", PlacedOres.DISK_SEASTONE);
        registerPlacedFeature("huge_red_mushroom_flat", PlacedTrees.HUGE_RED_MUSHROOM_FLAT);
        registerPlacedFeature("huge_green_mushroom_flat", PlacedTrees.HUGE_GREEN_MUSHROOM_FLAT);
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
        registerPlacedFeature("beanstalk", PlacedVegetation.BEANSTALK);
        registerPlacedFeature("beanstalk_budding", PlacedVegetation.BEANSTALK_BUDDING);
        registerPlacedFeature("muncher_few", PlacedVegetation.MUNCHER_FEW);
        registerPlacedFeature("frozen_muncher", PlacedVegetation.FROZEN_MUNCHER);
        registerPlacedFeature("muncher_many", PlacedVegetation.MUNCHER_MANY);
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
        registerPlacedFeature("quicksand", PlacedMisc.QUICKSAND);
        registerPlacedFeature("cactus", PlacedVegetation.CACTUS);

        // Fossil falls features
        registerPlacedFeature("fossil_falls_plants", PlacedVegetation.FOSSIL_FALLS_PLANTS);
        registerPlacedFeature("fossil_falls_flowers", PlacedVegetation.FOSSIL_FALLS_FLOWERS);

        // Cave features
        registerPlacedFeature("cave_vegetation", PlacedVegetation.CAVE_VEGETATION);


    }
}

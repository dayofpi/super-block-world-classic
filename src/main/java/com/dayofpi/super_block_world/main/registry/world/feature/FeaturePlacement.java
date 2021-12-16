package com.dayofpi.super_block_world.main.registry.world.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.world.feature.placed.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class FeaturePlacement {

    public static void registerPlacedFeature(String id, PlacedFeature feature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Main.MOD_ID, id), feature);
    }

    public static void registerPlacedFeatures() {
        registerPlacedFeature("seagrass", PlacedVegetation.SEAGRASS);
        registerPlacedFeature("coral_few", PlacedVegetation.CORAL_FEW);
        registerPlacedFeature("coral_many", PlacedVegetation.CORAL_MANY);
        registerPlacedFeature("spring", PlacedMisc.SPRING);
        registerPlacedFeature("lake_lava", PlacedMisc.LAKE_LAVA);
        registerPlacedFeature("lake_poison", PlacedMisc.LAKE_POISON);
        registerPlacedFeature("patch_cave_crystals", PlacedMisc.AMETHYST);
        registerPlacedFeature("amethyst_extra", PlacedMisc.AMETHYST_EXTRA);
        registerPlacedFeature("cave_decoration", PlacedMisc.CAVE_DECORATION);
        registerPlacedFeature("pipe_underwater", PlacedMisc.WARP_PIPE_WATER);
        registerPlacedFeature("jellybeam", PlacedBlocks.JELLYBEAM);
        registerPlacedFeature("block_single", PlacedBlocks.BLOCK_SINGLE);
        registerPlacedFeature("block_line", PlacedBlocks.BLOCK_LINE_SURFACE);
        registerPlacedFeature("block_line_deep", PlacedBlocks.BLOCK_LINE_DEEP);
        registerPlacedFeature("vanillate_topping", PlacedMisc.VANILLATE_TOPPING);
        registerPlacedFeature("quicksand", PlacedMisc.QUICKSAND);
        registerPlacedFeature("ore_crumble", PlacedOres.ORE_CRUMBLE);
        registerPlacedFeature("ore_bronze", PlacedOres.ORE_BRONZE);
        registerPlacedFeature("ore_amethyst", PlacedOres.ORE_AMETHYST);
        registerPlacedFeature("ore_cerise", PlacedOres.ORE_CERISE);
        registerPlacedFeature("ore_hardstone", PlacedOres.ORE_HARDSTONE);
        registerPlacedFeature("toppings_coal", PlacedOres.TOPPING_COAL);
        registerPlacedFeature("toppings_iron", PlacedOres.TOPPING_IRON);
        registerPlacedFeature("toppings_gold", PlacedOres.TOPPING_GOLD);
        registerPlacedFeature("disk_sand", PlacedOres.DISK_SAND);
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
        registerPlacedFeature("cactus", PlacedVegetation.CACTUS);
        registerPlacedFeature("beanstalk", PlacedVegetation.BEANSTALK_NOT_BUDDING);
        registerPlacedFeature("beanstalk_budding", PlacedVegetation.BEANSTALK_BUDDING);
        registerPlacedFeature("muncher_few", PlacedVegetation.MUNCHER_FEW);
        registerPlacedFeature("muncher_many", PlacedVegetation.MUNCHER_MANY);
        registerPlacedFeature("flowerbed", PlacedVegetation.FLOWERBED);
        registerPlacedFeature("grassland_plants", PlacedVegetation.PLANT_GRASSLAND);
        registerPlacedFeature("gorge_plants", PlacedVegetation.PLANT_GORGE);
        registerPlacedFeature("desert_plants", PlacedVegetation.PLANT_DESERT);
        registerPlacedFeature("grassland_mushrooms", PlacedVegetation.MUSHROOM_GRASSLAND);
        registerPlacedFeature("grassland_flowers", PlacedVegetation.FLOWER_GRASSLAND);
        registerPlacedFeature("gorge_flowers", PlacedVegetation.FLOWER_GORGE);
        registerPlacedFeature("grassland_vegetation", PlacedVegetation.VEGETATION_GRASSLAND);
        registerPlacedFeature("gorge_vegetation", PlacedVegetation.VEGETATION_GORGE);
        registerPlacedFeature("tall_gorge_vegetation", PlacedVegetation.VEGETATION_GORGE_TALL);
        registerPlacedFeature("cave_vegetation", PlacedVegetation.VEGETATION_CAVE);
    }
}

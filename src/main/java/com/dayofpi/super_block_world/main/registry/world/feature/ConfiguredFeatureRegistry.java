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
        registerConfiguredFeature("spring", ConfiguredMisc.SPRING);
        registerConfiguredFeature("lake_lava", ConfiguredMisc.LAKE_LAVA);
        registerConfiguredFeature("lake_poison", ConfiguredMisc.LAKE_POISON);
        registerConfiguredFeature("amethyst_ceiling", ConfiguredMisc.AMETHYST_CEILING);
        registerConfiguredFeature("amethyst_floor", ConfiguredMisc.AMETHYST_FLOOR);
        registerConfiguredFeature("icicle", ConfiguredMisc.ICICLE);
        registerConfiguredFeature("cave_decoration_common", ConfiguredMisc.CAVE_DECORATION_COMMON);
        registerConfiguredFeature("cave_decoration_rare", ConfiguredMisc.CAVE_DECORATION_RARE);
        registerConfiguredFeature("jellybeam", ConfiguredBlocks.JELLYBEAM);
        registerConfiguredFeature("block_single", ConfiguredBlocks.BLOCK_SINGLE);
        registerConfiguredFeature("block_line", ConfiguredBlocks.BLOCK_LINE_SURFACE);
        registerConfiguredFeature("block_line_deep", ConfiguredBlocks.BLOCK_LINE_DEEP);
        registerConfiguredFeature("block_line_crystal", ConfiguredBlocks.BLOCK_LINE_CRYSTAL);
        registerConfiguredFeature("block_pile", ConfiguredBlocks.BLOCK_PILE);
        registerConfiguredFeature("pipe_patch", ConfiguredMisc.PIPE_PATCH);
        registerConfiguredFeature("topping", ConfiguredMisc.TOPPING);
        registerConfiguredFeature("frosting", ConfiguredMisc.FROSTING);
        registerConfiguredFeature("quicksand", ConfiguredMisc.QUICKSAND);

        registerConfiguredFeature("ore_crumble", ConfiguredOres.ORE_CRUMBLE);
        registerConfiguredFeature("ore_bronze", ConfiguredOres.ORE_BRONZE);
        registerConfiguredFeature("ore_amethyst", ConfiguredOres.ORE_AMETHYST);
        registerConfiguredFeature("ore_cerise", ConfiguredOres.ORE_CERISE);
        registerConfiguredFeature("ore_hardstone", ConfiguredOres.ORE_HARDSTONE);
        registerConfiguredFeature("ore_frozen", ConfiguredOres.ORE_FROZEN);

        registerConfiguredFeature("topping_coal", ConfiguredOres.TOPPING_COAL);
        registerConfiguredFeature("topping_iron", ConfiguredOres.TOPPING_IRON);
        registerConfiguredFeature("topping_gold", ConfiguredOres.TOPPING_GOLD);
        registerConfiguredFeature("disk_sand", ConfiguredOres.DISK_SAND);
        registerConfiguredFeature("disk_seastone", ConfiguredOres.DISK_SEASTONE);
        registerConfiguredFeature("horsetail_patch", ConfiguredVegetation.HORSETAIL_PATCH);
        registerConfiguredFeature("stump_patch", ConfiguredVegetation.STUMP_PATCH);
        registerConfiguredFeature("beanstalk", ConfiguredVegetation.BEANSTALK);
        registerConfiguredFeature("beanstalk_budding", ConfiguredVegetation.BEANSTALK_BUDDING);
        registerConfiguredFeature("muncher", ConfiguredVegetation.MUNCHER);
        registerConfiguredFeature("frozen_muncher", ConfiguredVegetation.FROZEN_MUNCHER);
        registerConfiguredFeature("flowerbed", ConfiguredVegetation.FLOWERBED);
        registerConfiguredFeature("water_plants", ConfiguredVegetation.WATER_PLANTS);
        registerConfiguredFeature("amanita_carpet", ConfiguredVegetation.AMANITA_CARPET);

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

        registerConfiguredFeature("grassland_plants", ConfiguredVegetation.GRASSLAND_PLANTS);
        registerConfiguredFeature("autumn_forest_plants", ConfiguredVegetation.AUTUMN_FOREST_PLANTS);
        registerConfiguredFeature("mushroom_gorge_plants", ConfiguredVegetation.MUSHROOM_GORGE_PLANTS);
        registerConfiguredFeature("fossil_falls_plants", ConfiguredVegetation.FOSSIL_FALLS_PLANTS);

        registerConfiguredFeature("grassland_mushrooms", ConfiguredVegetation.GRASSLAND_MUSHROOMS);
        registerConfiguredFeature("amanita_forest_mushrooms", ConfiguredVegetation.AMANITA_FOREST_MUSHROOMS);
        registerConfiguredFeature("autumn_forest_mushrooms", ConfiguredVegetation.AUTUMN_FOREST_MUSHROOMS);

        registerConfiguredFeature("grassland_flowers", ConfiguredVegetation.GRASSLAND_FLOWERS);
        registerConfiguredFeature("forest_of_illusion_flowers", ConfiguredVegetation.FOREST_OF_ILLUSION_FLOWERS);
        registerConfiguredFeature("reef_flowers", ConfiguredVegetation.REEF_FLOWERS);
        registerConfiguredFeature("mushroom_gorge_flowers", ConfiguredVegetation.MUSHROOM_GORGE_FLOWERS);
        registerConfiguredFeature("fossil_falls_flowers", ConfiguredVegetation.FOSSIL_FALLS_FLOWERS);

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

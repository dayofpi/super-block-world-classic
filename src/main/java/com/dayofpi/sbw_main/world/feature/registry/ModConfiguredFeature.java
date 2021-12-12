package com.dayofpi.sbw_main.world.feature.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.world.feature.registry.configured_feature.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ModConfiguredFeature {
    private static void registerConfiguredFeature(String id, ConfiguredFeature<?, ?> type) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Main.MOD_ID, id), type);
    }

    public static void registerConfiguredFeatures() {
        registerConfiguredFeature("spring", ConfiguredMisc.SPRING);
        registerConfiguredFeature("lake_lava", ConfiguredMisc.LAKE_LAVA);
        registerConfiguredFeature("lake_poison", ConfiguredMisc.LAKE_POISON);
        registerConfiguredFeature("amethyst_ceiling", ConfiguredMisc.AMETHYST_CEILING);
        registerConfiguredFeature("amethyst_floor", ConfiguredMisc.AMETHYST_FLOOR);
        registerConfiguredFeature("cave_decoration", ConfiguredMisc.CAVE_DECORATION);
        registerConfiguredFeature("jellybeam", ConfiguredBlocks.JELLYBEAM);
        registerConfiguredFeature("block_single", ConfiguredBlocks.BLOCK_SINGLE);
        registerConfiguredFeature("block_line", ConfiguredBlocks.BLOCK_LINE_SURFACE);
        registerConfiguredFeature("block_line_deep", ConfiguredBlocks.BLOCK_LINE_DEEP);
        registerConfiguredFeature("vanillate_topping", ConfiguredOres.VANILLATE_TOPPING);
        registerConfiguredFeature("quicksand", ConfiguredOres.QUICKSAND);
        registerConfiguredFeature("ore_crumble", ConfiguredOres.ORE_CRUMBLE);
        registerConfiguredFeature("ore_bronze", ConfiguredOres.ORE_BRONZE);
        registerConfiguredFeature("ore_amethyst", ConfiguredOres.ORE_AMETHYST);
        registerConfiguredFeature("ore_cerise", ConfiguredOres.ORE_CERISE);
        registerConfiguredFeature("ore_hardstone", ConfiguredOres.ORE_HARDSTONE);
        registerConfiguredFeature("topping_coal", ConfiguredOres.TOPPING_COAL);
        registerConfiguredFeature("topping_iron", ConfiguredOres.TOPPING_IRON);
        registerConfiguredFeature("topping_gold", ConfiguredOres.TOPPING_GOLD);
        registerConfiguredFeature("disk_sand", ConfiguredOres.DISK_SAND);
        registerConfiguredFeature("horsetail_patch", ConfiguredVegetation.HORSETAIL_PATCH);
        registerConfiguredFeature("stump_patch", ConfiguredVegetation.STUMP_PATCH);
        registerConfiguredFeature("beanstalk_not_budding", ConfiguredVegetation.BEANSTALK_NOT_BUDDING);
        registerConfiguredFeature("beanstalk_budding", ConfiguredVegetation.BEANSTALK_BUDDING);
        registerConfiguredFeature("muncher", ConfiguredVegetation.MUNCHER);
        registerConfiguredFeature("flowerbed", ConfiguredVegetation.FLOWERBED);
        registerConfiguredFeature("plant_grassland", ConfiguredVegetation.PLANT_GRASSLAND);
        registerConfiguredFeature("plant_gorge", ConfiguredVegetation.PLANT_GORGE);
        registerConfiguredFeature("mushroom_grassland", ConfiguredVegetation.MUSHROOM_GRASSLAND);
        registerConfiguredFeature("flower_grassland", ConfiguredVegetation.FLOWER_GRASSLAND);
        registerConfiguredFeature("flower_gorge", ConfiguredVegetation.FLOWER_GORGE);
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
        registerConfiguredFeature("vegetation_grassland", ConfiguredVegetation.VEGETATION_GRASSLAND);
        registerConfiguredFeature("vegetation_gorge", ConfiguredVegetation.VEGETATION_GORGE);
        registerConfiguredFeature("vegetation_gorge_tall", ConfiguredVegetation.VEGETATION_GORGE_TALL);
        registerConfiguredFeature("giant_cave_mushroom", ConfiguredVegetation.VEGETATION_CAVE);
    }
}

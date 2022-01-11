package com.dayofpi.super_block_world.registry.world.feature;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.registry.world.feature.placed.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class PlacedFeatureInit {

    public static void registerPlacedFeature(String id, PlacedFeature feature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Main.MOD_ID, id), feature);
    }

    public static void registerPlacedFeatures() {
        registerPlacedFeature("spring", PlacedDecoration.SPRING_WATER);
        registerPlacedFeature("lake_lava", PlacedDecoration.LAKE_LAVA);
        registerPlacedFeature("lake_poison", PlacedDecoration.LAKE_POISON);
        registerPlacedFeature("lake_poison_forest", PlacedDecoration.LAKE_POISON_FOREST);

        registerPlacedFeature("seagrass", PlacedPlants.SEAGRASS);
        registerPlacedFeature("coral_few", PlacedPlants.CORAL_FEW);
        registerPlacedFeature("coral_many", PlacedPlants.CORAL_MANY);

        registerPlacedFeature("amethyst", PlacedDecoration.AMETHYST);
        registerPlacedFeature("amethyst_extra", PlacedDecoration.AMETHYST_EXTRA);
        registerPlacedFeature("star_crystal", PlacedDecoration.STAR_CRYSTAL);
        registerPlacedFeature("icicle", PlacedDecoration.ICICLE);
        registerPlacedFeature("cave_decoration_common", PlacedDecoration.CAVE_DECORATION_COMMON);
        registerPlacedFeature("cave_decoration_rare", PlacedDecoration.CAVE_DECORATION_RARE);

        registerPlacedFeature("pipe", PlacedPipes.PIPE);
        registerPlacedFeature("pipe_patch_high", PlacedPipes.PIPE_PATCH_HIGH);
        registerPlacedFeature("pipe_patch_low", PlacedPipes.PIPE_PATCH_LOW);
        registerPlacedFeature("underwater_pipe", PlacedPipes.UNDERWATER_PIPE);
        registerPlacedFeature("ocean_floor_pipe", PlacedPipes.OCEAN_FLOOR_PIPE);

        registerPlacedFeature("jellybeam", PlacedBlocks.JELLYBEAM);
        registerPlacedFeature("block_single", PlacedBlocks.BLOCK_SINGLE);
        registerPlacedFeature("block_line", PlacedBlocks.BLOCK_LINE_SURFACE);
        registerPlacedFeature("block_line_deep", PlacedBlocks.BLOCK_LINE_DEEP);
        registerPlacedFeature("block_line_crystal", PlacedBlocks.BLOCK_LINE_CRYSTAL);
        registerPlacedFeature("block_pile_patch", PlacedBlocks.BLOCK_PILE);

        registerPlacedFeature("topping", PlacedDecoration.TOPPING);
        registerPlacedFeature("frosting", PlacedDecoration.FROSTING);
        registerPlacedFeature("quicksand", PlacedDecoration.QUICKSAND);

        registerPlacedFeature("ore_bronze", PlacedOres.ORE_BRONZE);
        registerPlacedFeature("ore_vanillate_coal", PlacedOres.ORE_VANILLATE_COAL);
        registerPlacedFeature("ore_vanillate_coal_extra", PlacedOres.ORE_VANILLATE_COAL_EXTRA);
        registerPlacedFeature("ore_vanillate_iron", PlacedOres.ORE_VANILLATE_IRON);
        registerPlacedFeature("ore_cerise", PlacedOres.ORE_CERISE);
        registerPlacedFeature("ore_frozen", PlacedOres.ORE_FROZEN);

        registerPlacedFeature("ore_crumble", PlacedOres.ORE_CRUMBLE);
        registerPlacedFeature("ore_toadstone", PlacedOres.ORE_TOADSTONE);
        registerPlacedFeature("ore_chiseled_toadstone", PlacedOres.ORE_CHISELED_TOADSTONE);
        registerPlacedFeature("ore_gloomstone", PlacedOres.ORE_GLOOMSTONE);
        registerPlacedFeature("ore_hardstone", PlacedOres.ORE_HARDSTONE);

        registerPlacedFeature("toppings_gold", PlacedOres.TOPPING_GOLD);
        registerPlacedFeature("toppings_amethyst", PlacedOres.TOPPING_AMETHYST);
        registerPlacedFeature("disk_sand", PlacedOres.DISK_SAND);
        registerPlacedFeature("disk_seastone", PlacedOres.DISK_SEASTONE);

        registerPlacedFeature("huge_red_mushroom_flat", PlacedTrees.HUGE_RED_MUSHROOM_FLAT);
        registerPlacedFeature("huge_green_mushroom_flat", PlacedTrees.HUGE_GREEN_MUSHROOM_FLAT);

        registerPlacedFeature("huge_red_mushroom_canyon", PlacedTrees.HUGE_RED_MUSHROOM_CANYON);
        registerPlacedFeature("huge_green_mushroom_canyon", PlacedTrees.HUGE_GREEN_MUSHROOM_CANYON);

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
        registerPlacedFeature("amanita_belled", PlacedTrees.BELL_PINE);
        registerPlacedFeature("amanita_oaky", PlacedTrees.AMANITA_OAKY);
        registerPlacedFeature("amanita_oaky_fruit", PlacedTrees.AMANITA_OAKY_FRUIT);
        registerPlacedFeature("dark_amanita", PlacedTrees.DARK_AMANITA);
        registerPlacedFeature("dark_amanita_tall", PlacedTrees.DARK_AMANITA_TALL);
        registerPlacedFeature("dark_amanita_mountain", PlacedTrees.DARK_AMANITA_MOUNTAIN);

        registerPlacedFeature("stump", PlacedPlants.STUMP);
        registerPlacedFeature("cactus", PlacedPlants.CACTUS);
        registerPlacedFeature("cactus_patch", PlacedPlants.CACTUS_PATCH);
        registerPlacedFeature("horsetail", PlacedPlants.HORSETAIL);
        registerPlacedFeature("tall_grass_patch", PlacedPlants.TALL_GRASS_PATCH);
        registerPlacedFeature("beanstalk", PlacedTrees.BEANSTALK);
        registerPlacedFeature("beanstalk_budding", PlacedTrees.BEANSTALK_BUDDING);
        registerPlacedFeature("beanstalk_underground", PlacedTrees.BEANSTALK_UNDERGROUND);
        registerPlacedFeature("beanstalk_budding_underground", PlacedTrees.BEANSTALK_BUDDING_UNDERGROUND);

        registerPlacedFeature("muncher_few", PlacedPlants.MUNCHER_FEW);
        registerPlacedFeature("muncher_many", PlacedPlants.MUNCHER_MANY);
        registerPlacedFeature("frozen_muncher", PlacedPlants.FROZEN_MUNCHER);
        registerPlacedFeature("fuzzbush", PlacedPlants.FUZZBUSH);
        registerPlacedFeature("piranha_lily", PlacedPlants.PIRANHA_LILY);
        registerPlacedFeature("flowerbed", PlacedPlants.FLOWERBED);
        registerPlacedFeature("amanita_carpet", PlacedPlants.AMANITA_CARPET);

        registerPlacedFeature("plants_grassland", PlacedPlants.PLANTS_GRASSLAND);
        registerPlacedFeature("plants_autumn", PlacedPlants.PLANTS_AUTUMN);
        registerPlacedFeature("plants_meadow", PlacedPlants.PLANTS_MEADOW);
        registerPlacedFeature("plants_desert", PlacedPlants.PLANTS_DESERT);
        registerPlacedFeature("plants_gorge", PlacedPlants.PLANTS_GORGE);
        registerPlacedFeature("plants_fossil", PlacedPlants.PLANTS_FOSSIL);
        registerPlacedFeature("plants_water_surface", PlacedPlants.PLANTS_WATER_SURFACE);

        registerPlacedFeature("flowers_grassland", PlacedPlants.FLOWERS_GRASSLAND);
        registerPlacedFeature("flowers_illusion", PlacedPlants.FLOWERS_ILLUSION);
        registerPlacedFeature("flowers_meadow", PlacedPlants.FLOWERS_MEADOW);
        registerPlacedFeature("flowers_reef", PlacedPlants.FLOWERS_REEF);
        registerPlacedFeature("flowers_gorge", PlacedPlants.FLOWERS_GORGE);
        registerPlacedFeature("flowers_fossil", PlacedPlants.FLOWERS_FOSSIL);

        registerPlacedFeature("mushrooms_grassland", PlacedPlants.MUSHROOMS_GRASSLAND);
        registerPlacedFeature("mushrooms_mountain", PlacedPlants.MUSHROOMS_MOUNTAIN);
        registerPlacedFeature("mushrooms_amanita", PlacedPlants.MUSHROOMS_AMANITA);
        registerPlacedFeature("mushrooms_autumn", PlacedPlants.MUSHROOMS_AUTUMN);

        registerPlacedFeature("vegetation_grassland", PlacedVegetation.VEGETATION_GRASSLAND);
        registerPlacedFeature("vegetation_amanita", PlacedVegetation.VEGETATION_AMANITA);
        registerPlacedFeature("vegetation_autumn", PlacedVegetation.VEGETATION_AUTUMN);
        registerPlacedFeature("vegetation_autumn_mountain", PlacedVegetation.VEGETATION_AUTUMN_MOUNTAIN);
        registerPlacedFeature("vegetation_illusion", PlacedVegetation.VEGETATION_ILLUSION);
        registerPlacedFeature("vegetation_sherbet", PlacedVegetation.VEGETATION_SHERBET);
        registerPlacedFeature("vegetation_meadow", PlacedVegetation.VEGETATION_MEADOW);
        registerPlacedFeature("vegetation_reef", PlacedVegetation.VEGETATION_REEF);
        registerPlacedFeature("vegetation_gorge", PlacedVegetation.VEGETATION_GORGE);
        registerPlacedFeature("vegetation_gorge_canyon", PlacedVegetation.VEGETATION_GORGE_CANYON);
        registerPlacedFeature("vegetation_mountain", PlacedVegetation.VEGETATION_MOUNTAIN);
        registerPlacedFeature("vegetation_underground", PlacedVegetation.VEGETATION_UNDERGROUND);






    }
}

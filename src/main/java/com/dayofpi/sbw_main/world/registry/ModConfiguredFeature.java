package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredBlocks;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredMisc;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredOres;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredTrees;
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
        registerConfiguredFeature("waterfall", ConfiguredMisc.WATERFALL);
        registerConfiguredFeature("lake_lava", ConfiguredMisc.LAKE_LAVA);
        registerConfiguredFeature("lake_poison", ConfiguredMisc.LAKE_POISON);
        registerConfiguredFeature("pipe_ceiling", ConfiguredMisc.PIPE_CEILING);
        registerConfiguredFeature("pipe_floor", ConfiguredMisc.PIPE_FLOOR);
        registerConfiguredFeature("jellybeam", ConfiguredBlocks.JELLYBEAM);
        registerConfiguredFeature("block_single", ConfiguredBlocks.BLOCK_SINGLE);
        registerConfiguredFeature("block_line_toadstone", ConfiguredBlocks.BLOCK_LINE_TOADSTONE);
        registerConfiguredFeature("block_line_gloomstone", ConfiguredBlocks.BLOCK_LINE_GLOOMSTONE);
        registerConfiguredFeature("block_line", ConfiguredBlocks.BLOCK_LINE);
        registerConfiguredFeature("block_line_deep", ConfiguredBlocks.BLOCK_LINE_DEEP);
        registerConfiguredFeature("block_pile", ConfiguredBlocks.BLOCK_PILE);
        registerConfiguredFeature("block_pile_rare", ConfiguredBlocks.BLOCK_PILE_RARE);
        registerConfiguredFeature("vanillate_topping", ConfiguredOres.VANILLATE_TOPPING);
        registerConfiguredFeature("ore_crumble", ConfiguredOres.ORE_CRUMBLE);
        registerConfiguredFeature("ore_bronze", ConfiguredOres.ORE_BRONZE);
        registerConfiguredFeature("ore_amethyst", ConfiguredOres.ORE_AMETHYST);
        registerConfiguredFeature("ore_hardstone", ConfiguredOres.ORE_HARDSTONE);
        registerConfiguredFeature("topping_coal", ConfiguredOres.TOPPING_COAL);
        registerConfiguredFeature("topping_iron", ConfiguredOres.TOPPING_IRON);
        registerConfiguredFeature("topping_gold", ConfiguredOres.TOPPING_GOLD);
        registerConfiguredFeature("disk_sand", ConfiguredOres.DISK_SAND);
        registerConfiguredFeature("amanita", ConfiguredTrees.AMANITA);
        registerConfiguredFeature("amanita_fruit", ConfiguredTrees.AMANITA_FRUIT);
        registerConfiguredFeature("amanita_large", ConfiguredTrees.AMANITA_LARGE);
        registerConfiguredFeature("amanita_oaky", ConfiguredTrees.AMANITA_OAKY);
        registerConfiguredFeature("amanita_oaky_fruit", ConfiguredTrees.AMANITA_OAKY_FRUIT);
        registerConfiguredFeature("dark_amanita", ConfiguredTrees.DARK_AMANITA);
        registerConfiguredFeature("dark_amanita_tall", ConfiguredTrees.DARK_AMANITA_TALL);
        registerConfiguredFeature("huge_red_mushroom_flat", ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_green_mushroom_flat", ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT);
        registerConfiguredFeature("huge_yellow_mushroom", ConfiguredTrees.HUGE_YELLOW_MUSHROOM);
        registerConfiguredFeature("huge_green_mushroom", ConfiguredTrees.HUGE_GREEN_MUSHROOM);
        registerConfiguredFeature("huge_pink_mushroom", ConfiguredTrees.HUGE_PINK_MUSHROOM);
        registerConfiguredFeature("huge_purple_mushroom", ConfiguredTrees.HUGE_PURPLE_MUSHROOM);
        registerConfiguredFeature("huge_orange_mushroom", ConfiguredTrees.HUGE_ORANGE_MUSHROOM);
        registerConfiguredFeature("huge_brown_mushroom", ConfiguredTrees.HUGE_BROWN_MUSHROOM);
        registerConfiguredFeature("huge_red_mushroom", ConfiguredTrees.HUGE_RED_MUSHROOM);
        }
}

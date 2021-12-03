package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredBlocks;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredMisc;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredOres;
import com.dayofpi.sbw_main.world.registry.configured_feature.ConfiguredTrees;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;

public class ModPlacedFeature {
    public static final PlacedFeature SPRING = ConfiguredMisc.SPRING.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature WATERFALL = ConfiguredMisc.SPRING.withPlacement(CountPlacementModifier.of(10), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(70), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_LAVA = ConfiguredMisc.LAKE_LAVA.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_POISON = ConfiguredMisc.LAKE_POISON.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(200)), BiomePlacementModifier.of());

    public static final PlacedFeature PIPE_DEFAULT = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(ConfiguredMisc.PIPE_CEILING::withPlacement, ConfiguredMisc.PIPE_FLOOR::withPlacement)).withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature PIPE_UNDERWATER = ModFeature.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());

    public static final PlacedFeature JELLYBEAM = ConfiguredBlocks.JELLYBEAM.withPlacement(RarityFilterPlacementModifier.of(13), CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_SINGLE = ConfiguredBlocks.BLOCK_SINGLE.withPlacement(RarityFilterPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(85)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE = ConfiguredBlocks.BLOCK_LINE.withPlacement(RarityFilterPlacementModifier.of(39), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(50), YOffset.fixed(100)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_DEEP = ConfiguredBlocks.BLOCK_LINE_DEEP.withPlacement(RarityFilterPlacementModifier.of(33), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-30), YOffset.fixed(50)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_PILE = ConfiguredBlocks.BLOCK_PILE.withPlacement(RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_PILE_RARE = ConfiguredBlocks.BLOCK_PILE_RARE.withPlacement(RarityFilterPlacementModifier.of(39), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(70)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());

    public static final PlacedFeature VANILLATE_TOPPING = ConfiguredOres.VANILLATE_TOPPING.withPlacement(CountPlacementModifier.of(40), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_CRUMBLE = ConfiguredOres.ORE_CRUMBLE.withPlacement(CountPlacementModifier.of(170), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_BRONZE = ConfiguredOres.ORE_BRONZE.withPlacement(CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(12), YOffset.fixed(96)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_AMETHYST = ConfiguredOres.ORE_AMETHYST.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(20)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_HARDSTONE = ConfiguredOres.ORE_HARDSTONE.withPlacement(CountPlacementModifier.of(16), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_COAL = ConfiguredOres.TOPPING_COAL.withPlacement(CountPlacementModifier.of(25), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(0), YOffset.fixed(200)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_IRON = ConfiguredOres.TOPPING_IRON.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_GOLD = ConfiguredOres.TOPPING_GOLD.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-30), YOffset.fixed(5)), BiomePlacementModifier.of());
    public static final PlacedFeature DISK_SAND = ConfiguredOres.DISK_SAND.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final PlacedFeature AMANITA = ConfiguredTrees.AMANITA.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_FRUIT = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_LARGE = ConfiguredTrees.AMANITA_LARGE.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_OAKY = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_OAKY_FRUIT = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature DARK_AMANITA = ConfiguredTrees.DARK_AMANITA.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature DARK_AMANITA_TALL = ConfiguredTrees.DARK_AMANITA_TALL.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));

    public static void registerPlacedFeature(String id, PlacedFeature feature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Main.MOD_ID, id), feature);
    }

    public static void registerPlacedFeatures() {
        registerPlacedFeature("spring", SPRING);
        registerPlacedFeature("waterfall", WATERFALL);
        registerPlacedFeature("lake_lava", LAKE_LAVA);
        registerPlacedFeature("lake_poison", LAKE_POISON);
        registerPlacedFeature("pipe_default", PIPE_DEFAULT);
        registerPlacedFeature("pipe_underwater", PIPE_UNDERWATER);
        registerPlacedFeature("jellybeam", JELLYBEAM);
        registerPlacedFeature("block_single", BLOCK_SINGLE);
        registerPlacedFeature("block_line", BLOCK_LINE);
        registerPlacedFeature("block_line_deep", BLOCK_LINE_DEEP);
        registerPlacedFeature("block_pile", BLOCK_PILE);
        registerPlacedFeature("block_pile_rare", BLOCK_PILE_RARE);
        registerPlacedFeature("vanillate_topping", VANILLATE_TOPPING);
        registerPlacedFeature("ore_crumble", ORE_CRUMBLE);
        registerPlacedFeature("ore_bronze", ORE_BRONZE);
        registerPlacedFeature("ore_amethyst", ORE_AMETHYST);
        registerPlacedFeature("ore_hardstone", ORE_HARDSTONE);
        registerPlacedFeature("toppings_coal", TOPPING_COAL);
        registerPlacedFeature("toppings_iron", TOPPING_IRON);
        registerPlacedFeature("toppings_gold", TOPPING_GOLD);
        registerPlacedFeature("disk_sand", DISK_SAND);
        registerPlacedFeature("amanita", AMANITA);
        registerPlacedFeature("amanita_fruit", AMANITA_FRUIT);
        registerPlacedFeature("amanita_large", AMANITA_LARGE);
        registerPlacedFeature("amanita_oaky", AMANITA_OAKY);
        registerPlacedFeature("amanita_oaky_fruit", AMANITA_OAKY_FRUIT);
        registerPlacedFeature("dark_amanita", DARK_AMANITA);
        registerPlacedFeature("dark_amanita_tall", DARK_AMANITA_TALL);
    }
}

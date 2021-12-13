package com.dayofpi.super_block_world.main.registry.world;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.block.ModBlocks;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.main.registry.world.configured_feature.*;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

import java.util.List;

public class ModPlacedFeature {
    private static final List<Block> PLACE_CRYSTAL_ON = List.of(ModBlocks.VANILLATE, ModBlocks.HARDSTONE, ModBlocks.ROYALITE);

    public static final PlacedFeature SEAGRASS = OceanConfiguredFeatures.SEAGRASS_SHORT.withPlacement(CountPlacementModifier.of(25), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_FEW = ModFeature.COLUMN_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CORAL_MANY = ModFeature.COLUMN_CORAL.configure(FeatureConfig.DEFAULT).withPlacement(SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
    public static final PlacedFeature SPRING = ConfiguredMisc.SPRING.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_LAVA = ConfiguredMisc.LAKE_LAVA.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature LAKE_POISON = ConfiguredMisc.LAKE_POISON.withPlacement(RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(200)), BiomePlacementModifier.of());

    private static final PlacedFeature AMETHYST_CEILING = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 7, 3, () -> ConfiguredMisc.AMETHYST_CEILING.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, 1, 0))))))).withPlacement();
    private static final PlacedFeature AMETHYST_FLOOR = Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig(16, 7, 3, () -> ConfiguredMisc.AMETHYST_FLOOR.withPlacement(BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(PLACE_CRYSTAL_ON, new Vec3i(0, -1, 0))))))).withPlacement();

    public static final PlacedFeature AMETHYST = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(() -> AMETHYST_CEILING, () -> AMETHYST_FLOOR)).withPlacement(CountPlacementModifier.of(UniformIntProvider.create(3, 5)), SquarePlacementModifier.of(), HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(90))), BiomePlacementModifier.of());
    public static final PlacedFeature AMETHYST_EXTRA = Feature.RANDOM_BOOLEAN_SELECTOR.configure(new RandomBooleanFeatureConfig(() -> AMETHYST_CEILING, () -> AMETHYST_FLOOR)).withPlacement(CountPlacementModifier.of(ConstantIntProvider.create(8)), SquarePlacementModifier.of(), HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.fixed(40), YOffset.fixed(200))), BiomePlacementModifier.of());
    public static final PlacedFeature CAVE_DECORATION = ConfiguredMisc.CAVE_DECORATION.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)));
    public static final PlacedFeature WARP_PIPE_WATER = ModFeature.WARP_PIPE_WATER.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.belowTop(0)), BiomePlacementModifier.of());

    public static final PlacedFeature JELLYBEAM = ConfiguredBlocks.JELLYBEAM.withPlacement(RarityFilterPlacementModifier.of(13), CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_SINGLE = ConfiguredBlocks.BLOCK_SINGLE.withPlacement(RarityFilterPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(85)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_SURFACE = ConfiguredBlocks.BLOCK_LINE_SURFACE.withPlacement(RarityFilterPlacementModifier.of(38), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(63), YOffset.fixed(90)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());
    public static final PlacedFeature BLOCK_LINE_DEEP = ConfiguredBlocks.BLOCK_LINE_DEEP.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-30), YOffset.fixed(62)), BlockFilterPlacementModifier.of(BlockPredicate.IS_AIR_OR_WATER), BiomePlacementModifier.of());

    public static final PlacedFeature VANILLATE_TOPPING = ConfiguredOres.VANILLATE_TOPPING.withPlacement(CountPlacementModifier.of(35), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature QUICKSAND = ConfiguredOres.QUICKSAND.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_CRUMBLE = ConfiguredOres.ORE_CRUMBLE.withPlacement(CountPlacementModifier.of(170), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_BRONZE = ConfiguredOres.ORE_BRONZE.withPlacement(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(12), YOffset.fixed(96)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_AMETHYST = ConfiguredOres.ORE_AMETHYST.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(20)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_CERISE = ConfiguredOres.ORE_CERISE.withPlacement(CountPlacementModifier.of(7), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-30), YOffset.fixed(-7)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_HARDSTONE = ConfiguredOres.ORE_HARDSTONE.withPlacement(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_COAL = ConfiguredOres.TOPPING_COAL.withPlacement(CountPlacementModifier.of(25), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(0), YOffset.fixed(200)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_IRON = ConfiguredOres.TOPPING_IRON.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_GOLD = ConfiguredOres.TOPPING_GOLD.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-30), YOffset.fixed(50)), BiomePlacementModifier.of());
    public static final PlacedFeature DISK_SAND = ConfiguredOres.DISK_SAND.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final PlacedFeature AMANITA = ConfiguredTrees.AMANITA.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_FRUIT = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_LARGE = ConfiguredTrees.AMANITA_LARGE.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_MOUNTAIN = ConfiguredTrees.AMANITA_MOUNTAIN.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_OAKY = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature AMANITA_OAKY_FRUIT = ConfiguredTrees.AMANITA_FRUIT.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature DARK_AMANITA = ConfiguredTrees.DARK_AMANITA.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.DARK_AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacedFeature DARK_AMANITA_TALL = ConfiguredTrees.DARK_AMANITA_TALL.withBlockPredicateFilter(BlockPredicate.wouldSurvive(PlantBlocks.DARK_AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));

    public static final PlacedFeature HUGE_RED_MUSHROOM_FLAT = ConfiguredTrees.HUGE_RED_MUSHROOM_FLAT.withPlacement();
    public static final PlacedFeature HUGE_GREEN_MUSHROOM_FLAT = ConfiguredTrees.HUGE_GREEN_MUSHROOM_FLAT.withPlacement();

    public static final PlacedFeature HUGE_YELLOW_MUSHROOM = ConfiguredTrees.HUGE_YELLOW_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_GREEN_MUSHROOM = ConfiguredTrees.HUGE_GREEN_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_PINK_MUSHROOM = ConfiguredTrees.HUGE_PINK_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_PURPLE_MUSHROOM = ConfiguredTrees.HUGE_PURPLE_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_ORANGE_MUSHROOM = ConfiguredTrees.HUGE_ORANGE_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_BROWN_MUSHROOM = ConfiguredTrees.HUGE_BROWN_MUSHROOM.withPlacement();
    public static final PlacedFeature HUGE_RED_MUSHROOM = ConfiguredTrees.HUGE_RED_MUSHROOM.withPlacement();
    
    public static final PlacedFeature HORSETAIL = ConfiguredVegetation.HORSETAIL_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature STUMP = ConfiguredVegetation.STUMP_PATCH.withPlacement(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature CACTUS = ConfiguredVegetation.CACTUS_PATCH.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
    public static final PlacedFeature BEANSTALK_NOT_BUDDING = ConfiguredVegetation.BEANSTALK_NOT_BUDDING.withPlacement();
    public static final PlacedFeature BEANSTALK_BUDDING = ConfiguredVegetation.BEANSTALK_BUDDING.withPlacement();
    public static final PlacedFeature MUNCHER_FEW = ConfiguredVegetation.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature MUNCHER_MANY = ConfiguredVegetation.MUNCHER.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWERBED = ConfiguredVegetation.FLOWERBED.withPlacement();
    public static final PlacedFeature PLANT_GRASSLAND = ConfiguredVegetation.PLANT_GRASSLAND.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANT_GORGE = ConfiguredVegetation.PLANT_GORGE.withPlacement(CountPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature PLANT_DESERT = ConfiguredVegetation.PLANT_DESERT.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature MUSHROOM_GRASSLAND = ConfiguredVegetation.MUSHROOM_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWER_GRASSLAND = ConfiguredVegetation.FLOWER_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature FLOWER_GORGE = ConfiguredVegetation.FLOWER_GORGE.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GRASSLAND = ConfiguredVegetation.VEGETATION_GRASSLAND.withPlacement(RarityFilterPlacementModifier.of(3), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GORGE = ConfiguredVegetation.VEGETATION_GORGE.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature VEGETATION_GORGE_TALL = ConfiguredVegetation.VEGETATION_GORGE_TALL.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(11), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BiomePlacementModifier.of());
    public static final PlacedFeature GIANT_CAVE_MUSHROOM = ConfiguredVegetation.VEGETATION_CAVE.withPlacement(RarityFilterPlacementModifier.of(5), CountPlacementModifier.of(100), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-5), YOffset.fixed(70)), BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(List.of(ModBlocks.VANILLATE, ModBlocks.HARDSTONE), new Vec3i(0, -1, 0)))));

    public static void registerPlacedFeature(String id, PlacedFeature feature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Main.MOD_ID, id), feature);
    }

    public static void registerPlacedFeatures() {
        registerPlacedFeature("seagrass", SEAGRASS);
        registerPlacedFeature("coral_few", CORAL_FEW);
        registerPlacedFeature("coral_many", CORAL_MANY);
        registerPlacedFeature("spring", SPRING);
        registerPlacedFeature("lake_lava", LAKE_LAVA);
        registerPlacedFeature("lake_poison", LAKE_POISON);
        registerPlacedFeature("patch_cave_crystals", AMETHYST);
        registerPlacedFeature("amethyst_extra", AMETHYST_EXTRA);
        registerPlacedFeature("cave_decoration", CAVE_DECORATION);
        registerPlacedFeature("pipe_underwater", WARP_PIPE_WATER);
        registerPlacedFeature("jellybeam", JELLYBEAM);
        registerPlacedFeature("block_single", BLOCK_SINGLE);
        registerPlacedFeature("block_line", BLOCK_LINE_SURFACE);
        registerPlacedFeature("block_line_deep", BLOCK_LINE_DEEP);
        registerPlacedFeature("vanillate_topping", VANILLATE_TOPPING);
        registerPlacedFeature("quicksand", QUICKSAND);
        registerPlacedFeature("ore_crumble", ORE_CRUMBLE);
        registerPlacedFeature("ore_bronze", ORE_BRONZE);
        registerPlacedFeature("ore_amethyst", ORE_AMETHYST);
        registerPlacedFeature("ore_cerise", ORE_CERISE);
        registerPlacedFeature("ore_hardstone", ORE_HARDSTONE);
        registerPlacedFeature("toppings_coal", TOPPING_COAL);
        registerPlacedFeature("toppings_iron", TOPPING_IRON);
        registerPlacedFeature("toppings_gold", TOPPING_GOLD);
        registerPlacedFeature("disk_sand", DISK_SAND);
        registerPlacedFeature("huge_red_mushroom_flat", HUGE_RED_MUSHROOM_FLAT);
        registerPlacedFeature("huge_green_mushroom_flat", HUGE_GREEN_MUSHROOM_FLAT);;
        registerPlacedFeature("huge_yellow_mushroom", HUGE_YELLOW_MUSHROOM);
        registerPlacedFeature("huge_green_mushroom", HUGE_GREEN_MUSHROOM);
        registerPlacedFeature("huge_pink_mushroom", HUGE_PINK_MUSHROOM);
        registerPlacedFeature("huge_purple_mushroom", HUGE_PURPLE_MUSHROOM);
        registerPlacedFeature("huge_orange_mushroom", HUGE_ORANGE_MUSHROOM);
        registerPlacedFeature("huge_brown_mushroom", HUGE_BROWN_MUSHROOM);
        registerPlacedFeature("huge_red_mushroom", HUGE_RED_MUSHROOM);
        registerPlacedFeature("amanita", AMANITA);
        registerPlacedFeature("amanita_fruit", AMANITA_FRUIT);
        registerPlacedFeature("amanita_large", AMANITA_LARGE);
        registerPlacedFeature("amanita_mountain", AMANITA_MOUNTAIN);
        registerPlacedFeature("amanita_oaky", AMANITA_OAKY);
        registerPlacedFeature("amanita_oaky_fruit", AMANITA_OAKY_FRUIT);
        registerPlacedFeature("dark_amanita", DARK_AMANITA);
        registerPlacedFeature("dark_amanita_tall", DARK_AMANITA_TALL);
        registerPlacedFeature("horsetail", HORSETAIL);
        registerPlacedFeature("stump", STUMP);
        registerPlacedFeature("cactus", CACTUS);
        registerPlacedFeature("beanstalk", BEANSTALK_NOT_BUDDING);
        registerPlacedFeature("beanstalk_budding", BEANSTALK_BUDDING);
        registerPlacedFeature("muncher_few", MUNCHER_FEW);
        registerPlacedFeature("muncher_many", MUNCHER_MANY);
        registerPlacedFeature("flowerbed", FLOWERBED);
        registerPlacedFeature("plant_grassland", PLANT_GRASSLAND);
        registerPlacedFeature("plant_gorge", PLANT_GORGE);
        registerPlacedFeature("plant_desert", PLANT_DESERT);
        registerPlacedFeature("mushroom_grassland", MUSHROOM_GRASSLAND);
        registerPlacedFeature("flower_grassland", FLOWER_GRASSLAND);
        registerPlacedFeature("flower_gorge", FLOWER_GORGE);
        registerPlacedFeature("vegetation_grassland", VEGETATION_GRASSLAND);
        registerPlacedFeature("vegetation_gorge", VEGETATION_GORGE);
        registerPlacedFeature("vegetation_gorge_tall", VEGETATION_GORGE_TALL);
        registerPlacedFeature("giant_cave_mushroom", GIANT_CAVE_MUSHROOM);
    }
}

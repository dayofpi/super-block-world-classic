package com.dayofpi.super_block_world.registry.world.feature.placed;

import com.dayofpi.super_block_world.registry.world.feature.configured.ConfiguredOres;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

public class PlacedOres {
    public static final PlacedFeature ORE_FROZEN = ConfiguredOres.ORE_FROZEN.withPlacement(CountPlacementModifier.of(40), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_CRUMBLE = ConfiguredOres.ORE_CRUMBLE.withPlacement(CountPlacementModifier.of(170), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_BRONZE = ConfiguredOres.ORE_BRONZE.withPlacement(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(12), YOffset.fixed(96)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_VANILLATE_COAL = ConfiguredOres.ORE_VANILLATE_COAL.withPlacement(CountPlacementModifier.of(15), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(12), YOffset.fixed(96)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_VANILLATE_COAL_EXTRA = ConfiguredOres.ORE_VANILLATE_COAL.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(63), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_VANILLATE_IRON = ConfiguredOres.ORE_VANILLATE_IRON.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(63)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_CERISE = ConfiguredOres.ORE_CERISE.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-30), YOffset.fixed(-7)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_TOADSTONE = ConfiguredOres.ORE_TOADSTONE.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(55), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_CHISELED_TOADSTONE = ConfiguredOres.ORE_CHISELED_TOADSTONE.withPlacement(CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(68), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_GLOOMSTONE = ConfiguredOres.ORE_GLOOMSTONE.withPlacement(CountPlacementModifier.of(5), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.fixed(50)), BiomePlacementModifier.of());
    public static final PlacedFeature ORE_HARDSTONE = ConfiguredOres.ORE_HARDSTONE.withPlacement(CountPlacementModifier.of(14), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_GOLD = ConfiguredOres.TOPPING_GOLD.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-30), YOffset.fixed(50)), BiomePlacementModifier.of());
    public static final PlacedFeature TOPPING_AMETHYST = ConfiguredOres.TOPPING_AMETHYST.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(20)), BiomePlacementModifier.of());
    public static final PlacedFeature DISK_SAND = ConfiguredOres.DISK_SAND.withPlacement(CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final PlacedFeature DISK_SEASTONE = ConfiguredOres.DISK_SEASTONE.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
}
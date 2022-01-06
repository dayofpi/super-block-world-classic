package com.dayofpi.super_block_world.main.registry.world.feature.placed;

import com.dayofpi.super_block_world.main.registry.world.feature.FeatureRegistry;
import com.dayofpi.super_block_world.main.registry.world.feature.configured.ConfiguredPipes;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public class PlacedPipes {
    public static final PlacedFeature PIPE = FeatureRegistry.PIPE.configure(FeatureConfig.DEFAULT).withPlacement();
    public static final PlacedFeature PIPE_PATCH_HIGH = ConfiguredPipes.PIPE_PATCH.withPlacement(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature PIPE_PATCH_LOW = ConfiguredPipes.PIPE_PATCH.withPlacement(RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.fixed(0)), BiomePlacementModifier.of());
    public static final PlacedFeature UNDERWATER_PIPE = FeatureRegistry.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(16), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature OCEAN_FLOOR_PIPE = FeatureRegistry.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
}

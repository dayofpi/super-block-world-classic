package com.dayofpi.super_block_world.world.feature.placed;

import com.dayofpi.super_block_world.registry.block.ColoredBlocks;
import com.dayofpi.super_block_world.world.feature.FeatureInit;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredPipes;
import com.dayofpi.super_block_world.world.feature.utility.feature_config.PipeFeatureConfig;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class PlacedPipes {
    public static final PlacedFeature GREEN_PIPE = FeatureInit.PIPE.configure(new PipeFeatureConfig(BlockStateProvider.of(ColoredBlocks.GREEN_WARP_PIPE), BlockStateProvider.of(ColoredBlocks.GREEN_PIPE_BODY))).withPlacement();
    public static final PlacedFeature RED_PIPE = FeatureInit.PIPE.configure(new PipeFeatureConfig(BlockStateProvider.of(ColoredBlocks.RED_WARP_PIPE), BlockStateProvider.of(ColoredBlocks.RED_PIPE_BODY))).withPlacement();
    public static final PlacedFeature GREEN_PIPE_PATCH = ConfiguredPipes.GREEN_PIPE_PATCH.withPlacement(RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(54), YOffset.fixed(90)), BiomePlacementModifier.of());
    public static final PlacedFeature RED_PIPE_PATCH = ConfiguredPipes.RED_PIPE_PATCH.withPlacement(RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(54)), BiomePlacementModifier.of());
    public static final PlacedFeature UNDERWATER_PIPE = FeatureInit.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(CountPlacementModifier.of(30), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.belowTop(0)), BiomePlacementModifier.of());
    public static final PlacedFeature OCEAN_FLOOR_PIPE = FeatureInit.UNDERWATER_PIPE.configure(FeatureConfig.DEFAULT).withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
}
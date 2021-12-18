package com.dayofpi.super_block_world.main.registry.world.feature.utility;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifier;

import java.util.List;

public class PlacementModifiers {
    private static final Vec3i UNDER = new Vec3i(0, -1, 0);
    public static final PlacementModifier IN_CANYON = BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR_OR_WATER, BlockPredicate.matchingBlocks(List.of(BlockRegistry.VANILLATE, BlockRegistry.HARDSTONE), UNDER)));
    public static final PlacementModifier ON_SOIL_AND_LEAVES = BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.eitherOf(BlockPredicate.matchingBlockTag(BlockTags.DIRT, UNDER), BlockPredicate.matchingBlockTag(BlockTags.LEAVES, UNDER))));
    public static final PlacementModifier SAPLING_SURVIVES = BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
}

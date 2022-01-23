package com.dayofpi.super_block_world.world.feature.utility;

import com.dayofpi.super_block_world.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BlockFilterPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifier;

import java.util.List;

public class PlacementModifiers {
    private static final Vec3i UNDER = new Vec3i(0, -1, 0);
    public static final PlacementModifier IN_CANYON = BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR_OR_WATER, BlockPredicate.matchingBlocks(List.of(BlockInit.VANILLATE, BlockInit.TOPPED_VANILLATE, BlockInit.HARDSTONE), UNDER)));
    public static final PlacementModifier SAPLING_SURVIVES = BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PlantBlocks.AMANITA_SAPLING.getDefaultState(), BlockPos.ORIGIN));
    public static final PlacementModifier MUSHROOM_SURVIVES = BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(MushroomBlocks.GREEN_MUSHROOM.getDefaultState(), BlockPos.ORIGIN));
}

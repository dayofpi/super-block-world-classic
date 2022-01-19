/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.world.feature.utility.type;

import com.dayofpi.super_block_world.common.blocks.plant.AmanitaCarpetBlock;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AmanitaCarpetFeature
extends Feature<GlowLichenFeatureConfig> {
    public AmanitaCarpetFeature(Codec<GlowLichenFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<GlowLichenFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        GlowLichenFeatureConfig glowLichenFeatureConfig = context.getConfig();
        if (AmanitaCarpetFeature.isNotAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        }
        List<Direction> list = AmanitaCarpetFeature.shuffleDirections(glowLichenFeatureConfig, random);
        if (AmanitaCarpetFeature.generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), glowLichenFeatureConfig, random, list)) {
            return true;
        }
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        block0: for (Direction direction : list) {
            mutable.set(blockPos);
            List<Direction> list2 = AmanitaCarpetFeature.shuffleDirections(glowLichenFeatureConfig, random, direction.getOpposite());
            for (int i = 0; i < glowLichenFeatureConfig.searchRange; ++i) {
                mutable.set(blockPos, direction);
                BlockState blockState = structureWorldAccess.getBlockState(mutable);
                if (AmanitaCarpetFeature.isNotAirOrWater(blockState) && !blockState.isOf(PlantBlocks.AMANITA_CARPET)) continue block0;
                if (!AmanitaCarpetFeature.generate(structureWorldAccess, mutable, blockState, glowLichenFeatureConfig, random, list2)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean generate(StructureWorldAccess world, BlockPos pos, BlockState state, GlowLichenFeatureConfig config, Random random, List<Direction> directions) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutable.set(pos, direction));
            if (!config.canPlaceOn.contains(blockState.getBlock())) continue;
            AmanitaCarpetBlock amanitaCarpetBlock = (AmanitaCarpetBlock) PlantBlocks.AMANITA_CARPET;
            BlockState blockState2 = amanitaCarpetBlock.withDirection(state, world, pos, direction);
            if (blockState2 == null) {
                return false;
            }
            world.setBlockState(pos, blockState2, Block.NOTIFY_ALL);
            world.getChunk(pos).markBlockForPostProcessing(pos);
            if (random.nextFloat() < config.spreadChance) {
                amanitaCarpetBlock.trySpreadRandomly(blockState2, world, pos, direction, random, true);
            }
            return true;
        }
        return false;
    }

    public static List<Direction> shuffleDirections(GlowLichenFeatureConfig config, Random random) {
        ArrayList<Direction> list = Lists.newArrayList(config.directions);
        Collections.shuffle(list, random);
        return list;
    }

    public static List<Direction> shuffleDirections(GlowLichenFeatureConfig config, Random random, Direction excluded) {
        List<Direction> list = config.directions.stream().filter(direction -> direction != excluded).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list;
    }

    private static boolean isNotAirOrWater(BlockState state) {
        return !state.isAir() && !state.isOf(Blocks.WATER);
    }
}


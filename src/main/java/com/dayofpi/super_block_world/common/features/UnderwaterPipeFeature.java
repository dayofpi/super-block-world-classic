package com.dayofpi.super_block_world.common.features;

import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class UnderwaterPipeFeature extends Feature<DefaultFeatureConfig> {
    public UnderwaterPipeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int i = 0;
        StructureWorldAccess world = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        BlockPos floor = context.getOrigin().down();

        if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(floor).isSolidBlock(world, floor)) {
            BlockState warpPipeState = WarpPipeFeature.createPipeState(ModBlocks.GREEN_WARP_PIPE, true);
            BlockState pipeBodyState = WarpPipeFeature.createPipeState(ModBlocks.GREEN_PIPE_BODY, true);
            Random random = context.getRandom();

            if (random.nextInt(4) == 0) {
                warpPipeState = WarpPipeFeature.createPipeState(ModBlocks.RED_WARP_PIPE, true);
                pipeBodyState = WarpPipeFeature.createPipeState(ModBlocks.RED_PIPE_BODY, true);
            } else if (random.nextInt(4) == 0) {
                warpPipeState = WarpPipeFeature.createPipeState(ModBlocks.YELLOW_WARP_PIPE, true);
                pipeBodyState = WarpPipeFeature.createPipeState(ModBlocks.YELLOW_PIPE_BODY, true);
            } else if (random.nextInt(4) == 0) {
                warpPipeState = WarpPipeFeature.createPipeState(ModBlocks.BLUE_WARP_PIPE, true);
                pipeBodyState = WarpPipeFeature.createPipeState(ModBlocks.BLUE_PIPE_BODY, true);
            }

            int height = 1 + random.nextInt(6);
            for (int l = 0; l <= height; ++l) {
                if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.up()).isOf(Blocks.WATER)) {
                    if (l == height) {
                        world.setBlockState(blockPos, warpPipeState, Block.NOTIFY_LISTENERS);
                        BubbleColumnBlock.update(world, blockPos.up(), warpPipeState);
                        ++i;
                    } else {
                        world.setBlockState(blockPos, pipeBodyState, Block.NOTIFY_LISTENERS);
                    }
                } else if (l > 0) {
                    BlockPos blockPos3 = blockPos.down();
                    if (!world.getBlockState(blockPos3.down()).isIn(ModTags.WARP_PIPES)) {
                        world.setBlockState(blockPos3, warpPipeState, Block.NOTIFY_LISTENERS);
                        BubbleColumnBlock.update(world, blockPos3.up(), warpPipeState);
                        ++i;
                    }
                    break;
                }

                blockPos = blockPos.up();
            }
        }

        return i > 0;
    }
}

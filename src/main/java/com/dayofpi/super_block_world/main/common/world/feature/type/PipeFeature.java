package com.dayofpi.super_block_world.main.common.world.feature.type;

import com.dayofpi.super_block_world.main.registry.block.ColoredBlocks;
import com.dayofpi.super_block_world.main.registry.misc.TagRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PipeFeature extends Feature<DefaultFeatureConfig> {
	public PipeFeature(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		int i = 0;
		StructureWorldAccess world = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		BlockPos floor = context.getOrigin().down();
		Random random = context.getRandom();

		if (world.getBlockState(blockPos).isAir() && world.getBlockState(floor).isSolidBlock(world, floor)) {
			BlockState warpPipeState = ColoredBlocks.GREEN_WARP_PIPE.getDefaultState();
			BlockState pipeBodyState = ColoredBlocks.GREEN_PIPE_BODY.getDefaultState();

			if (random.nextInt(6) == 0) {
				warpPipeState = ColoredBlocks.RED_WARP_PIPE.getDefaultState();
				pipeBodyState = ColoredBlocks.RED_PIPE_BODY.getDefaultState();
			} else if (random.nextInt(6) == 0) {
				warpPipeState = ColoredBlocks.YELLOW_WARP_PIPE.getDefaultState();
				pipeBodyState = ColoredBlocks.YELLOW_PIPE_BODY.getDefaultState();
			} else if (random.nextInt(6) == 0) {
				warpPipeState = ColoredBlocks.BLUE_WARP_PIPE.getDefaultState();
				pipeBodyState = ColoredBlocks.BLUE_PIPE_BODY.getDefaultState();
			}

			int k = 1 + random.nextInt(6);

			for(int l = 0; l <= k; ++l) {
				if (world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.up()).isAir()) {
					if (l == k) {
						world.setBlockState(blockPos, warpPipeState, Block.NOTIFY_LISTENERS);
						++i;
					} else {
						world.setBlockState(blockPos, pipeBodyState, Block.NOTIFY_LISTENERS);
					}
				} else if (l > 0) {
					BlockPos blockPos3 = blockPos.down();
					if (!world.getBlockState(blockPos3.down()).isIn(TagRegistry.WARP_PIPES)) {
						world.setBlockState(blockPos3, warpPipeState, Block.NOTIFY_LISTENERS);
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

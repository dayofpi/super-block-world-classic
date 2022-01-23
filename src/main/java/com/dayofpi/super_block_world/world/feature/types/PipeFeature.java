package com.dayofpi.super_block_world.world.feature.types;

import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.world.feature.utility.feature_config.PipeFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PipeFeature extends Feature<PipeFeatureConfig> {
	public PipeFeature(Codec<PipeFeatureConfig> codec) {
		super(codec);
	}

	public boolean generate(FeatureContext<PipeFeatureConfig> context) {
		int i = 0;
		StructureWorldAccess world = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		BlockPos floor = context.getOrigin().down();
		Random random = context.getRandom();

		if (world.getBlockState(blockPos).isAir() && world.getBlockState(floor).isSolidBlock(world, floor)) {
			BlockState warpPipeState = context.getConfig().pipeProvider.getBlockState(context.getRandom(), blockPos);
			BlockState pipeBodyState = context.getConfig().bodyProvider.getBlockState(context.getRandom(), blockPos);

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
					if (!world.getBlockState(blockPos3.down()).isIn(TagInit.WARP_PIPES)) {
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

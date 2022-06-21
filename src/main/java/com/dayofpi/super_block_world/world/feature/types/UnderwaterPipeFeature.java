package com.dayofpi.super_block_world.world.feature.types;

import com.dayofpi.super_block_world.registry.block.ColoredBlocks;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class UnderwaterPipeFeature extends Feature<DefaultFeatureConfig> {
	public UnderwaterPipeFeature(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		int i = 0;
		StructureWorldAccess world = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		BlockPos floor = context.getOrigin().down();
		Random random = context.getRandom();

		if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(floor).isSolidBlock(world, floor)) {
			BlockState warpPipeState = ColoredBlocks.GREEN_WARP_PIPE.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
			BlockState pipeBodyState = ColoredBlocks.GREEN_PIPE_BODY.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);

			if (random.nextInt(4) == 0) {
				warpPipeState = ColoredBlocks.RED_WARP_PIPE.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
				pipeBodyState = ColoredBlocks.RED_PIPE_BODY.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
			} else if (random.nextInt(4) == 0) {
				warpPipeState = ColoredBlocks.YELLOW_WARP_PIPE.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
				pipeBodyState = ColoredBlocks.YELLOW_PIPE_BODY.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
			}  else if (random.nextInt(4) == 0) {
				warpPipeState = ColoredBlocks.BLUE_WARP_PIPE.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
				pipeBodyState = ColoredBlocks.BLUE_PIPE_BODY.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
			}

			int k = 1 + random.nextInt(6);

			for(int l = 0; l <= k; ++l) {
				if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.up()).isOf(Blocks.WATER)) {
					if (l == k) {
						world.setBlockState(blockPos, warpPipeState, Block.NOTIFY_LISTENERS);
						BubbleColumnBlock.update(world, blockPos.up(), warpPipeState);
						++i;
					} else {
						world.setBlockState(blockPos, pipeBodyState, Block.NOTIFY_LISTENERS);
					}
				} else if (l > 0) {
					BlockPos blockPos3 = blockPos.down();
					if (!world.getBlockState(blockPos3.down()).isIn(TagInit.WARP_PIPES)) {
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

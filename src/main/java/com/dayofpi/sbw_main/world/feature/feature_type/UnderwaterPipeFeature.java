package com.dayofpi.sbw_main.world.feature.feature_type;

import com.dayofpi.sbw_main.registry.block.ModBlocks;
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
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Predicate;

public class UnderwaterPipeFeature extends Feature<DefaultFeatureConfig> {
	public UnderwaterPipeFeature(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		int i = 0;
		StructureWorldAccess world = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		DefaultFeatureConfig config = context.getConfig();
		Random random = context.getRandom();

		if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.down()).isSolidBlock(world, blockPos.down())) {
			BlockState blockState = ModBlocks.WARP_PIPE.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
			BlockState blockState2 = ModBlocks.WARP_PIPE_BODY.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, true);
			int k = 1 + random.nextInt(6);

			for(int l = 0; l <= k; ++l) {
				if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.up()).isOf(Blocks.WATER)) {
					if (l == k) {
						world.setBlockState(blockPos, blockState, Block.NOTIFY_LISTENERS);
						BubbleColumnBlock.update(world, blockPos.up(), blockState);
						++i;
					} else {
						world.setBlockState(blockPos, blockState2, Block.NOTIFY_LISTENERS);
					}
				} else if (l > 0) {
					BlockPos blockPos3 = blockPos.down();
					if (!world.getBlockState(blockPos3.down()).isOf(ModBlocks.WARP_PIPE)) {
						world.setBlockState(blockPos3, blockState, Block.NOTIFY_LISTENERS);
						BubbleColumnBlock.update(world, blockPos3.up(), blockState);
						++i;
					}
					break;
				}

				blockPos = blockPos.up();
			}
		}

		return i > 0;
	}

	private static OptionalInt getFloorHeight(StructureWorldAccess world, BlockPos pos, DefaultFeatureConfig config) {
		Predicate<BlockState> predicate = (state) -> state.isOf(Blocks.WATER);
		Predicate<BlockState> predicate2 = (state) -> !state.isOf(Blocks.WATER);
		Optional<CaveSurface> optional = CaveSurface.create(world, pos, 12, predicate, predicate2);
		return optional.map(CaveSurface::getFloorHeight).orElseGet(OptionalInt::empty);
	}
}

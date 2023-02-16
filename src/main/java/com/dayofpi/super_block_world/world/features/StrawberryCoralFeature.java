package com.dayofpi.super_block_world.world.features;

import com.dayofpi.super_block_world.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class StrawberryCoralFeature extends Feature<DefaultFeatureConfig> {
    private static final BlockState STRAWBERRY_CORAL = ModBlocks.STRAWBERRY_CORAL.getDefaultState();
    public StrawberryCoralFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        int length = 0;
        int topY = world.getTopY(Heightmap.Type.OCEAN_FLOOR, origin.getX(), origin.getZ());
        BlockPos blockPos = new BlockPos(origin.getX(), topY, origin.getZ());

        if (world.getBlockState(blockPos).isOf(Blocks.WATER)) {
            int i = 1 + random.nextInt(10);

            for (int l = 0; l <= i; ++l) {
                if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.up()).isOf(Blocks.WATER) && STRAWBERRY_CORAL.canPlaceAt(world, blockPos)) {
                    world.setBlockState(blockPos, STRAWBERRY_CORAL, Block.NOTIFY_LISTENERS);
                    if (l == i) {
                        ++length;
                    }
                } else if (l > 0) {
                    BlockPos floor = blockPos.down();
                    if (STRAWBERRY_CORAL.canPlaceAt(world, floor)) {
                        world.setBlockState(floor, STRAWBERRY_CORAL, Block.NOTIFY_LISTENERS);
                        ++length;
                    }
                    break;
                }

                blockPos = blockPos.up();
            }
        }

        return length > 0;
    }
}

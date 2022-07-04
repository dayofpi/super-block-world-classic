package com.dayofpi.super_block_world.common.features;

import com.dayofpi.super_block_world.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BellTreeFeature extends Feature<DefaultFeatureConfig> {
    private static final BlockState BELL_CAP = ModBlocks.BELL_CAP.getDefaultState().with(Properties.PERSISTENT, true);
    private static final BlockState DARKENED_BELL_CAP = ModBlocks.DARKENED_BELL_CAP.getDefaultState().with(Properties.PERSISTENT, true);
    public BellTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable mutable) {
        int i = pos.getY();
        if (i >= world.getBottomY() + 1 && i + height + 1 < world.getTopY()) {
            BlockState blockState = world.getBlockState(pos.down());
            if (!isSoil(blockState)) {
                return false;
            } else {
                for(int j = 0; j <= height; ++j) {
                    int maxCapRadius = 3;

                    for(int l = -maxCapRadius; l <= maxCapRadius; ++l) {
                        for(int m = -maxCapRadius; m <= maxCapRadius; ++m) {
                            BlockState blockState2 = world.getBlockState(mutable.set(pos.up(4), l, j, m));
                            if (!blockState2.isAir() && !blockState2.isIn(BlockTags.LEAVES)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        } else return false;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        int i = UniformIntProvider.create(7, 10).get(random);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        if (!this.canGenerate(structureWorldAccess, blockPos, i, mutable)) {
            return false;
        } else {
            this.generateCap(structureWorldAccess, blockPos, i, mutable);
            this.generateStem(structureWorldAccess, blockPos, i, mutable);
            return true;
        }
    }

    private void generateStem(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable mutable) {
        for(int i = 0; i < height; ++i) {
            mutable.set(pos).move(Direction.UP, i);
            if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                this.setBlockState(world, mutable, ModBlocks.BELL_LOG.getDefaultState());
            }
        }

    }

    protected void generateCap(WorldAccess world, BlockPos start, int y, BlockPos.Mutable mutable) {
        for(int i = 0; i < 4; ++i) {
            BlockPos inner = mutable.set(start).move(Direction.UP, i).offset(Direction.UP, y - 2);
            BlockState blockState = BELL_CAP;

            if (inner.getY() % 2 == 0)
                blockState = DARKENED_BELL_CAP;

            if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                this.setBlockState(world, inner, blockState);
                this.setBlockState(world, inner.add(1, 0, 0), blockState);
                this.setBlockState(world, inner.add(-1, 0, 0), blockState);
                this.setBlockState(world, inner.add(0, 0, 1), blockState);
                this.setBlockState(world, inner.add(0, 0, -1), blockState);
                this.setBlockState(world, inner.add(-1, 0, -1), blockState);
                this.setBlockState(world, inner.add(1, 0, -1), blockState);
                this.setBlockState(world, inner.add(1, 0, 1), blockState);
                this.setBlockState(world, inner.add(-1, 0, 1), blockState);

            }
            BlockPos outer = mutable.set(start).move(Direction.UP, i).offset(Direction.UP, y - 3);
            BlockPos rim = mutable.set(start).move(Direction.EAST, i).offset(Direction.UP, y - 3);
            BlockPos rim2 = mutable.set(start).move(Direction.NORTH, i).offset(Direction.UP, y - 3);

            if (outer.getY() % 2 == 0)
                blockState = DARKENED_BELL_CAP;
            else blockState = BELL_CAP;

            BlockState blockState1 = DARKENED_BELL_CAP;

            if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                this.setBlockState(world, rim.add(-2, 0, 3), blockState1);
                this.setBlockState(world, rim.add(-2, 0, -3), blockState1);


                this.setBlockState(world, rim2.add(3, 0, 2), blockState1);
                this.setBlockState(world, rim2.add(-3, 0, 2), blockState1);
                this.setBlockState(world, rim2.add(-3, 0, 1), blockState1);
                this.setBlockState(world, rim2.add(3, 0, 1), blockState1);
                this.setBlockState(world, rim2.add(-2, 0, 1), blockState1);
                this.setBlockState(world, rim2.add(2, 0, 1), blockState1);

                this.setBlockState(world, outer.add(2, 0, 0), blockState);
                this.setBlockState(world, outer.add(-2, 0, 0), blockState);
                this.setBlockState(world, outer.add(0, 0, 2), blockState);
                this.setBlockState(world, outer.add(0, 0, -2), blockState);

                this.setBlockState(world, outer.add(-1, 0, -2), blockState);
                this.setBlockState(world, outer.add(-1, 0, 2), blockState);
                this.setBlockState(world, outer.add(1, 0, -2), blockState);
                this.setBlockState(world, outer.add(1, 0, 2), blockState);

                this.setBlockState(world, outer.add(-2, 0, -1), blockState);
                this.setBlockState(world, outer.add(-2, 0, 1), blockState);
                this.setBlockState(world, outer.add(2, 0, -1), blockState);
                this.setBlockState(world, outer.add(2, 0, 1), blockState);
            }
        }
    }
}

package com.dayofpi.super_block_world.main.world.feature.type;

import com.dayofpi.super_block_world.main.world.feature.config.GiantMushroomFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class GiantMushroomFeature extends Feature<GiantMushroomFeatureConfig> {
    public GiantMushroomFeature(Codec<GiantMushroomFeatureConfig> codec) {
        super(codec);
    }

    private void generateStem(WorldAccess world, Random random, BlockPos pos, GiantMushroomFeatureConfig config, int height, BlockPos.Mutable mutable) {
        for(int i = 0; i < height; ++i) {
            mutable.set(pos).move(Direction.UP, i);
            if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                this.setBlockState(world, mutable, config.stemProvider.getBlockState(random, pos));
            }
        }

    }

    protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable mutable, GiantMushroomFeatureConfig config) {
        int i = pos.getY();
        if (i >= world.getBottomY() + 1 && i + height + 1 < world.getTopY()) {
            BlockState blockState = world.getBlockState(pos.down());
            if (!isSoil(blockState) && !blockState.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
                return false;
            } else {
                for(int j = 0; j <= height; ++j) {
                    int maxCapRadius = this.getCapSize(-1, -1, config.capRadius.getMax(), j);

                    if (config.isFlat) {
                        maxCapRadius = this.getFlatCapSize(-1, -1, config.capRadius.getMax(), j);
                    }

                    for(int l = -maxCapRadius; l <= maxCapRadius; ++l) {
                        for(int m = -maxCapRadius; m <= maxCapRadius; ++m) {
                            BlockState blockState2 = world.getBlockState(mutable.set(pos, l, j, m));
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

    protected int getFlatCapSize(int i, int j, int capSize, int y) {
        return y <= 3 ? 0 : capSize;
    }

    protected int getCapSize(int i, int j, int capSize, int y) {
        int k = 0;
        if (y < j && y >= j - 3) {
            k = capSize;
        } else if (y == j) {
            k = capSize;
        }

        return k;
    }


    public boolean generate(FeatureContext<GiantMushroomFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        GiantMushroomFeatureConfig hugeMushroomFeatureConfig = context.getConfig();
        int i = context.getConfig().stemHeight.get(random);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        if (!this.canGenerate(structureWorldAccess, blockPos, i, mutable, hugeMushroomFeatureConfig)) {
            return false;
        } else {
            if (context.getConfig().isFlat) {
                this.generateFlatCap(structureWorldAccess, random, blockPos, i, mutable, hugeMushroomFeatureConfig);
            } else {
                this.generateConeCap(structureWorldAccess, random, blockPos, i, mutable, hugeMushroomFeatureConfig);
            }
            this.generateStem(structureWorldAccess, random, blockPos, hugeMushroomFeatureConfig, i, mutable);
            return true;
        }
    }

    protected void generateConeCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, GiantMushroomFeatureConfig config) {
        for(int i = y - 3; i <= y; ++i) {
            int j = i < y ? config.capRadius.get(random) : config.capRadius.get(random) - 1;
            int k = config.capRadius.get(random) - 2;

            for(int l = -j; l <= j; ++l) {
                for(int m = -j; m <= j; ++m) {
                    boolean bl = l == -j;
                    boolean bl2 = l == j;
                    boolean bl3 = m == -j;
                    boolean bl4 = m == j;
                    boolean bl5 = bl || bl2;
                    boolean bl6 = bl3 || bl4;
                    if (i >= y || bl5 != bl6) {
                        mutable.set(start, l, i, m);
                        if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                            BlockState blockState = config.capProvider.getBlockState(random, start);
                            if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                                blockState = blockState.with(MushroomBlock.UP, i >= y - 1).with(MushroomBlock.WEST, l < -k).with(MushroomBlock.EAST, l > k).with(MushroomBlock.NORTH, m < -k).with(MushroomBlock.SOUTH, m > k);
                            }

                            this.setBlockState(world, mutable, blockState);
                        }
                    }
                }
            }
        }
    }

    protected void generateFlatCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, GiantMushroomFeatureConfig config) {
        int i = config.capRadius.get(random);

        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                boolean bl = j == -i;
                boolean bl2 = j == i;
                boolean bl3 = k == -i;
                boolean bl4 = k == i;
                boolean bl5 = bl || bl2;
                boolean bl6 = bl3 || bl4;
                if (!bl5 || !bl6) {
                    mutable.set(start, j, y, k);
                    if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                        boolean bl7 = bl || bl6 && j == 1 - i;
                        boolean bl8 = bl2 || bl6 && j == i - 1;
                        boolean bl9 = bl3 || bl5 && k == 1 - i;
                        boolean bl10 = bl4 || bl5 && k == i - 1;
                        BlockState blockState = config.capProvider.getBlockState(random, start);
                        if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH)) {
                            blockState = blockState.with(MushroomBlock.WEST, bl7).with(MushroomBlock.EAST, bl8).with(MushroomBlock.NORTH, bl9).with(MushroomBlock.SOUTH, bl10);
                        }

                        this.setBlockState(world, mutable, blockState);
                    }
                }
            }
        }

    }
}
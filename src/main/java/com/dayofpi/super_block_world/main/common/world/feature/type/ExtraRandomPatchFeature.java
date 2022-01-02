package com.dayofpi.super_block_world.main.common.world.feature.type;

import com.dayofpi.super_block_world.main.common.block.item_block.CoinBlock;
import com.dayofpi.super_block_world.main.common.block_entity.CoinBlockBE;
import com.dayofpi.super_block_world.main.common.world.feature.config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.key.ModBiomeKeys;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class ExtraRandomPatchFeature extends Feature<ExtraRandomPatchFeatureConfig> {
    public ExtraRandomPatchFeature(Codec<ExtraRandomPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<ExtraRandomPatchFeatureConfig> context) {
        ExtraRandomPatchFeatureConfig config = context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int x = config.xSpread();
        int z = config.zSpread();
        int y = config.ySpread();
        if (random.nextBoolean()) {
            for (int l = 0; l < config.tries(); ++l) {
                mutable.set(blockPos, random.nextInt(x) - random.nextInt(x), random.nextInt(y) - random.nextInt(y), random.nextInt(z) - random.nextInt(z));
                if (!config.feature().get().generateUnregistered(structureWorldAccess, context.getGenerator(), random, mutable)) continue;
                ++i;
            }
        } else {
            for (int l = 0; l < config.tries(); ++l) {
                mutable.set(blockPos, random.nextInt(z) - random.nextInt(z), random.nextInt(y) - random.nextInt(y), random.nextInt(x) - random.nextInt(x));
                if (!config.feature().get().generateUnregistered(structureWorldAccess, context.getGenerator(), random, mutable)) continue;
                ++i;
            }
        }

        if (structureWorldAccess.getBlockEntity(mutable) instanceof CoinBlockBE blockEntity && structureWorldAccess.getBlockState(mutable).isOf(BlockRegistry.COIN_BLOCK)) {
            if (random.nextInt(3) == 0) {
                blockEntity.getStack(0).setCount(5);
                BlockState state = structureWorldAccess.getBlockState(mutable);
                if (mutable.getY() < 63) {
                    if (structureWorldAccess.getBiomeKey(mutable).isPresent() && structureWorldAccess.getBiomeKey(mutable).get() == ModBiomeKeys.SHERBET_LAND)
                        structureWorldAccess.setBlockState(mutable, state.with(CoinBlock.TYPE, 4), 2);
                    else
                        structureWorldAccess.setBlockState(mutable, state.with(CoinBlock.TYPE, 2), 2);
                }
                else
                    structureWorldAccess.setBlockState(mutable, state.with(CoinBlock.TYPE, 1), 2);
            }
        }
        return i > 0;
    }
}

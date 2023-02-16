package com.dayofpi.super_block_world.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class RandomPatchFeature extends Feature<RandomPatchFeatureConfig> {
    public RandomPatchFeature(Codec<RandomPatchFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<RandomPatchFeatureConfig> context) {
        RandomPatchFeatureConfig config = context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int x = config.xSpread();
        int y = config.ySpread();
        int z = config.zSpread();

        for (int l = 0; l < config.tries(); ++l) {
            int x1 = x > 0 ? random.nextInt(x) - random.nextInt(x) : 0;
            int y1 = y > 0 ? random.nextInt(y) - random.nextInt(y) : 0;
            int z1 = z > 0 ? random.nextInt(z) - random.nextInt(z) : 0;
            mutable.set(blockPos, x1, y1, z1);
            if (config.feature().value().generateUnregistered(structureWorldAccess, context.getGenerator(), random, mutable)) {
                ++i;
            }
        }

        return i > 0;
    }
}
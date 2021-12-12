package com.dayofpi.sbw_main.world.feature.feature_type;

import com.dayofpi.sbw_main.world.feature.feature_config.BlockLineFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class BlockLineFeature extends Feature<BlockLineFeatureConfig> {
    public BlockLineFeature(Codec<BlockLineFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<BlockLineFeatureConfig> context) {
        BlockLineFeatureConfig config = context.getConfig();
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
        return i > 0;
    }
}

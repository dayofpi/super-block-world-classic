package com.dayofpi.super_block_world.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.HugeBrownMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

public class TallMushroomFeature extends HugeBrownMushroomFeature {
    public TallMushroomFeature(Codec<HugeMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected int getHeight(Random random) {
        return super.getHeight(random) * 3;
    }
}

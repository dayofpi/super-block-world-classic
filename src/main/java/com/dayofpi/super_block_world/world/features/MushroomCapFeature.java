package com.dayofpi.super_block_world.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.HugeBrownMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

public class MushroomCapFeature extends HugeBrownMushroomFeature {
    public MushroomCapFeature(Codec<HugeMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected int getHeight(Random random) {
        return 0;
    }
}

package com.dayofpi.sbw_main.world.feature.types;

import com.mojang.serialization.Codec;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class CustomUnderwaterDiskFeature extends CustomDiskFeature {
    public CustomUnderwaterDiskFeature(Codec<DiskFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DiskFeatureConfig> context) {
        return context.getWorld().getFluidState(context.getOrigin()).isIn(FluidTags.WATER) && super.generate(context);
    }
}

package com.dayofpi.sbw_main.world.structure.types;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ToadHouseFeature extends JigsawFeature {
    public ToadHouseFeature(Codec<StructurePoolFeatureConfig> configCodec) {
        super(configCodec, 0, true, true, (arg) -> true);
    }
}

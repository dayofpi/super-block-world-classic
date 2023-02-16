package com.dayofpi.super_block_world.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WarpPipeFeature extends Feature<DefaultFeatureConfig> {
    public WarpPipeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public static BlockState createPipeState(Block block, boolean waterlogged) {
        return block.getDefaultState().with(Properties.FACING, Direction.UP).with(Properties.WATERLOGGED, waterlogged);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        return false;
    }
}

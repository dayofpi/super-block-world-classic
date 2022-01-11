package com.dayofpi.super_block_world.common.block.plant;

import net.minecraft.block.AbstractLichenBlock;
import net.minecraft.state.property.Properties;

public class AmanitaCarpetBlock extends AbstractLichenBlock {
    public AmanitaCarpetBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(Properties.EAST, false).with(Properties.WEST, false).with(Properties.NORTH, false).with(Properties.SOUTH, false).with(Properties.UP, false).with(Properties.DOWN, false));
    }
}

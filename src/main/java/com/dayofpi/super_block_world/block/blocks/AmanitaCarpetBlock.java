package com.dayofpi.super_block_world.block.blocks;

import net.minecraft.block.LichenGrower;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.state.property.Properties;

public class AmanitaCarpetBlock extends MultifaceGrowthBlock {
    public AmanitaCarpetBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(Properties.EAST, false).with(Properties.WEST, false).with(Properties.NORTH, false).with(Properties.SOUTH, false).with(Properties.UP, false).with(Properties.DOWN, false));
    }

    @Override
    public LichenGrower getGrower() {
        return new LichenGrower(this);
    }
}

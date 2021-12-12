package com.dayofpi.sbw_main.common.block.type;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SongflowerBlock extends FlowerBlock {
    public static final BooleanProperty DANCING;

    static {
        DANCING = BooleanProperty.of("dancing");
    }

    public SongflowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DANCING, true));
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(DANCING) != world.isDay()) {
            world.setBlockState(pos, state.cycle(DANCING));
        }
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DANCING);
    }

}

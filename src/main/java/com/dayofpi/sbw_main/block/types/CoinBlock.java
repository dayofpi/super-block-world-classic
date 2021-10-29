package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.item.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Random;
@SuppressWarnings("deprecation")
public class CoinBlock extends ReactiveBlock {
    public static final IntProperty COIN_COUNT;
    public static final IntProperty TYPE;

    public CoinBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(COIN_COUNT, 5)
                .with(TYPE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COIN_COUNT, TYPE);
    }

    static {
        COIN_COUNT = IntProperty.of("coin_count", 1, 64);
        TYPE = IntProperty.of("type", 0, 4);
    }

    @Override
    public void activate(BlockState state, World world, BlockPos blockPos) {
        world.getBlockTickScheduler().schedule(blockPos, this, 2);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        int i = state.get(COIN_COUNT);
        BlockPos pos = blockPos.add(0, 1, 0);
        if (state.isSolidBlock(world, blockPos.up())) {
            if (!state.isSolidBlock(world, blockPos.down())) {
                pos = blockPos.add(0, -1, 0);
            }
        }
        Block.dropStack(world, pos, new ItemStack(ModItems.COIN));
        world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_COIN, SoundCategory.NEUTRAL, 0.8F, 1.0F);
        ParticleUtil.spawnParticle(world, blockPos, ParticleTypes.WAX_OFF, UniformIntProvider.create(1, 2));
        if (state.get(COIN_COUNT) > 1) {
            world.setBlockState(blockPos, state.with(COIN_COUNT, i - 1));
        } else
            world.setBlockState(blockPos, pushEntitiesUpBeforeBlockChange(state, ModBlocks.EMPTY_BLOCK.getDefaultState(), world, blockPos));


    }
}

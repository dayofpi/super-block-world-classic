package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PullBlock extends Block {
    private static final BooleanProperty POWERED;

    static {
        POWERED = Properties.POWERED;
    }

    public PullBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (state.get(POWERED))
            world.playSound(null, pos, Sounds.BLOCK_PULL_BLOCK_PULL, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block block, BlockPos fromPos, boolean notify) {
        boolean isPowered = state.get(POWERED);
        if (isPowered != world.isReceivingRedstonePower(blockPos)) {
            if (isPowered) {
                world.createAndScheduleBlockTick(blockPos, this, 4);
            } else activate(state, world, blockPos);
        }
    }

    public void activate(BlockState state, World world, BlockPos pos) {
        world.playSound(null, pos, Sounds.BLOCK_PULL_BLOCK_PULL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
        }
    }
}

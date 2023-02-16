package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class OnOffSwitchBlock extends Block {
    private static final BooleanProperty POWERED = Properties.POWERED;

    public OnOffSwitchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) {
            return;
        }
        boolean bl = state.get(POWERED);
        if (bl != world.isReceivingRedstonePower(pos)) {
            if (bl) {
                world.createAndScheduleBlockTick(pos, this, 4);
            } else {
                world.playSound(null, pos, Sounds.BLOCK_ON_OFF_SWITCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                world.emitGameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.playSound(null, pos, Sounds.BLOCK_ON_OFF_SWITCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
            world.emitGameEvent(null, GameEvent.BLOCK_DEACTIVATE, pos);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

}

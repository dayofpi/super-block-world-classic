package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.block.ModBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.Optional;

public class LogBlock extends PillarBlock {
    public LogBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof AxeItem) {
            Optional<BlockState> optional = this.getStrippedState(state);
            if (optional.isEmpty())
                return ActionResult.PASS;

            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
            }
            world.setBlockState(pos, optional.get(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, optional.get()));
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private Optional<BlockState> getStrippedState(BlockState state) {
        final Map<Block, Block> STRIPPED_BLOCKS = new ImmutableMap.Builder<Block, Block>().put(ModBlocks.AMANITA_WOOD, ModBlocks.STRIPPED_AMANITA_WOOD).put(ModBlocks.DARK_AMANITA_WOOD, ModBlocks.STRIPPED_DARK_AMANITA_WOOD).put(ModBlocks.BELL_WOOD, ModBlocks.STRIPPED_BELL_WOOD).put(ModBlocks.AMANITA_LOG, ModBlocks.STRIPPED_AMANITA_LOG).put(ModBlocks.DARK_AMANITA_LOG, ModBlocks.STRIPPED_DARK_AMANITA_LOG).put(ModBlocks.BELL_LOG, ModBlocks.STRIPPED_BELL_LOG).build();
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map(block -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
    }
}

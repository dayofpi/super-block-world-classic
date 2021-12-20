package com.dayofpi.super_block_world.main.common.block.reactive;

import com.dayofpi.super_block_world.main.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.common.block_entity.CoinBlockBE;
import com.dayofpi.super_block_world.main.common.block_entity.QuestionBlockBE;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class HiddenBlock extends Block {
    public HiddenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isEmpty()) {
            if (world.isClient) {
                return ActionResult.SUCCESS;
            } else {
                if (itemStack.isOf(ItemRegistry.COIN)) {
                    world.setBlockState(blockPos, BlockRegistry.COIN_BLOCK.getDefaultState().with(CoinBlock.TYPE, 5), 2);
                    if (world.getBlockEntity(blockPos) instanceof CoinBlockBE blockEntity) {
                        blockEntity.setStack(0, itemStack.copy());
                        blockEntity.markDirty();
                    }
                } else {
                    world.setBlockState(blockPos, BlockRegistry.QUESTION_BLOCK.getDefaultState().with(QuestionBlock.HIDDEN, true), 2);
                    if (world.getBlockEntity(blockPos) instanceof QuestionBlockBE blockEntity) {
                        blockEntity.setStack(0, itemStack.copy());
                        blockEntity.markDirty();
                    }
                }
                if (!player.isCreative()) {
                    player.getStackInHand(hand).setCount(0);
                }
                world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_STORE, SoundCategory.NEUTRAL, 2.0F, 1.0F);
                ParticleUtil.spawnParticle(world, blockPos, ParticleTypes.WAX_ON, UniformIntProvider.create(3, 5));
                return ActionResult.CONSUME;
            }
        } else return ActionResult.PASS;
    }
}

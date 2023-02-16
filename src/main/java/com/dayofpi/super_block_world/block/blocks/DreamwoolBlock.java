package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class DreamwoolBlock extends Block {
    public DreamwoolBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.SHEARS)) {
            world.setBlockState(blockPos, Blocks.WHITE_WOOL.getDefaultState(), 1);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, state));
            Block.dropStack(world, blockPos, hit.getSide(), new ItemStack(ModItems.SUBCON_THREAD));
            world.playSound(player, blockPos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

}

package com.dayofpi.sbw_main.common.block.type;

import com.dayofpi.sbw_main.registry.block.ModBlocks;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ToadstoolSoilBlock extends Block {
    public ToadstoolSoilBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(FabricToolTags.HOES)) {
            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, ModBlocks.TOADSTOOL_FARMLAND.getDefaultState(), 2);
            return ActionResult.success(world.isClient);
        } else if (itemStack.isIn(FabricToolTags.SHOVELS)) {
            world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, ModBlocks.TOADSTOOL_PATH.getDefaultState(), 2);
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
}

package com.dayofpi.super_block_world.main.common.block;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class WoodBlock extends PillarBlock {
    public WoodBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockState stripResult = BlockRegistry.STRIPPED_AMANITA_LOG.getDefaultState();
        if (state.isOf(BlockRegistry.AMANITA_WOOD)) {
            stripResult = BlockRegistry.STRIPPED_AMANITA_WOOD.getDefaultState();
        } else if (state.isOf(BlockRegistry.DARK_AMANITA_LOG)) {
            stripResult = BlockRegistry.STRIPPED_DARK_AMANITA_LOG.getDefaultState();
        } else if (state.isOf(BlockRegistry.DARK_AMANITA_WOOD)) {
            stripResult = BlockRegistry.STRIPPED_DARK_AMANITA_WOOD.getDefaultState();
        }
        if (itemStack.isIn(FabricToolTags.AXES)) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, stripResult, 2);
            return ActionResult.success(world.isClient);
        } else return ActionResult.PASS;
    }
}

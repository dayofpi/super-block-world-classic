package com.dayofpi.super_block_world.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class WaterFlowerItem extends BlockItem {
    public WaterFlowerItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.withBlockPos(blockHitResult.getBlockPos().up());
        ActionResult actionResult = super.useOnBlock(new ItemUsageContext(user, hand, blockHitResult2));
        return new TypedActionResult<>(actionResult, user.getStackInHand(hand));
    }
}

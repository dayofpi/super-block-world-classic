package com.dayofpi.super_block_world.main.common.block.soil;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SherbetSoilBlock extends Block {
    public SherbetSoilBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SNOWBALL)) {
            world.setBlockState(pos, BlockRegistry.SNOWY_SHERBET_SOIL.getDefaultState(), 1);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity && EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            world.setBlockState(pos, BlockRegistry.SNOWY_SHERBET_SOIL.getDefaultState(), 1);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}

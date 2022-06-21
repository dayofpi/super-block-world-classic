package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class CherrySoilBlock extends Block implements Fertilizable {
    public CherrySoilBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.getItem() instanceof ShovelItem) {
            world.setBlockState(blockPos, ModBlocks.CHERRY_PATH.getDefaultState(), 1);
            world.playSound(player, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos blockPos, BlockState state) {
        for (BlockPos soil : BlockPos.iterateOutwards(blockPos, 3, 3, 3)) {
            if (world.getBlockState(soil).isOf(ModBlocks.CHERRY_SOIL) && !world.getBlockState(soil.up()).isSideSolidFullSquare(world, soil.up(), Direction.DOWN))
                world.setBlockState(soil, ModBlocks.CHERRY_GRASS.getDefaultState());
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.isOf(ModBlocks.CHERRY_SOIL) && !world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
}

package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnMixin {

    @Inject(at = @At("HEAD"), method = "getBubbleState", cancellable = true)
    private static void getBubbleState(BlockState state, CallbackInfoReturnable<BlockState> info) {
        if (state.isIn(ModTags.WARP_PIPES) && state.get(Properties.FACING) == Direction.UP && state.get(Properties.WATERLOGGED)) {
            info.setReturnValue(Blocks.BUBBLE_COLUMN.getDefaultState().with(BubbleColumnBlock.DRAG, false));
        }
        if (state.isOf(ModBlocks.BLAZING_CHARROCK)) {
            info.setReturnValue(Blocks.BUBBLE_COLUMN.getDefaultState().with(BubbleColumnBlock.DRAG, true));
        }
    }

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isIn(ModTags.WARP_PIPES) && blockState.get(Properties.FACING) == Direction.UP && blockState.get(Properties.WATERLOGGED)) {
            info.setReturnValue(true);
        }
        if (blockState.isOf(ModBlocks.BLAZING_CHARROCK)) {
            info.setReturnValue(true);
        }
    }
}

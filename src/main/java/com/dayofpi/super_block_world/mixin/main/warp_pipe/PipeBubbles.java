package com.dayofpi.super_block_world.mixin.main.warp_pipe;

import com.dayofpi.super_block_world.main.registry.misc.TagRegistry;
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
public class PipeBubbles {

    @Inject(at=@At("HEAD"), method = "getBubbleState(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockState;", cancellable = true)
    private static void getBubbleState(BlockState state, CallbackInfoReturnable<BlockState> info) {
        if (state.isIn(TagRegistry.WARP_PIPES) && state.get(Properties.FACING) == Direction.UP && state.get(Properties.WATERLOGGED)) {
            info.setReturnValue(Blocks.BUBBLE_COLUMN.getDefaultState().with(BubbleColumnBlock.DRAG, false));
            info.cancel();
        }
    }

    @Inject(at=@At("HEAD"), method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos.down());
        info.setReturnValue((state.isIn(TagRegistry.WARP_PIPES) && state.get(Properties.FACING) == Direction.UP) || blockState.isOf(Blocks.BUBBLE_COLUMN) || blockState.isOf(Blocks.MAGMA_BLOCK) || blockState.isOf(Blocks.SOUL_SAND));
        info.cancel();
    }

}

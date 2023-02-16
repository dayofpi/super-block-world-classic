package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.block.blocks.QuicksandBlock;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class LandPathNodeMixin {
    @Inject(at = @At("HEAD"), method = "getNodeTypeFromNeighbors", cancellable = true)
    private static void getNodeTypeFromNeighbors(BlockView world, BlockPos.Mutable pos, PathNodeType nodeType, CallbackInfoReturnable<PathNodeType> cir) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int l = -1; l <= 1; ++l) {
            for (int m = -1; m <= 1; ++m) {
                for (int n = -1; n <= 1; ++n) {
                    if (l == 0 && n == 0) continue;
                    pos.set(x + l, y + m, z + n);
                    BlockState blockState = world.getBlockState(pos);
                    FluidState fluidState = world.getFluidState(pos);
                    if (blockState.isOf(ModBlocks.JELLYBEAM) || blockState.getBlock() instanceof QuicksandBlock || blockState.isOf(ModBlocks.BLACK_PAINT)) {
                        cir.setReturnValue(PathNodeType.DANGER_OTHER);
                    }
                    if (blockState.isOf(ModBlocks.MUNCHER)) {
                        cir.setReturnValue(PathNodeType.DANGER_OTHER);
                    }
                    if (blockState.isOf(ModBlocks.PIT_PLANT)) {
                        cir.setReturnValue(PathNodeType.DANGER_OTHER);
                    }
                    if (fluidState.isIn(ModTags.POISON)) {
                        cir.setReturnValue(PathNodeType.DANGER_OTHER);
                    }
                }
            }
        }
    }

    @Inject(at=@At("HEAD"), method = "inflictsFireDamage", cancellable = true)
    private static void inflictsFireDamage(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isOf(ModBlocks.FIRE_TULIP) || state.isOf(ModBlocks.BLAZING_CHARROCK))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "getCommonNodeType", cancellable = true)
    private static void getCommonNodeType(BlockView world, BlockPos pos, CallbackInfoReturnable<PathNodeType> cir) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);
        if (blockState.isOf(ModBlocks.JELLYBEAM) || blockState.getBlock() instanceof QuicksandBlock || blockState.isOf(ModBlocks.BLACK_PAINT)) {
            cir.setReturnValue(PathNodeType.DAMAGE_OTHER);
        }
        if (blockState.isOf(ModBlocks.MUNCHER)) {
            cir.setReturnValue(PathNodeType.DAMAGE_OTHER);
        }
        if (blockState.isOf(ModBlocks.PIT_PLANT)) {
            cir.setReturnValue(PathNodeType.DAMAGE_OTHER);
        }
        if (fluidState.isIn(ModTags.POISON)) {
            cir.setReturnValue(PathNodeType.DAMAGE_OTHER);
        }
    }
}

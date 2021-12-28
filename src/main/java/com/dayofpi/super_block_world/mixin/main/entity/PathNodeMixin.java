package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.main.registry.general.TagRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.block.PlantBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class PathNodeMixin {

    @Inject(at=@At("HEAD"), method = "getCommonNodeType(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/ai/pathing/PathNodeType;", cancellable = true)
    private static void getCommonNodeType(BlockView world, BlockPos pos, CallbackInfoReturnable<PathNodeType> info) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(PlantBlocks.MUNCHER)) {
            info.setReturnValue(PathNodeType.DAMAGE_OTHER);
            info.cancel();
        } else if (blockState == BlockRegistry.SPIKE_TRAP.getDefaultState().with(Properties.POWERED, true)) {
            info.setReturnValue(PathNodeType.DAMAGE_OTHER);
            info.cancel();
        } else if (blockState.isOf(PlantBlocks.FIRE_TULIP)) {
            info.setReturnValue(PathNodeType.DAMAGE_FIRE);
            info.cancel();
        } else if (blockState.isOf(BlockRegistry.STONE_TORCH) && blockState.get(Properties.LIT)) {
            info.setReturnValue(PathNodeType.DAMAGE_FIRE);
            info.cancel();
        } else {
            FluidState fluidState = world.getFluidState(pos);
            if (fluidState.isIn(TagRegistry.POISON)) {
                info.setReturnValue(PathNodeType.DAMAGE_OTHER);
                info.cancel();
            }
        }
    }

    @Inject(at=@At("HEAD"), method = "getNodeTypeFromNeighbors(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos$Mutable;Lnet/minecraft/entity/ai/pathing/PathNodeType;)Lnet/minecraft/entity/ai/pathing/PathNodeType;", cancellable = true)
    private static void getNodeTypeFromNeighbors(BlockView world, BlockPos.Mutable pos, PathNodeType nodeType, CallbackInfoReturnable<PathNodeType> info) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        for (int l = -1; l <= 1; ++l) {
            for (int m = -1; m <= 1; ++m) {
                for (int n = -1; n <= 1; ++n) {
                    if (l == 0 && n == 0) continue;
                    pos.set(i + l, j + m, k + n);
                    BlockState blockState = world.getBlockState(pos);
                    if (blockState.isOf(PlantBlocks.MUNCHER)) {
                        info.setReturnValue(PathNodeType.DANGER_OTHER);
                        info.cancel();
                    }
                    if (blockState.isOf(BlockRegistry.POISON)) {
                        info.setReturnValue(PathNodeType.DANGER_OTHER);
                        info.cancel();
                    }
                    if (blockState.isOf(BlockRegistry.SPIKE_TRAP) && blockState.get(Properties.POWERED)) {
                        info.setReturnValue(PathNodeType.DAMAGE_OTHER);
                        info.cancel();
                    }
                    if (blockState.isOf(BlockRegistry.STONE_TORCH) && blockState.get(Properties.LIT)) {
                        info.setReturnValue(PathNodeType.DAMAGE_FIRE);
                        info.cancel();
                    }
                    if (blockState.isOf(PlantBlocks.FIRE_TULIP)) {
                        info.setReturnValue(PathNodeType.DAMAGE_FIRE);
                        info.cancel();
                    }
                }
            }
        }
    }
}

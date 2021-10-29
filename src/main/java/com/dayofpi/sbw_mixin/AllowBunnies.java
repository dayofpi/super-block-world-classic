package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RabbitEntity.class)
public class AllowBunnies {
    @Inject(at = @At("HEAD"), method = "canSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", cancellable = true)
    private static void canSpawn(EntityType<RabbitEntity> entity, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos.down());
        info.setReturnValue(blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.SNOW) || blockState.isOf(Blocks.SAND) || blockState.isOf(ModBlocks.TOADSTOOL_GRASS) && (world.getBaseLightLevel(pos, 0) > 8));
        info.cancel();
    }
}
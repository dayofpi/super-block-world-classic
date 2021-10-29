package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AnimalEntity.class)
public class AllowAnimals {
    @Inject(at = @At("HEAD"), method = "isValidNaturalSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", cancellable = true)
    private static void isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) || world.getBlockState(pos.down()).isOf(ModBlocks.TOADSTOOL_GRASS) && world.getBaseLightLevel(pos, 0) > 8);
        info.cancel();
    }
}
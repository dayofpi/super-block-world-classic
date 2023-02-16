package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {
    private static boolean canSnowGolemSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.isSkyVisible(pos) && (world.getBlockState(pos.down()).isOf(ModBlocks.SNOWY_SHERBET_SOIL) || world.getBlockState(pos.down()).isOf(Blocks.ICE));
    }

    @Inject(at = @At("HEAD"), method = "canSpawn", cancellable = true)
    private static <T extends MobEntity> void canSpawn(EntityType<T> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        if (type == EntityType.SNOW_GOLEM) {
            info.setReturnValue(canSnowGolemSpawn(type, world, reason, pos, random));
            info.cancel();
        }
    }
}

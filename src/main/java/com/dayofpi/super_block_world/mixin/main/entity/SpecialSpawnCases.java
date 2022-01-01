package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.main.common.entity.mob.ThwompEntity;
import com.dayofpi.super_block_world.main.common.entity.mob.buzzy.AbstractBuzzy;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.misc.EntityRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SpawnRestriction.class)
public class SpecialSpawnCases {
    private static boolean canSnowGolemSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.isSkyVisible(pos) && (world.getBlockState(pos.down()).isOf(BlockRegistry.SNOWY_SHERBET_SOIL) || world.getBlockState(pos.down()).isOf(Blocks.ICE));
    }

    @Inject(at = @At("HEAD"), method = "canSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", cancellable = true)
    private static <T extends MobEntity> void canSpawn(EntityType<T> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        if (type == EntityRegistry.BUZZY_BEETLE) {
            info.setReturnValue(AbstractBuzzy.canSpawn(type, world, reason, pos, random));
            info.cancel();
        }
        if (type == EntityRegistry.SPIKE_TOP) {
            info.setReturnValue(AbstractBuzzy.canSpawn(type, world, reason, pos, random));
            info.cancel();
        }
        if (type == EntityRegistry.THWOMP) {
            info.setReturnValue(ThwompEntity.canSpawn(type, world, reason, pos, random));
            info.cancel();
        }
        if (type == EntityType.SNOW_GOLEM) {
            info.setReturnValue(canSnowGolemSpawn(type, world, reason, pos, random));
            info.cancel();
        }
    }
}

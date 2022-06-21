package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.common.entities.misc.TrampolineMinecartEntity;
import com.dayofpi.super_block_world.util.EnumUtil;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class MinecartEntityMixin {
    @Inject(at = @At("HEAD"), method = "create", cancellable = true)
    private static void create(World world, double x, double y, double z, AbstractMinecartEntity.Type type, CallbackInfoReturnable<AbstractMinecartEntity> cir) {
        if (type == EnumUtil.TRAMPOLINE) {
            cir.setReturnValue(new TrampolineMinecartEntity(world, x, y, z));
            cir.cancel();
        }
    }
}

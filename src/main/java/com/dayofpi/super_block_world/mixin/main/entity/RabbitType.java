package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.main.common.world.dimension.MushroomKingdom;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntity.class)
public abstract class RabbitType extends LivingEntity {

    protected RabbitType(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method = "chooseType", cancellable = true)
    private void chooseType(WorldAccess world, CallbackInfoReturnable<Integer> info) {
        if (this.getWorld().getRegistryKey() == MushroomKingdom.WORLD_KEY) {
            info.setReturnValue(random.nextBoolean() ? 1 : 4);
            info.cancel();
        }
    }
}

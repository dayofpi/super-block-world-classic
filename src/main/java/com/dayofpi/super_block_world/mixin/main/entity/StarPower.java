package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.registry.more.StatusEffectInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class StarPower {
    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        // Prevent Star Powered entities from taking damage
        if (this.activeStatusEffects.containsKey(StatusEffectInit.STAR_POWER) && !source.isOutOfWorld()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}


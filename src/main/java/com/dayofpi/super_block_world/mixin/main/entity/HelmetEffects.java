package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class HelmetEffects extends Entity {
    public HelmetEffects(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Inject(at=@At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemRegistry.RED_SHELL) && !this.isOnFire() && !this.isInLava()) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
        }
    }
}

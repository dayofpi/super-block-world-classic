package com.dayofpi.super_block_world.mixin.living_entity;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.registry.ModStatusEffects;
import com.dayofpi.super_block_world.main.registry.ModItems;
import com.dayofpi.super_block_world.main.util.entity.ModEntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class DefensiveItems extends Entity {
    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    public DefensiveItems(EntityType<?> type, World world) {
        super(type, world);
    }

    @Invoker("getEquippedStack")
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract float getSoundPitch();

    @Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        // Prevent Star Powered entities from taking damage
        if (this.activeStatusEffects.containsKey(ModStatusEffects.STAR_POWER) && !source.isOutOfWorld()) {
            info.setReturnValue(false);
            info.cancel();
        }

        ItemStack headArmor = this.getEquippedStack(EquipmentSlot.HEAD);

        if (headArmor.isOf(ModItems.BUZZY_SHELL)) {
            if (source instanceof ProjectileDamageSource damageSource && source.getPosition() != null && source.getPosition().y > this.getY() + 1) {
                Entity projectile = damageSource.getSource();
                if (projectile != null) {
                    this.protect(headArmor, info);
                    Vec3d vec3d = projectile.getVelocity();
                    projectile.pushAwayFrom(this);
                }
            } else if (source.isFallingBlock())
                this.protect(headArmor, info);
            else if (source instanceof ModEntityDamageSource damageSource) {
                if (damageSource.isFromAbove()) {
                    this.protect(headArmor, info);
                }
            }
        }
    }

    private void protect(ItemStack helmet, CallbackInfoReturnable<Boolean> info) {
        this.world.playSound(null, this.getBlockPos(), ModSounds.ENTITY_BUZZY_BLOCK, SoundCategory.NEUTRAL, 0.6F, this.getSoundPitch());
        helmet.setDamage(helmet.getDamage() + random.nextInt(4));
        if (helmet.getDamage() >= helmet.getMaxDamage()) {
            this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
            this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
        }
        info.setReturnValue(false);
        info.cancel();
    }

    public void sendEquipmentBreakStatus(EquipmentSlot slot) {
        this.world.sendEntityStatus(this, getEquipmentBreakStatus(slot));
    }

    @Shadow
    private static byte getEquipmentBreakStatus(EquipmentSlot slot) {
        return 0;
    }
}


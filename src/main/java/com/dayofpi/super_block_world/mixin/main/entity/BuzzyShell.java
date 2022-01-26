package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.more.MobDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class BuzzyShell extends Entity {
    public BuzzyShell(EntityType<?> type, World world) {
        super(type, world);
    }

    @Invoker("getEquippedStack")
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract float getSoundPitch();

    @Shadow
    private static byte getEquipmentBreakStatus(EquipmentSlot slot) {
        return 0;
    }

    @Inject(at = @At("HEAD"), method = "pushAwayFrom", cancellable = true)
    private void pushAwayFrom(Entity entity, CallbackInfo info) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(ItemInit.JUMP_BOOTS) && livingEntity.getY() > this.getBlockY()) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.HEAD);
        if (stack.isOf(ItemInit.BUZZY_SHELL)) {
            if (source instanceof ProjectileDamageSource damageSource && source.getPosition() != null && source.getPosition().y > this.getY() + 1) {
                Entity projectile = damageSource.getSource();
                if (projectile != null) {
                    this.protect(stack, info);
                    projectile.pushAwayFrom(this);
                }
            }
            else if (source.isFallingBlock())
                this.protect(stack, info);
            else if (source instanceof MobDamageSource damageSource) {
                if (damageSource.isFromAbove()) {
                    this.protect(stack, info);
                }
            }
        }
    }

    private void protect(ItemStack helmet, CallbackInfoReturnable<Boolean> info) {
        // The regular playSound method doesn't work for some reason
        world.playSound(null, this.getBlockPos(), SoundInit.ENTITY_BUZZY_BLOCK, this.getSoundCategory(), 1.0F, this.getSoundPitch());
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
}


package com.dayofpi.sbw_mixin.living_entity;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.ModTags;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import com.dayofpi.sbw_main.entity.types.bases.AbstractBuzzy;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.ModDamageSource;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class ArmorAbilities extends Entity {
    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    public ArmorAbilities(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "pushAwayFrom")
    public void pushAwayFrom(Entity entity, CallbackInfo info) {
        if (entity instanceof LivingEntity other) {
            if (!other.isDead() && this.getY() > other.getY() && this.hasJumpBoots()) {
                Vec3d velocity = this.getVelocity();
                this.setVelocity(velocity.x, 0.6, velocity.z);
            }

            if (!other.isDead() && other.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.JUMP_BOOTS)) {
                ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);

                if (other.getY() > this.getY() && other.fallDistance > 0) {
                    if (!(helmet.isOf(ModItems.BUZZY_SHELL) || this.getType().isIn(ModTags.IMMUNE_TO_BOOTS))) {
                        this.damage(ModDamageSource.stomp(other), 5F);
                        this.playSound(ModSounds.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, this.getSoundPitch());
                        if (helmet.isDamageable()) {
                            helmet.damage(2, other, ((e) -> e.sendEquipmentBreakStatus(EquipmentSlot.HEAD)));
                        }
                    } else {
                        this.playSound(ModSounds.ENTITY_JUMP_BOOTS_BOUNCE, 1.0F, this.getSoundPitch());
                    }
                }
            }
        }
    }

    private boolean hasJumpBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ModItems.JUMP_BOOTS));
    }

    @Invoker("getEquippedStack")
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract float getSoundPitch();

    @Inject(at = @At("HEAD"), method = "jump")
    protected void jump(CallbackInfo info) {
        if (this.hasJumpBoots()) {
            this.playSound(ModSounds.ENTITY_JUMP_BOOTS_JUMP, 0.5F, this.getSoundPitch());
        }
    }

    @Inject(at = @At("HEAD"), method = "getJumpVelocity()F", cancellable = true)
    private void getJumpVelocity(CallbackInfoReturnable<Float> info) {
        if (this.hasJumpBoots()) {
            float jumpHeight = 0.9F;
            if (this.isSneaking()) {
                jumpHeight = 0.6F;
            }
            info.setReturnValue(jumpHeight * this.getJumpVelocityMultiplier());
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (this.activeStatusEffects.containsKey(ModEffects.STAR_POWER) && !source.isOutOfWorld()) {
            info.setReturnValue(false);
            info.cancel();
        }

        ItemStack headSlot = this.getEquippedStack(EquipmentSlot.HEAD);
        if (headSlot.isOf(ModItems.BUZZY_SHELL)) {
            if (source instanceof ProjectileDamageSource && source.getPosition() != null && source.getPosition().y > this.getY() + 1.5 || source.isFallingBlock() || source.getAttacker() != null && source.getAttacker() instanceof AbstractBuzzy && ((AbstractBuzzy) source.getAttacker()).isUpsideDown() && source.getAttacker().fallDistance > 0) {
                this.world.playSound(null, this.getBlockPos(), ModSounds.ENTITY_BUZZY_BLOCK, SoundCategory.NEUTRAL, 0.6F, this.getSoundPitch());
                headSlot.setDamage(headSlot.getDamage() + random.nextInt(2));
                if (headSlot.getDamage() >= headSlot.getMaxDamage()) {
                    this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                    this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                }
                if (source instanceof ProjectileDamageSource projectileDamageSource) {
                    if (projectileDamageSource.getSource() != null)
                        projectileDamageSource.getSource().addVelocity(0.0D, 2.0D, 0.0D);
                }
                info.setReturnValue(false);
                info.cancel();
            }
        }
    }

    public void sendEquipmentBreakStatus(EquipmentSlot slot) {
        this.world.sendEntityStatus(this, getEquipmentBreakStatus(slot));
    }

    @Shadow
    private static byte getEquipmentBreakStatus(EquipmentSlot slot) {
        return 0;
    }

    @Inject(at = @At("HEAD"), method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        if (this.hasJumpBoots()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}


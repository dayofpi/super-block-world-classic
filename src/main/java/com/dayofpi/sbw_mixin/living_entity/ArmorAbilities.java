package com.dayofpi.sbw_mixin.living_entity;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.SoundList;
import com.dayofpi.sbw_main.TagList;
import com.dayofpi.sbw_main.entity.types.mobs.AbstractBuzzy;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ArmorAbilities extends Entity {
    public ArmorAbilities(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "pushAwayFrom")
    public void pushAwayFrom(Entity entity, CallbackInfo info) {
        if (entity instanceof LivingEntity other) {
            if (!other.isDead() && this.getY() > other.getY() && this.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.JUMP_BOOTS)) {
                this.setVelocity(this.getVelocity().x, 0.5, this.getVelocity().z);
            }

            if (!other.isDead() && other.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.JUMP_BOOTS)) {
                ItemStack entityHelmet = this.getEquippedStack(EquipmentSlot.HEAD);
                boolean conditions = other.getY() > this.getY() && other.fallDistance > 0;

                if (conditions) {
                    if (!(entityHelmet.isOf(ModItems.BUZZY_SHELL) || this.getType().isIn(TagList.IMMUNE_TO_BOOTS))) {
                        this.damage(ModDamageSource.stomp(other), 5F);
                        this.playSound(ModSounds.ENTITY_JUMP_BOOTS_JUMP, 1.0F, this.getSoundPitch());
                        if (entityHelmet.isDamageable()) {
                            entityHelmet.damage(2, other, ((e) -> e.sendEquipmentBreakStatus(EquipmentSlot.HEAD)));
                        }
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "jump", cancellable = true)
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

    private boolean hasJumpBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ModItems.JUMP_BOOTS));
    }

    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    @Invoker("getEquippedStack")
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (((LivingEntityAccessors)this).activeStatusEffects().containsKey(ModEffects.STAR_POWER) && !source.isOutOfWorld()) {
            info.setReturnValue(false);
            info.cancel();
        }

        ItemStack headSlot = this.getEquippedStack(EquipmentSlot.HEAD);
        if (headSlot.isOf(ModItems.BUZZY_SHELL)) {
            if (source instanceof ProjectileDamageSource && source.getPosition() != null && source.getPosition().y > this.getY() + 1.5 || source.isFallingBlock() || source.getAttacker() != null && source.getAttacker() instanceof AbstractBuzzy && ((AbstractBuzzy) source.getAttacker()).isUpsideDown() && source.getAttacker().fallDistance > 0) {
                this.world.playSound(null, this.getBlockPos(), SoundList.buzzyBlock, SoundCategory.NEUTRAL, 0.6F, this.randomPitch());
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

    public float randomPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    public void sendEquipmentBreakStatus(EquipmentSlot slot) {
        this.world.sendEntityStatus(this, getEquipmentBreakStatus(slot));
    }

    private static byte getEquipmentBreakStatus(EquipmentSlot slot) {
        switch (slot) {
            case OFFHAND:
                return 48;
            case HEAD:
                return 49;
            case CHEST:
                return 50;
            case FEET:
                return 52;
            case LEGS:
                return 51;
            default:
                return 47;
        }
    }

    @Inject(at = @At("HEAD"), method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        if (this.hasJumpBoots()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}


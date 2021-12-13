package com.dayofpi.super_block_world.mixin.living_entity;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.registry.ModTags;
import com.dayofpi.super_block_world.main.registry.ModItems;
import com.dayofpi.super_block_world.main.util.entity.ModEntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class JumpBoots extends Entity {
    public JumpBoots(EntityType<?> type, World world) {
        super(type, world);
    }

    @Invoker("getEquippedStack")
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract float getSoundPitch();

    @Inject(at = @At("HEAD"), method = "pushAwayFrom")
    public void pushAwayFrom(Entity entity, CallbackInfo info) {
        if (entity instanceof LivingEntity other) {
            if (this.hasJumpBoots() && !other.isDead() && this.getY() > other.getY()) {
                Vec3d velocity = this.getVelocity();
                this.setVelocity(velocity.x, 0.6, velocity.z);
            }

            if (other.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.JUMP_BOOTS)) {
                if (!other.isDead() && other.getY() > this.getY()) {
                    SoundEvent soundEvent = ModSounds.ENTITY_JUMP_BOOTS_BOUNCE;
                    if (!this.getType().isIn(ModTags.IMMUNE_TO_BOOTS)) {
                        this.damage(ModEntityDamageSource.stomp(other), 5F);
                        soundEvent = ModSounds.ENTITY_JUMP_BOOTS_ATTACK;
                    }
                    this.doStompSound(soundEvent);
                }
            }
        }
    }

    private void doStompSound(SoundEvent soundEvent) {
        this.playSound(soundEvent, 1.0F, 1.0F);
    }

    private boolean hasJumpBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ModItems.JUMP_BOOTS));
    }

    @Inject(at = @At("HEAD"), method = "jump")
    protected void jump(CallbackInfo info) {
        if (this.hasJumpBoots()) {
            this.playSound(ModSounds.ENTITY_JUMP_BOOTS_JUMP, 0.6F, this.getSoundPitch());
        }
    }

    @Inject(at = @At("HEAD"), method = "getJumpVelocity()F", cancellable = true)
    private void getJumpVelocity(CallbackInfoReturnable<Float> info) {
        if (this.hasJumpBoots()) {
            float jumpHeight = 0.92F;
            if (this.isSneaking()) {
                jumpHeight = 0.6F;
            }
            info.setReturnValue(jumpHeight * this.getJumpVelocityMultiplier());
            info.cancel();
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


package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.more.MobDamageSource;
import com.dayofpi.super_block_world.common.util.entity.Stompable;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class BootAbilities extends LivingEntity {
    private int stompCooldown;

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    protected BootAbilities(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (!(this.hasJumpBoots() && this.getY() > entity.getBlockY()))
            super.pushAwayFrom(entity);
    }

    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        if (this.hasJumpBoots()) {
            if (stompCooldown > 0)
                --stompCooldown;
            if (stompCooldown == 0 && this.isAlive() && !this.isOnGround() && !this.hasVehicle() && !this.isInSwimmingPose()) {
                LivingEntity livingEntity = world.getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox());
                if (livingEntity != null) {
                    if (livingEntity.getPassengerList().isEmpty() && !livingEntity.getType().isIn(TagInit.IGNORES_BOOTS) && this.getY() > livingEntity.getBlockY()) {
                        this.stomp(livingEntity);
                    }
                }
            }
        }
    }

    private void stomp(LivingEntity livingEntity) {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x, 0.6D, vec3d.z);
        this.stompCooldown = 5;
        if (livingEntity.getType().isIn(TagInit.STOMP_IMMUNE) || livingEntity instanceof Tameable && ((Tameable) livingEntity).getOwner() == this) {
            this.playSound(SoundInit.ENTITY_JUMP_BOOTS_BOUNCE, 1.0F, 1.0F);
        }
        else if (livingEntity instanceof Stompable) {
            ((Stompable) livingEntity).stompResult(this);
            this.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
        }
        else {
            livingEntity.damage(MobDamageSource.stomp(this), 5);
            this.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
        }
    }

    private boolean hasJumpBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ItemInit.JUMP_BOOTS));
    }

    private boolean hasCloudBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ItemInit.CLOUD_BOOTS));
    }

    @Inject(at=@At("HEAD"), method = "jump")
    protected void jump(CallbackInfo info) {
        if (this.hasJumpBoots() && !this.isSneaking()) {
            this.playSound(SoundInit.ENTITY_JUMP_BOOTS_JUMP, 0.6F, this.getSoundPitch());
        }
    }

    protected float getJumpVelocity() {
        if (this.hasJumpBoots()) {
            float jumpHeight = 0.92F;
            if (this.isSneaking()) {
                jumpHeight = 0.5F;
            }
            return jumpHeight * this.getJumpVelocityMultiplier();
        } else return super.getJumpVelocity();
    }

    @Inject(at=@At("HEAD"), method = "handleFallDamage", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        if (this.hasJumpBoots() || this.hasCloudBoots()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}


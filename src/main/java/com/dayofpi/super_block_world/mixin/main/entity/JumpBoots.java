package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entity.mob.buzzy.SpikeTopEntity;
import com.dayofpi.super_block_world.common.entity.mob.npc.ToadEntity;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.common.util.entity.ModEntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class JumpBoots extends LivingEntity {
    protected JumpBoots(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        if (this.isAlive() && this.hasJumpBoots() && !this.hasVehicle() && !this.isInSwimmingPose()) {
            List<Entity> list = world.getOtherEntities(this, this.getBoundingBox().offset(0, -0.5, 0), entity -> entity instanceof LivingEntity);
            list.forEach(entity -> {
                Vec3d velocity = this.getVelocity();
                if (entity.isAlive() && entity.getBlockY() < this.getY() && velocity.y <= 0) {
                    this.setVelocity(velocity.x, 0.6, velocity.z);
                    boolean isImmune = entity.getType().isIn(TagInit.IMMUNE_TO_BOOTS);
                    boolean isPet = entity instanceof TameableEntity tameableEntity && tameableEntity.getOwner() == this;
                    if (!isPet && !isImmune) {
                        entity.damage(ModEntityDamageSource.stomp(this), 5F);
                        this.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
                    } else if (!(entity instanceof SpikeTopEntity)) {
                        this.playSound(SoundInit.ENTITY_JUMP_BOOTS_BOUNCE, 1.0F, 1.0F);
                        if (entity instanceof ToadEntity && random.nextBoolean()) {
                            ((ToadEntity)entity).setEmotion(2);
                        }

                    }
                }
            });
        }
    }

    private boolean hasJumpBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ItemInit.JUMP_BOOTS));
    }

    @Inject(at=@At("HEAD"), method = "jump")
    protected void jump(CallbackInfo info) {
        if (this.hasJumpBoots() && !this.isSneaking()) {
            this.playSound(SoundInit.ENTITY_JUMP_BOOTS_JUMP, 0.6F, this.getSoundPitch());
        }
    }

    // Higher jump
    protected float getJumpVelocity() {
        if (this.hasJumpBoots()) {
            float jumpHeight = 0.92F;
            if (this.isSneaking()) {
                jumpHeight = 0.6F;
            }
            return jumpHeight * this.getJumpVelocityMultiplier();
        } else return super.getJumpVelocity();
    }

    @Inject(at=@At("HEAD"), method = "handleFallDamage", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        if (this.hasJumpBoots()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}


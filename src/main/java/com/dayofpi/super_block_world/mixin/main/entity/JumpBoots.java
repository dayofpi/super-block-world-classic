package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import com.dayofpi.super_block_world.main.registry.misc.TagRegistry;
import com.dayofpi.super_block_world.main.util.entity.ModEntityDamageSource;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class JumpBoots extends Entity {
    public JumpBoots(EntityType<?> type, World world) {
        super(type, world);
    }

    @Invoker("getEquippedStack")
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract float getSoundPitch();

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract Box getBoundingBox(EntityPose pose);

    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        if (this.isAlive() && this.hasJumpBoots()) {
            List<Entity> list = world.getOtherEntities(this, this.getBoundingBox().offset(0, -0.5, 0), entity -> entity instanceof LivingEntity);
            list.forEach(entity -> {
                Vec3d velocity = this.getVelocity();
                if (entity.isAlive() && entity.getBlockY() < this.getY() && velocity.y <= 0) {
                    this.setVelocity(velocity.x, 0.6, velocity.z);
                    if (!entity.getType().isIn(TagRegistry.IMMUNE_TO_BOOTS)) {
                        entity.damage(ModEntityDamageSource.stomp(this), 5F);
                        this.playSound(ModSounds.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
                    } else {
                        this.playSound(ModSounds.ENTITY_JUMP_BOOTS_BOUNCE, 1.0F, 1.0F);
                    }
                }
            });
        }
    }

    private boolean hasJumpBoots() {
        ItemStack feetSlot = this.getEquippedStack(EquipmentSlot.FEET);
        return (feetSlot.isOf(ItemRegistry.JUMP_BOOTS));
    }

    @Inject(at = @At("HEAD"), method = "jump")
    protected void jump(CallbackInfo info) {
        if (this.hasJumpBoots() && !this.isSneaking()) {
            this.playSound(ModSounds.ENTITY_JUMP_BOOTS_JUMP, 0.6F, this.getSoundPitch());
        }
    }

    // Higher jump
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

    // No fall damage
    @Inject(at = @At("HEAD"), method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        if (this.hasJumpBoots()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}


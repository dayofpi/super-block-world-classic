package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.BuzzyShellEntity;
import com.dayofpi.super_block_world.common.entities.mob.*;
import com.dayofpi.super_block_world.common.util.entity.ModEntityDamageSource;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stat;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class JumpBoots extends LivingEntity {
    @Shadow public abstract void increaseStat(Stat<?> stat, int amount);

    protected JumpBoots(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        if (this.isAlive() && this.hasJumpBoots() && !this.hasVehicle() && !this.isInSwimmingPose()) {
            List<Entity> list = world.getOtherEntities(this, this.getBoundingBox().offset(0, -0.5, 0), entity -> entity instanceof LivingEntity);
            list.forEach(entity -> {
                Vec3d velocity = this.getVelocity();
                boolean isHazard = entity.getType().isIn(TagInit.IGNORES_BOOTS);
                if (entity.isAlive() && !isHazard && entity.getBlockY() < this.getY() && velocity.y <= 0) {
                    if (!this.world.isClient) {
                        boolean isImmune = entity.getType().isIn(TagInit.STOMP_IMMUNE);
                        boolean isPet = entity instanceof TameableEntity tameableEntity && tameableEntity.getOwner() == this;

                        if (entity instanceof ParagoombaEntity paragoombaEntity) {
                            GoombaEntity goombaEntity = paragoombaEntity.convertTo(EntityInit.GOOMBA, true);
                            entity.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
                            if (goombaEntity != null) {
                                goombaEntity.setSize(paragoombaEntity.getSize());
                                goombaEntity.setGold(paragoombaEntity.isGold());
                                goombaEntity.setGrowable(paragoombaEntity.isGrowable());
                            }
                        } else if (entity instanceof KoopaEntity koopaEntity) {
                            if (koopaEntity instanceof ParatroopaEntity) {
                                koopaEntity.convertTo(EntityInit.KOOPA_TROOPA, true);
                            } else {
                                KoopaShellEntity koopaShellEntity = koopaEntity.convertTo(EntityInit.KOOPA_SHELL, true);
                                entity.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
                                if (koopaShellEntity != null) {
                                    koopaShellEntity.setKoopaType(koopaEntity.getKoopaType());
                                    koopaShellEntity.setHasMob(true);
                                }
                            }
                        } else if (entity instanceof BuzzyEntity buzzyEntity) {
                            BuzzyShellEntity buzzyShellEntity = buzzyEntity.convertTo(EntityInit.BUZZY_SHELL, true);
                            entity.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
                            if (buzzyShellEntity != null) {
                                buzzyShellEntity.setHasMob(true);
                            }
                            if (buzzyEntity.isSaddled())
                                buzzyEntity.dropItem(Items.SADDLE);
                        } else if (!isPet && !isImmune) {
                            entity.damage(ModEntityDamageSource.stomp(this), 5F);
                            entity.playSound(SoundInit.ENTITY_JUMP_BOOTS_ATTACK, 1.0F, 1.0F);
                        } else {
                            entity.playSound(SoundInit.ENTITY_JUMP_BOOTS_BOUNCE, 1.0F, 1.0F);
                            if (entity instanceof ToadEntity && random.nextBoolean()) {
                                ((ToadEntity) entity).setEmotion(2);
                            }
                        }
                    }
                    this.setVelocity(velocity.x, 0.6, velocity.z);

                }
            });
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

    // Higher jump
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


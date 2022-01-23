package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.common.entities.DryBonesShellEntity;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class HelmetEffects extends Entity {
    public HelmetEffects(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow protected boolean jumping;

    public boolean isInLava() {
        return !(this.getVehicle() instanceof DryBonesShellEntity) && !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.LAVA) > 0.0;
    }

    @Inject(at=@At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        double horizontalMultiplier = 1;
        final double maxSpeed = 0.1D;

        if (this.getVelocity().horizontalLengthSquared() < maxSpeed) {
            horizontalMultiplier = 1.25D;
        }

        if (this.getVehicle() instanceof DryBonesShellEntity) {
            this.setOnFire(false);
        }

        if (this.getEquippedStack(EquipmentSlot.FEET).isOf(ItemInit.CLOUD_BOOTS)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0, false, false, true));
        }

        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.RED_SHELL) && !this.isOnFire() && !this.isInLava()) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
        } else if (this.isOnWaterSurface() && this.getEquippedStack(EquipmentSlot.HEAD).isOf(PlantBlocks.ROCKET_FLOWER.asItem())) {
            this.setVelocity(this.getVelocity().multiply(horizontalMultiplier, 0, horizontalMultiplier));
            if (this.world.isClient) {
                world.addParticle(ParticleTypes.SPLASH, this.getX(), this.getY() + 0.2D, this.getZ(), 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.CLOUD, this.getX(), this.getY() + 2.0D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Inject(at=@At("HEAD"), method = "getPreferredEquipmentSlot", cancellable = true)
    private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> info) {
        if (stack.isOf(Items.SUNFLOWER) || stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof FlowerBlock) {
            info.setReturnValue(EquipmentSlot.HEAD);
            info.cancel();
        }
    }

    private boolean isOnWaterSurface() {
        return !this.jumping && this.isTouchingWater() && !this.isSubmergedInWater() && this.getVelocity().x != 0  && this.getVelocity().z != 0 && world.getFluidState(this.getBlockPos().up()).isEmpty();
    }
}

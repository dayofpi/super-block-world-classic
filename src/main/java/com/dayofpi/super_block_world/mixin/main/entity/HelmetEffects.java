package com.dayofpi.super_block_world.mixin.main.entity;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
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

    @Shadow public abstract float getMovementSpeed();

    @Inject(at=@At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.RED_SHELL) && !this.isOnFire() && !this.isInLava()) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
        }
    }

    @Inject(at=@At("HEAD"), method = "getPreferredEquipmentSlot", cancellable = true)
    private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> info) {
        if (stack.isOf(PlantBlocks.BLUE_SONGFLOWER.asItem()) || stack.isOf(PlantBlocks.PINK_SONGFLOWER.asItem()) || stack.isOf(PlantBlocks.YELLOW_SONGFLOWER.asItem()) || stack.isOf(PlantBlocks.PURPLE_SONGFLOWER.asItem()) || stack.isOf(PlantBlocks.ROCKET_FLOWER.asItem())) {
            info.setReturnValue(EquipmentSlot.HEAD);
            info.cancel();
        }
    }
    
    

    @Inject(at=@At("HEAD"), method = "canWalkOnFluid", cancellable = true)
    private void canWalkOnFluid(Fluid fluid, CallbackInfoReturnable<Boolean> info) {
        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(PlantBlocks.ROCKET_FLOWER.asItem())) {
            info.setReturnValue(!this.isSubmergedInWater() && this.getMovementSpeed() != 0 && world.getFluidState(this.getBlockPos().up()).isEmpty() && !this.isSneaking() && fluid.isStill(fluid.getDefaultState()) && fluid.isIn(FluidTags.WATER) && !fluid.isIn(TagInit.POISON));
            info.cancel();
        }
    }
}

package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.util.EnumAddons;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatMixin {
    @Shadow
    public abstract BoatEntity.Type getBoatType();

    @Inject(at = @At("HEAD"), method = "asItem", cancellable = true)
    private void asItem(CallbackInfoReturnable<Item> info) {
        if (this.getBoatType() == EnumAddons.AMANITA_BOAT) {
            info.setReturnValue(ModItems.AMANITA_BOAT);
        } else if (this.getBoatType() == EnumAddons.DARK_AMANITA_BOAT) {
            info.setReturnValue(ModItems.DARK_AMANITA_BOAT);
        } else if (this.getBoatType() == EnumAddons.BELL_BOAT) {
            info.setReturnValue(ModItems.BELL_BOAT);
        }
    }
}
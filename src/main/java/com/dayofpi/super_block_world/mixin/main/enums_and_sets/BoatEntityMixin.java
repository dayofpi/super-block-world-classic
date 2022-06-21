package com.dayofpi.super_block_world.mixin.main.enums_and_sets;

import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.common.util.mixin_aid.ModBoatType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow public abstract BoatEntity.Type getBoatType();

    @Inject(at=@At("HEAD"), method = "asItem", cancellable = true)
    private void asItem(CallbackInfoReturnable<Item> info) {
        if (this.getBoatType() == ModBoatType.AMANITA) {
            info.setReturnValue(ItemInit.AMANITA_BOAT);
            info.cancel();
        } else if (this.getBoatType() == ModBoatType.DARK_AMANITA) {
            info.setReturnValue(ItemInit.DARK_AMANITA_BOAT);
            info.cancel();
        } else if (this.getBoatType() == ModBoatType.BELL) {
            info.setReturnValue(ItemInit.BELL_BOAT);
            info.cancel();
        }
    }
}
package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.util.EnumAddons;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public abstract class ChestBoatMixin extends BoatEntity {

    public ChestBoatMixin(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "asItem", cancellable = true)
    private void asItem(CallbackInfoReturnable<Item> info) {
        if (this.getBoatType() == EnumAddons.AMANITA_BOAT) {
            info.setReturnValue(ModItems.AMANITA_CHEST_BOAT);
        } else if (this.getBoatType() == EnumAddons.DARK_AMANITA_BOAT) {
            info.setReturnValue(ModItems.DARK_AMANITA_CHEST_BOAT);
        } else if (this.getBoatType() == EnumAddons.BELL_BOAT) {
            info.setReturnValue(ModItems.BELL_CHEST_BOAT);
        }
    }
}
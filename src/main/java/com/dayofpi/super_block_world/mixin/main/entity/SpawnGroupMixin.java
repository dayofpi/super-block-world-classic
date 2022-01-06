package com.dayofpi.super_block_world.mixin.main.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public abstract class SpawnGroupMixin {
    @Shadow public abstract Text getName();

    @Shadow public abstract String getTranslationKey();

    @Inject(at = @At("HEAD"), method = "getSpawnGroup", cancellable = true)
    private void getSpawnGroup(CallbackInfoReturnable<SpawnGroup> info) {
        if (this.getTranslationKey().equals("entity.minecraft.snow_golem")) {
            info.setReturnValue(SpawnGroup.CREATURE);
            info.cancel();
        }
    }
}

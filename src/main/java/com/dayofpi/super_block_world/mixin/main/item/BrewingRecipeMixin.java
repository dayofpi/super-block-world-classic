package com.dayofpi.super_block_world.mixin.main.item;

import com.dayofpi.super_block_world.main.registry.main.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeMixin {
    @Shadow
    private static void registerPotionRecipe(Potion input, Item item, Potion output) {
    }

    @Inject(at=@At("TAIL"), method = "registerDefaults")
    private static void registerDefaults(CallbackInfo info) {
        registerPotionRecipe(Potions.AWKWARD, ItemInit.FUZZBALL, Potions.POISON);
    }
}

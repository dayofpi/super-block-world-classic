package com.dayofpi.super_block_world.registry.other;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class FoodComponents {
    public static final FoodComponent YOSHI_FRUIT = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.3F).snack().build();

    public static final FoodComponent YOSHI_COOKIE = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.6F).build();

    public static final FoodComponent SUPER_MUSHROOM = new FoodComponent.Builder()
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 2, 0), 1F)
            .alwaysEdible().snack().build();

    public static final FoodComponent POISON_MUSHROOM = new FoodComponent.Builder()
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 1000, 1), 1F)
            .alwaysEdible().snack().build();

    public static final FoodComponent GOLDEN_MUSHROOM = new FoodComponent.Builder()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2000, 0), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 2, 0), 1F)
            .alwaysEdible().snack().build();

    public static final FoodComponent ONE_UP = new FoodComponent.Builder()
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 6000, 4), 1F)
            .alwaysEdible().snack().build();

}

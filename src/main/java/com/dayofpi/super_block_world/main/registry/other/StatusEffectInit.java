package com.dayofpi.super_block_world.main.registry.other;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.status_effect.StarPowerEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class StatusEffectInit {
    public static final StatusEffect STAR_POWER = new StarPowerEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID().toString(), 0.3D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.randomUUID().toString(), 3.0, EntityAttributeModifier.Operation.ADDITION);

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Main.MOD_ID, "star_power"), STAR_POWER);
        Registry.register(Registry.POTION, new Identifier(Main.MOD_ID, "star_power"), new Potion(new StatusEffectInstance(STAR_POWER, 3600)));
        Registry.register(Registry.POTION, new Identifier(Main.MOD_ID, "long_star_power"), new Potion("star_power", new StatusEffectInstance(STAR_POWER, 9600)));
    }
}

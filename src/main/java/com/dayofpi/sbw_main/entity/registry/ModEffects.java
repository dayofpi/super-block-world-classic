package com.dayofpi.sbw_main.entity.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.item.registry.StarPowerEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static final StatusEffect STAR_POWER = new StarPowerEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.3D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void registerEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Main.MOD_ID, "star_power"), STAR_POWER);
        Registry.register(Registry.POTION, new Identifier(Main.MOD_ID, "star_power"), new Potion(new StatusEffectInstance(STAR_POWER, 3600)));
        Registry.register(Registry.POTION, new Identifier(Main.MOD_ID, "long_star_power"), new Potion("star_power", new StatusEffectInstance(STAR_POWER, 9600)));
    }
}

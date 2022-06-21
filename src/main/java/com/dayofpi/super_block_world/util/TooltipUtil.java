package com.dayofpi.super_block_world.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class TooltipUtil {
    public static void tooltipFromEffect(List<Text> tooltip, StatusEffectInstance statusEffectInstance, float durationMultiplier) {
        MutableText text = Text.translatable(statusEffectInstance.getTranslationKey());

        if (statusEffectInstance.getAmplifier() > 0) {
            text = Text.translatable("potion.withAmplifier", text, Text.translatable("potion.potency." + statusEffectInstance.getAmplifier()));
        }

        if (statusEffectInstance.getDuration() > 20) {
            text = Text.translatable("potion.withDuration", text, StatusEffectUtil.durationToString(statusEffectInstance, durationMultiplier));
        }

        text.formatted(statusEffectInstance.getEffectType().getCategory().getFormatting());
        tooltip.add(text);
    }

    public static void tooltipFromEffects(List<Text> tooltip, List<Pair<StatusEffectInstance, Float>> statusEffects) {
        statusEffects.forEach((statusEffectFloatPair -> tooltipFromEffect(tooltip, statusEffectFloatPair.getFirst(), statusEffectFloatPair.getSecond())));
    }
}

package com.dayofpi.super_block_world.common.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class TooltipUtil {
	public static void tooltipFromEffect(List<Text> tooltip, StatusEffectInstance statusEffectInstance, float durationMultiplier) {
		TranslatableText text = new TranslatableText(statusEffectInstance.getTranslationKey());

		if (statusEffectInstance.getAmplifier() > 0) {
			text = new TranslatableText("potion.withAmplifier", text, new TranslatableText("potion.potency." + statusEffectInstance.getAmplifier()));
		}

		if (statusEffectInstance.getDuration() > 20) {
			text = new TranslatableText("potion.withDuration", text, StatusEffectUtil.durationToString(statusEffectInstance, durationMultiplier));
		}

		text.formatted(statusEffectInstance.getEffectType().getCategory().getFormatting());
		tooltip.add(text);
	}

	public static void tooltipFromEffects(List<Text> tooltip, List<Pair<StatusEffectInstance, Float>> statusEffects) {
		statusEffects.forEach((statusEffectFloatPair -> tooltipFromEffect(tooltip, statusEffectFloatPair.getFirst(), statusEffectFloatPair.getSecond())));
	}
}

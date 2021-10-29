package com.dayofpi.sbw_main.item.types;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SuperStarItem extends Item {
    public SuperStarItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.hasStatusEffect(ModEffects.STAR_POWER)) {

            user.addStatusEffect((new StatusEffectInstance(ModEffects.STAR_POWER, 450, 0)));
            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_SUPER_STAR_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return TypedActionResult.success(itemStack, world.isClient());
        } else return TypedActionResult.pass(itemStack);
    }
}

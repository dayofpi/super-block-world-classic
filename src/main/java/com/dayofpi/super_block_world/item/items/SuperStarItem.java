package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.util.TooltipUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperStarItem extends Item {
    private static final StatusEffectInstance STAR_POWER_INSTANCE = new StatusEffectInstance(Main.STAR_POWER, 490, 0, false, false, true);

    public SuperStarItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        TooltipUtil.tooltipFromEffect(tooltip, STAR_POWER_INSTANCE, 1);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.hasStatusEffect(Main.STAR_POWER)) {
            world.playSound(null, user.getBlockPos(), Sounds.MUSIC_STARMAN_LEAD, SoundCategory.MUSIC, 1.0F, 1.0F);
            user.addStatusEffect(new StatusEffectInstance(Main.STAR_POWER, 490, 0, false, false, true));
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return TypedActionResult.success(itemStack, world.isClient());
        } else return TypedActionResult.pass(itemStack);
    }
}

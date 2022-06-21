package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Random;

public class FuzzyMagnetItem extends Item {
    public FuzzyMagnetItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundInit.ITEM_FUZZY_MAGNET, 1.0f, this.getSoundPitch());
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public float getSoundPitch() {
        Random random = new Random();
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 1200;
    }
}

package com.dayofpi.super_block_world.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrownItem extends Item {
    public ThrownItem(Settings settings) {
        super(settings);
    }

    protected void playSound(World world, Vec3d pos, SoundEvent soundEvent, float volume, float pitch) {
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, SoundCategory.NEUTRAL, volume, pitch);
    }

    protected void useStack(PlayerEntity user, ItemStack itemStack) {
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
    }
}

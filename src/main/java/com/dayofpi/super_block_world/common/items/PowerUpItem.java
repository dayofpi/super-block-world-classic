package com.dayofpi.super_block_world.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PowerUpItem extends Item {
    private final SoundEvent soundEvent;
    public PowerUpItem(SoundEvent soundEvent, Settings settings) {
        super(settings);
        this.soundEvent = soundEvent;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (this.soundEvent != null)
            user.playSound(this.soundEvent, 1.0F, 1.0F);
        return super.use(world, user, hand);
    }
}

package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.projectile.HammerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HammerItem extends ThrownItem {
    public HammerItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        this.playSound(world, user.getPos(), Sounds.ITEM_HAMMER, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 5);
        if (!world.isClient) {
            HammerEntity hammerEntity = new HammerEntity(world, user);
            hammerEntity.setItem(itemStack);
            hammerEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(hammerEntity);
        }

        this.useStack(user, itemStack);
        return TypedActionResult.success(itemStack, world.isClient());
    }
}

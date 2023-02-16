package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.projectile.BombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BombItem extends ThrownItem {
    public BombItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        this.playSound(world, user.getPos(), Sounds.ITEM_THROW, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 5);
        if (!world.isClient) {
            BombEntity bombEntity = new BombEntity(world, user);
            bombEntity.setItem(itemStack);
            bombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(bombEntity);
        }

        this.useStack(user, itemStack);
        return TypedActionResult.success(itemStack, world.isClient());
    }
}

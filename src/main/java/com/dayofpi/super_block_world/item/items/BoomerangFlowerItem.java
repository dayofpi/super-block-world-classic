package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.projectile.BoomerangEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BoomerangFlowerItem extends Item {
    public BoomerangFlowerItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), Sounds.ITEM_BOOMERANG_FLOWER, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 20);
        if (!world.isClient) {
            BoomerangEntity boomerangEntity = new BoomerangEntity(world, user);
            boomerangEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(boomerangEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (user instanceof ServerPlayerEntity) {
            itemStack.damage(1, user, ((e) -> e.sendToolBreakStatus(hand)));
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}

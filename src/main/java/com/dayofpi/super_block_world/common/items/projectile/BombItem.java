package com.dayofpi.super_block_world.common.items.projectile;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.BombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BombItem extends Item {
   public BombItem(Settings settings) {
      super(settings);
   }

   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack itemStack = user.getStackInHand(hand);
      world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.ITEM_PROJECTILE_BOMB, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
      user.getItemCooldownManager().set(this, 5);
      if (!world.isClient) {
         BombEntity bombEntity = new BombEntity(world, user);
         bombEntity.setItem(itemStack);
         bombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
         world.spawnEntity(bombEntity);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.getAbilities().creativeMode) {
         itemStack.decrement(1);
      }

      return TypedActionResult.success(itemStack, world.isClient());
   }
}

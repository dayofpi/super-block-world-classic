package com.dayofpi.super_block_world.main.common.item.projectile;

import com.dayofpi.super_block_world.main.common.entity.projectile.StarBitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StarBitItem extends Item {
   public StarBitItem(Settings settings) {
      super(settings);
   }

   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack itemStack = user.getStackInHand(hand);
      world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.NEUTRAL, 1.0F, 1.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
      if (!world.isClient) {
         StarBitEntity starBitEntity = new StarBitEntity(world, user);
         starBitEntity.setItem(itemStack);
         starBitEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.1F);
         world.spawnEntity(starBitEntity);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.getAbilities().creativeMode) {
         itemStack.decrement(1);
      }

      return TypedActionResult.success(itemStack, world.isClient());
   }
}

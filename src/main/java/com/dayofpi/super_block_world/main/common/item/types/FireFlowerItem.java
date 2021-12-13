package com.dayofpi.super_block_world.main.common.item.types;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.type.projectiles.FlowerFireballEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FireFlowerItem extends Item {
   public FireFlowerItem(Settings settings) {
      super(settings);
   }

   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack itemStack = user.getStackInHand(hand);
      world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_FIRE_FLOWER_SHOOT, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
      user.getItemCooldownManager().set(this, 5);
      if (!world.isClient) {
         FlowerFireballEntity fireballEntity = new FlowerFireballEntity(null, user, world);
         fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
         fireballEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
         world.spawnEntity(fireballEntity);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.getAbilities().creativeMode) {
         itemStack.damage(1, user, ((e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND)));
      }

      return TypedActionResult.success(itemStack, world.isClient());
   }
}

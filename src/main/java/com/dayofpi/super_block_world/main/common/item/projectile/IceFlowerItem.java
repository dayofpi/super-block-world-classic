package com.dayofpi.super_block_world.main.common.item.projectile;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.projectile.IceballEntity;
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

public class IceFlowerItem extends Item {
   public IceFlowerItem(Settings settings) {
      super(settings);
   }

   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack itemStack = user.getStackInHand(hand);
      world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_ICE_FLOWER_SHOOT, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
      user.getItemCooldownManager().set(this, 5);
      if (!world.isClient) {
         IceballEntity iceballEntity = new IceballEntity(null, user, world);
         iceballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
         iceballEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
         world.spawnEntity(iceballEntity);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.getAbilities().creativeMode) {
         itemStack.damage(1, user, ((e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND)));
      }

      return TypedActionResult.success(itemStack, world.isClient());
   }
}

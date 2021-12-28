package com.dayofpi.super_block_world.main.common.item.projectile;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.projectile.HammerEntity;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HammerItem extends Item {
   private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

   public HammerItem(Settings settings) {
      super(settings);
      ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 4.0D, EntityAttributeModifier.Operation.ADDITION));
      builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.5F, EntityAttributeModifier.Operation.ADDITION));
      this.attributeModifiers = builder.build();
   }

   public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      stack.damage(1, attacker, ((e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND)));
      return true;
   }

   public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
      if (state.getHardness(world, pos) != 0.0F) {
         stack.damage(2, miner, ((e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND)));
      }

      return true;
   }

   public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
      return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
   }

   public boolean canRepair(ItemStack stack, ItemStack ingredient) {
      return ingredient.isOf(ItemRegistry.BRONZE_INGOT) || super.canRepair(stack, ingredient);
   }

   public int getEnchantability() {
      return 1;
   }

   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack itemStack = user.getStackInHand(hand);
      world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_PROJECTILE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.5F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
      user.getItemCooldownManager().set(this, 5);
      if (!world.isClient) {
         HammerEntity hammerEntity = new HammerEntity(world, user);
         hammerEntity.setItem(itemStack);
         hammerEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
         world.spawnEntity(hammerEntity);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.getAbilities().creativeMode) {
         itemStack.decrement(1);
      }

      return TypedActionResult.success(itemStack, world.isClient());
   }
}

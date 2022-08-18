package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.projectile.MagicBeamEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MagicWandItem extends Item implements Vanishable {
    public MagicWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 2700;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) {
            return;
        }
        playerEntity.getItemCooldownManager().set(this, 20);
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        if (i < 25) {
            return;
        }
        if (!world.isClient) {
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
            MagicBeamEntity magicBeamEntity = new MagicBeamEntity(world, user);
            magicBeamEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.8F, 1.0F);
            world.spawnEntity(magicBeamEntity);
        }
        world.playSoundFromEntity(null, user, Sounds.ITEM_MAGIC_WAND, SoundCategory.PLAYERS, 1.0F, user.getSoundPitch());
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        }
        world.playSoundFromEntity(null, user, Sounds.ENTITY_GENERIC_PREPARE_SPELL, SoundCategory.PLAYERS, 0.5F,1.0F);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}

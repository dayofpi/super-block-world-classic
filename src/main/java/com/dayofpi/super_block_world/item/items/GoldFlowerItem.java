package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.projectile.GoldFireballEntity;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GoldFlowerItem extends Item {
    public GoldFlowerItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), Sounds.ITEM_FIRE_FLOWER, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 5);
        if (!world.isClient) {
            GoldFireballEntity fireballEntity = new GoldFireballEntity(ModEntities.GOLD_FIREBALL, user, world);
            fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(fireballEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (user instanceof ServerPlayerEntity) {
            itemStack.damage(1, user, ((e) -> e.sendToolBreakStatus(hand)));
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}

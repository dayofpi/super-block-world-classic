package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.entity.entities.misc.GooMeEntity;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class GooMeItem extends Item {
    public GooMeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        }

        if (world instanceof ServerWorld) {
            GooMeEntity gooMe = (GooMeEntity) ModEntities.GOO_ME.spawnFromItemStack((ServerWorld) world, itemStack, user, blockHitResult.getBlockPos(), SpawnReason.MOB_SUMMONED, true, false);
            if (gooMe == null)
                return TypedActionResult.pass(itemStack);
            world.emitGameEvent(user, GameEvent.ENTITY_PLACE, blockHitResult.getBlockPos());
        }

        world.playSound(null, blockPos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}

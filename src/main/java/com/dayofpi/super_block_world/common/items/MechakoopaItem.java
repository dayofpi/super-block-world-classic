package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.common.entities.hostile.MechakoopaEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class MechakoopaItem extends Item {
    public MechakoopaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        }

        if (world instanceof ServerWorld) {
            MechakoopaEntity mechakoopa = (MechakoopaEntity) ModEntities.MECHAKOOPA.spawnFromItemStack((ServerWorld) world, itemStack, user, blockHitResult.getBlockPos(), SpawnReason.MOB_SUMMONED, true, false);
            if (mechakoopa == null)
                return TypedActionResult.pass(itemStack);
            mechakoopa.setOwner(user);
            world.emitGameEvent(user, GameEvent.ENTITY_PLACE, blockHitResult.getBlockPos());
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}

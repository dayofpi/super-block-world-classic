package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.common.entities.DryBonesShellEntity;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class DryBonesShellItem extends Item {
    public DryBonesShellItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockEntity;
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }
        ItemStack itemStack = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockState blockState = world.getBlockState(blockPos);
        blockEntity = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
        EntityType<?> mobSpawnerLogic = EntityInit.DRY_BONES_SHELL;
        DryBonesShellEntity dryBonesShellEntity = (DryBonesShellEntity) mobSpawnerLogic.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockEntity, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockEntity) && direction == Direction.UP);
        if (dryBonesShellEntity != null)
            dryBonesShellEntity.setPersistent();
        itemStack.decrement(1);
        world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
        return ActionResult.CONSUME;
    }

}

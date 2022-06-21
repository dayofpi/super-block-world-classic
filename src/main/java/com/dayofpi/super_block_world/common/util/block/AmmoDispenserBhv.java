/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.common.util.block;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public abstract class AmmoDispenserBhv
extends ItemDispenserBehavior {
    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        ServerWorld world = pointer.getWorld();
        Position position = DispenserBlock.getOutputLocation(pointer);
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        ProjectileEntity projectileEntity = this.createProjectile(world, position, stack);
        projectileEntity.setVelocity(direction.getOffsetX(), (float)direction.getOffsetY() + 0.1f, direction.getOffsetZ(), 1.1F, 5.0F);
        world.spawnEntity(projectileEntity);
        stack.setDamage(stack.getDamage() + 1);
        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        pointer.getWorld().syncWorldEvent(WorldEvents.DISPENSER_LAUNCHES_PROJECTILE, pointer.getPos(), 0);
    }

    protected abstract ProjectileEntity createProjectile(World var1, Position var2, ItemStack var3);
}


/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.common.entities.projectile.BoomerangEntity;
import com.dayofpi.super_block_world.common.entities.projectile.GoldFireballEntity;
import com.dayofpi.super_block_world.common.entities.projectile.IceballEntity;
import com.dayofpi.super_block_world.common.entities.projectile.ModFireballEntity;
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

public abstract class AmmoDispenserBehavior extends ItemDispenserBehavior {
    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        ServerWorld world = pointer.getWorld();
        Position position = DispenserBlock.getOutputLocation(pointer);
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        ProjectileEntity projectileEntity = this.createProjectile(world, position, stack);
        projectileEntity.setVelocity(direction.getOffsetX(), (float) direction.getOffsetY() + 0.1f, direction.getOffsetZ(), 1.1F, 5.0F);
        world.spawnEntity(projectileEntity);
        stack.damage(1, world.getRandom(), null);
        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        pointer.getWorld().syncWorldEvent(WorldEvents.DISPENSER_LAUNCHES_PROJECTILE, pointer.getPos(), 0);
    }

    protected abstract ProjectileEntity createProjectile(World world, Position position, ItemStack stack);

    public static class FireFlower extends AmmoDispenserBehavior {
        @Override
        protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return new ModFireballEntity(world, position.getX(), position.getY(), position.getZ());
        }
    }

    public static class IceFlower extends AmmoDispenserBehavior {
        @Override
        protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return new IceballEntity(world, position.getX(), position.getY(), position.getZ());
        }
    }

    public static class BoomerangFlower extends AmmoDispenserBehavior {
        @Override
        protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return new BoomerangEntity(world, position.getX(), position.getY(), position.getZ());
        }
    }

    public static class GoldFlower extends AmmoDispenserBehavior {
        @Override
        protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return new GoldFireballEntity(world, position.getX(), position.getY(), position.getZ());
        }
    }
}


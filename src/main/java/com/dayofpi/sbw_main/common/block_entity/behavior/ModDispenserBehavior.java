package com.dayofpi.sbw_main.common.block_entity.behavior;

import com.dayofpi.sbw_main.registry.block.ModBlocks;
import com.dayofpi.sbw_main.common.entity.type.projectiles.*;
import com.dayofpi.sbw_main.registry.ModItems;
import com.dayofpi.sbw_main.util.mixin_aid.ModBoatType;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.*;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ModDispenserBehavior {
    public static void addDispenserBehaviors() {
        DispenserBehavior dispenserBehavior = new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                FluidModificationItem fluidModificationItem = (FluidModificationItem) stack.getItem();
                BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
                    fluidModificationItem.onEmptied(null, world, stack, blockPos);
                    return new ItemStack(Items.BUCKET);
                } else return this.fallbackBehavior.dispense(pointer, stack);

            }
        };

        DispenserBlock.registerBehavior(Items.FLINT_AND_STEEL, new FallibleItemDispenserBehavior() {
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                World world = pointer.getWorld();
                this.setSuccess(true);
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockPos = pointer.getPos().offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                if (AbstractFireBlock.canPlaceAt(world, blockPos, direction)) {
                    world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                    world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                } else if (blockState.isOf(ModBlocks.STONE_TORCH) && !blockState.get(Properties.LIT)) {
                    world.setBlockState(blockPos, blockState.with(Properties.LIT, true));
                    world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
                }

                if (this.isSuccess() && stack.damage(1, world.random, null)) {
                    stack.setCount(0);
                }

                return stack;
            }
        });

        DispenserBlock.registerBehavior(ModItems.TURNIP, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new TurnipEntity(world, position.getX(), position.getY(), position.getZ()), (turnipEntity) -> turnipEntity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(ModItems.HAMMER, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new HammerEntity(world, position.getX(), position.getY(), position.getZ()), (hammerEntity) -> hammerEntity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(ModItems.BOMB, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new BombEntity(world, position.getX(), position.getY(), position.getZ()), (hammerEntity) -> hammerEntity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(ModItems.FIRE_FLOWER, new AmmoDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                PersistentProjectileEntity persistentProjectileEntity = new FlowerFireballEntity(world, position.getX(), position.getY(), position.getZ());
                persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
                return persistentProjectileEntity;
            }
        });

        DispenserBlock.registerBehavior(ModItems.ICE_FLOWER, new AmmoDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                PersistentProjectileEntity persistentProjectileEntity = new IceballEntity(world, position.getX(), position.getY(), position.getZ());
                persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
                return persistentProjectileEntity;
            }
        });

        DispenserBlock.registerBehavior(ModItems.POISON_BUCKET, dispenserBehavior);
        DispenserBlock.registerBehavior(ModItems.AMANITA_BOAT, new BoatDispenserBehavior(ModBoatType.AMANITA));
        DispenserBlock.registerBehavior(ModBlocks.DONUT_BLOCK.asItem(), new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ModBlocks.TRAMPOLINE.asItem(), new BlockPlacementDispenserBehavior());
    }
}

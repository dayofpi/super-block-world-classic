package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;
@SuppressWarnings("deprecation")
public class FreezieBlock extends FallingBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    public FreezieBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);
        this.freeze(world, hit.getBlockPos());
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        super.onLanding(world, pos, fallingBlockState, currentStateInPos, fallingBlockEntity);
        this.freeze(world, pos);
        world.breakBlock(pos, false);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);
        this.freeze(world, pos);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.freeze(world, pos);
        }
    }

    private void freeze(World world, BlockPos pos) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, Box.from(Vec3d.ofCenter(pos)).expand(3, 3, 3), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);

        for (int i = 0; i < 6; i++) {
            double rand = world.random.nextBoolean() ? 0.05 : -0.05;
            world.addParticle(ParticleTypes.SNOWFLAKE, pos.getX() + rand, pos.getY() + rand, pos.getZ() + rand, 0.0D, 0.0D, 0.0D);
        }

        world.playSound(null, pos, SoundInit.ENTITY_ICEBALL_FREEZE, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!world.isClient) {
            for (LivingEntity livingEntity : list) {
                if (livingEntity.canFreeze()) {
                    livingEntity.setFrozenTicks(400);
                }
            }

            for (BlockPos iterable : BlockPos.iterateOutwards(pos, 3, 3, 3)) {
                BlockState iterableState = world.getBlockState(iterable);
                FluidState iterableFluid = world.getFluidState(iterable);
                if (iterableState.isOf(PlantBlocks.MUNCHER)) {
                    world.setBlockState(iterable, PlantBlocks.FROZEN_MUNCHER.getDefaultState(), 2);
                } else if (iterableFluid.isIn(FluidTags.LAVA)) {
                    world.setBlockState(iterable, Blocks.OBSIDIAN.getDefaultState(), 2);
                } else if (iterableState.isOf(BlockInit.TOADSTOOL_SOIL)) {
                    world.setBlockState(iterable, BlockInit.SHERBET_SOIL.getDefaultState());
                } else if (iterableState.isOf(BlockInit.TOADSTOOL_GRASS)) {
                    world.setBlockState(iterable, BlockInit.SNOWY_SHERBET_SOIL.getDefaultState());
                } else if (iterableState.isOf(BlockInit.VANILLATE)) {
                    world.setBlockState(iterable, BlockInit.FROSTY_VANILLATE.getDefaultState(), 2);
                } else if (iterableState.isOf(BlockInit.VANILLATE_CRUMBLE)) {
                    world.setBlockState(iterable, BlockInit.FROSTY_VANILLATE_CRUMBLE.getDefaultState(), 2);
                } else if (iterableState.isOf(BlockInit.TOPPED_VANILLATE)) {
                    world.setBlockState(iterable, BlockInit.FROSTED_VANILLATE.getDefaultState(), 2);
                } else if (iterableState.isIn(TagInit.TOPPINGS)) {
                    world.setBlockState(iterable, BlockInit.FROZEN_ORE.getDefaultState(), 2);
                } else if (iterableFluid.isIn(FluidTags.WATER)) {
                    world.setBlockState(iterable, Blocks.ICE.getDefaultState(), 2);
                } else if (iterableState.isAir() && world.getBlockState(iterable.down()).isSideSolidFullSquare(world, iterable.down(), Direction.UP)) {
                    world.setBlockState(iterable, Blocks.SNOW.getDefaultState());
                }
            }
        }
    }
}

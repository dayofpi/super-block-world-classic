package com.dayofpi.super_block_world.common.block.soil;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.Random;

public class ToadstoolFarmland extends FarmlandBlock {
    public ToadstoolFarmland(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? BlockInit.TOADSTOOL_SOIL.getDefaultState() : super.getPlacementState(ctx);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            setToSoil(state, world, pos);
        }
    }

    public static void setToSoil(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, BlockInit.TOADSTOOL_SOIL.getDefaultState(), world, pos));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(MOISTURE);
        if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, state.with(MOISTURE, i - 1), Block.NOTIFY_LISTENERS);
            } else if (!hasCrop(world, pos)) {
                setToSoil(state, world, pos);
            }
        } else if (i < 7) {
            world.setBlockState(pos, state.with(MOISTURE, 7), Block.NOTIFY_LISTENERS);
        }
    }

    private static boolean isWaterNearby(WorldView world, BlockPos pos) {
        Iterator<BlockPos> var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();
        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }
            blockPos = var2.next();
        } while (!world.getFluidState(blockPos).isIn(FluidTags.WATER));
        return true;
    }

    private static boolean hasCrop(BlockView world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && world.random.nextFloat() < fallDistance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            setToSoil(state, world, pos);
        }
        entity.handleFallDamage(fallDistance, 1.0F, DamageSource.FALL);
    }
}

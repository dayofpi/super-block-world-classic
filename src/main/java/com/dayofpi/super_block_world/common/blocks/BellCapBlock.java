package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
@SuppressWarnings("deprecation")
public class BellCapBlock extends LeavesBlock {
    public BellCapBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            world.playSound(null, blockPos, Sounds.BLOCK_BELL_CAP_JINGLE, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
        }

    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos blockPos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, blockPos, state, blockEntity, stack);
        world.playSound(null, blockPos, Sounds.BLOCK_BELL_CAP_JINGLE, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
    }

    @Override
    public void onPlaced(World world, BlockPos blockPos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.playSound(null, blockPos, Sounds.BLOCK_BELL_CAP_JINGLE, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos blockPos, Random random) {
        super.randomDisplayTick(state, world, blockPos, random);
        if (random.nextInt(80) == 0)
            world.playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Sounds.BLOCK_BELL_CAP_JINGLE, SoundCategory.BLOCKS, random.nextFloat(), 0.5F + world.random.nextFloat() * 1.2F, false);
    }
}

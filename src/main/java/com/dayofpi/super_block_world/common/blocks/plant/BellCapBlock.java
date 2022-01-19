package com.dayofpi.super_block_world.common.blocks.plant;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BellCapBlock extends LeavesBlock {
    public BellCapBlock(Settings settings) {
        super(settings);
    }

    private void playJingle(World world, BlockPos blockPos) {
        world.playSound(null, blockPos, SoundInit.BLOCK_BELL_CAP_JINGLE, SoundCategory.BLOCKS, 1.0f, 0.85f + (world.random.nextFloat() * 0.3f));
    }

    @Override
    public void onPlaced(World world, BlockPos blockPos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, blockPos, state, placer, itemStack);
        playJingle(world, blockPos);
    }

    @Override
    public void onBreak(World world, BlockPos blockPos, BlockState state, PlayerEntity player) {
        super.onBreak(world, blockPos, state, player);
        playJingle(world, blockPos);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);
        playJingle(world, hit.getBlockPos());
    }
}

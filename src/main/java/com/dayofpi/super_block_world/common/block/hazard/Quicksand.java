package com.dayofpi.super_block_world.common.block.hazard;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Quicksand extends Block {
    public Quicksand(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getBlockX() == pos.getX() && entity.getBlockZ() == pos.getZ() && entity.getY() < pos.getY() - 0.55  ) {
            if (entity instanceof LivingEntity) {
                entity.damage(DamageSource.IN_WALL, 2.0F);
            }
        }
        if (entity.getY() + 0.55 > pos.getY())
            entity.slowMovement(state, new Vec3d(0.25, 0.6, 0.25));
    }
}

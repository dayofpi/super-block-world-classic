package com.dayofpi.sbw_main.block.types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class QuicksandBlock extends Block {
    public QuicksandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getBlockX() == pos.getX() && entity.getBlockZ() == pos.getZ() && entity.getY() < pos.getY()-0.5) {
            entity.damage(DamageSource.IN_WALL, 2.0F);
        }
        entity.slowMovement(state, new Vec3d(0.25, 0.5, 0.25));
    }
}

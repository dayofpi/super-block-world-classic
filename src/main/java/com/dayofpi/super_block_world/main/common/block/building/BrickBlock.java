package com.dayofpi.super_block_world.main.common.block.building;

import com.dayofpi.super_block_world.main.registry.general.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BrickBlock extends Block {
    public BrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.2D, 0.0D, 16.0D, 16D, 16.0D);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        boolean jumpUnder = entity.getY() < blockPos.getY();
        if (jumpUnder && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagRegistry.SHELLMETS))
            world.breakBlock(blockPos, true);
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }
}

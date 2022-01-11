package com.dayofpi.super_block_world.common.block.building;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class BrickBlock extends Block {
    private boolean wasHit = false;
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
        if (jumpUnder && entity instanceof PlayerEntity livingEntity) {
            if (!wasHit) {
                wasHit = true;
                world.playSound(null, blockPos, SoundInit.BLOCK_EMPTY_BLOCK_BUMP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            } else {
                world.createAndScheduleBlockTick(blockPos, this, 3);
            }
            if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagInit.SHELLMETS))
                world.breakBlock(blockPos, true);
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        if (world.getEntitiesByClass(PlayerEntity.class, Box.from(Vec3d.of(blockPos)), EntityPredicates.EXCEPT_SPECTATOR).isEmpty()) {
            this.wasHit = false;
        }
    }
}

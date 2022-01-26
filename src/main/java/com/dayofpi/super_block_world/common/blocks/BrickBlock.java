package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.blocks.mechanics.ReactiveBlock;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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

@SuppressWarnings("deprecation")
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
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        super.onBlockBreakStart(state, world, pos, player);
        if (player.getEquippedStack(EquipmentSlot.MAINHAND).isOf(ItemInit.SUPER_PICKAX)) {
            for (BlockPos blockPos : BlockPos.iterateOutwards(pos, 4, 4, 4)) {
                BlockState states = world.getBlockState(blockPos);
                if (states.isIn(TagInit.BRICKS)) {
                    world.breakBlock(blockPos, true);
                } else if (states.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.activate(states, world, blockPos);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        boolean jumpUnder = entity.getY() < blockPos.getY();
        if (!world.isClient && state.getBlock() != BlockInit.HARDSTONE_BRICKS) {
            if (jumpUnder && entity instanceof LivingEntity livingEntity) {
                if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagInit.SHELLMETS)) {
                    world.breakBlock(blockPos, true);
                } else if (!wasHit && livingEntity.isPlayer()) {
                    wasHit = true;
                    world.playSound(null, blockPos, SoundInit.BLOCK_EMPTY_BLOCK_BUMP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    world.createAndScheduleBlockTick(blockPos, this, 3);
                }
            }
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

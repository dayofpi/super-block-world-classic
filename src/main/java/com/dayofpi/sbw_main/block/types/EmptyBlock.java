package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.block_entity.QuestionBlockBE;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class EmptyBlock extends Block {
    private boolean wasHit = false;
    public EmptyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.2D, 0.0D, 16.0D, 16D, 16.0D);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        if (!world.isClient) {
            boolean jumpUnder = entity.getY() < blockPos.getY();
            if (jumpUnder) {
                if (!wasHit) {
                    wasHit = true;
                    world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_BUMP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    world.createAndScheduleBlockTick(blockPos, this, 3);
                }
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        if (world.getEntitiesByClass(PlayerEntity.class, Box.from(Vec3d.of(blockPos)), EntityPredicates.EXCEPT_SPECTATOR).isEmpty()) {
            this.wasHit = false;
        }
    }

        @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isEmpty()) {
            if (world.isClient) {
                return ActionResult.SUCCESS;
            } else {
                world.setBlockState(blockPos, ModBlocks.QUESTION_BLOCK.getDefaultState(), 2);
                if (world.getBlockEntity(blockPos) instanceof QuestionBlockBE blockEntity) {
                    blockEntity.setStack(0, itemStack.copy());
                    blockEntity.markDirty();
                    if (!player.isCreative()) {
                        player.getStackInHand(hand).setCount(0);
                    }
                }
                world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_STORE, SoundCategory.NEUTRAL, 2.0F, 1.0F);
                ParticleUtil.spawnParticle(world, blockPos, ParticleTypes.WAX_ON, UniformIntProvider.create(3, 5));
                return ActionResult.CONSUME;
            }
        } else return ActionResult.PASS;
    }
}

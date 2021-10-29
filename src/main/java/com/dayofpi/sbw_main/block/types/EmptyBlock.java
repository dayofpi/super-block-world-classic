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
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class EmptyBlock  extends Block {
    public EmptyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.2D, 0.0D, 16.0D, 16D, 16.0D);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        boolean jumpUnder = entity.getY() < blockPos.getY();
        if (jumpUnder) {
            world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_BUMP, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isEmpty()) {
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
            return ActionResult.success(world.isClient);
        } else return ActionResult.PASS;
    }
}

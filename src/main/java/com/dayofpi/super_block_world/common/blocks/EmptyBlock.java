package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.block_entities.QuestionBlockBE;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class EmptyBlock extends Block implements InventoryProvider {
    public EmptyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return false;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return 0;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            return ActionResult.PASS;
        }

        if (!world.isClient) {
            world.setBlockState(blockPos, ModBlocks.QUESTION_BLOCK.getDefaultState());
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof QuestionBlockBE) {
                ((Inventory) blockEntity).setStack(0, itemStack.copy());
                world.updateComparators(blockPos, this);
            }
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
            if (!player.isCreative())
                itemStack.setCount(0);

            Random random = world.getRandom();
            for (int i = 0; i < 4; ++i) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            world.playSound(null, blockPos, Sounds.BLOCK_EMPTY_BLOCK_STORE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return new EmptyInventory(world, pos);
    }

    static class EmptyInventory extends SimpleInventory implements SidedInventory {
        private final WorldAccess world;
        private final BlockPos blockPos;
        private boolean dirty;

        public EmptyInventory(WorldAccess world, BlockPos blockPos) {
            super(1);
            this.world = world;
            this.blockPos = blockPos;
        }

        @Override
        public int[] getAvailableSlots(Direction side) {
            return new int[]{0};
        }

        @Override
        public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
            return !this.dirty;
        }

        @Override
        public boolean canExtract(int slot, ItemStack stack, Direction dir) {
            return false;
        }

        @Override
        public void markDirty() {
            ItemStack itemStack = this.getStack(0);
            if (!itemStack.isEmpty()) {
                this.dirty = true;
                this.world.setBlockState(blockPos, ModBlocks.QUESTION_BLOCK.getDefaultState(), NOTIFY_ALL);
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                if (blockEntity instanceof QuestionBlockBE) {
                    ((Inventory) blockEntity).setStack(0, itemStack.copy());
                }

                Random random = world.getRandom();

                for (int i = 0; i < 4; ++i) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}

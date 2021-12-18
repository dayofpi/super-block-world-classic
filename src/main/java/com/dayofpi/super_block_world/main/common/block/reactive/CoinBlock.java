package com.dayofpi.super_block_world.main.common.block.reactive;

import com.dayofpi.super_block_world.main.common.block_entity.CoinBlockBE;
import com.dayofpi.super_block_world.main.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
@SuppressWarnings("deprecation")
public class CoinBlock extends ReactiveBlock implements BlockEntityProvider {
    public static final IntProperty TYPE;

    public CoinBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TYPE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    static {
        // 0 = ? Block; 1 = Toadstone; 2 = Gloomstone; 3 = Gold; 4 = Crystal
        TYPE = IntProperty.of("type", 0, 4);
    }

    @Override
    public void activate(BlockState state, World world, BlockPos blockPos) {
        world.createAndScheduleBlockTick(blockPos, this, 2);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(blockPos);
        if (blockEntity != null) {
            int coinCount = blockEntity.getStack(0).getCount();
            BlockPos pos = blockPos.add(0, 1, 0);
            if (state.isSolidBlock(world, blockPos.up())) {
                if (!state.isSolidBlock(world, blockPos.down())) {
                    pos = blockPos.add(0, -1, 0);
                }
            }
            if (coinCount != 0) {
                Block.dropStack(world, pos, new ItemStack(ItemRegistry.COIN));
                world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_COIN, SoundCategory.NEUTRAL, 0.8F, 1.0F);
                ParticleUtil.spawnParticle(world, blockPos, ParticleTypes.WAX_OFF, UniformIntProvider.create(1, 2));
                if (coinCount > 1) {
                    blockEntity.getStack(0).setCount(coinCount - 1);
                } else {
                    world.setBlockState(blockPos, pushEntitiesUpBeforeBlockChange(state, BlockRegistry.EMPTY_BLOCK.getDefaultState(), world, blockPos));
                    world.removeBlockEntity(blockPos);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoinBlockBE(pos, state);
    }
}

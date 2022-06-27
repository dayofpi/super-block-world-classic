package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.entities.passive.LilOinkEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

@SuppressWarnings("deprecation")
public class LilOinkEggBlock extends Block {
    private static final IntProperty HATCH = Properties.HATCH;
    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public LilOinkEggBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HATCH, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldHatchProgress(world)) {
            int i = state.get(HATCH);
            if (i < 2) {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                world.setBlockState(pos, state.with(HATCH, i + 1), Block.NOTIFY_LISTENERS);
            } else {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                world.removeBlock(pos, false);
                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
                LilOinkEntity lilOinkEntity = ModEntities.LIL_OINK.create(world);
                if (lilOinkEntity != null) {
                    lilOinkEntity.setBreedingAge(-24000);
                    lilOinkEntity.refreshPositionAndAngles((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, 0.0f, 0.0f);
                    world.spawnEntity(lilOinkEntity);
                }
            }
        }
    }

    private boolean shouldHatchProgress(World world) {
        return world.random.nextInt(300) == 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}

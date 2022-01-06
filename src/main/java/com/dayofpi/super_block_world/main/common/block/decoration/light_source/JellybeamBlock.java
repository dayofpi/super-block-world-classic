package com.dayofpi.super_block_world.main.common.block.decoration.light_source;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.main.util.entity.ModDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class JellybeamBlock extends HorizontalFacingBlock {
    public static final DirectionProperty FACING;
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    static {
        FACING = HorizontalFacingBlock.FACING;
    }

    public JellybeamBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient) {
            if (entity.damage(ModDamageSource.JELLYBEAM, 1.0F)) {
                world.playSound(null, pos, SoundInit.BLOCK_JELLYBEAM_DAMAGE, SoundCategory.BLOCKS, 1.0F, this.getSoundPitch());
            }

        }
    }

    public float getSoundPitch() {
        Random random = new Random();
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

}

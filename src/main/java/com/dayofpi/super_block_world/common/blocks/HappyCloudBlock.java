package com.dayofpi.super_block_world.common.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class HappyCloudBlock extends HorizontalFacingBlock {
    private static final BooleanProperty SAD;

    static {
        SAD = BooleanProperty.of("sad");
    }

    public HappyCloudBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(SAD, false));
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!state.get(SAD))
            world.setBlockState(pos, state.with(SAD, true));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(SAD))
            return;
        world.addParticle(ParticleTypes.DRIPPING_WATER, pos.getX() + random.nextFloat(), pos.getY(), pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.DRIPPING_WATER, pos.getX() + random.nextFloat(), pos.getY(), pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!(context instanceof EntityShapeContext))
            return VoxelShapes.empty();
        Entity entity = ((EntityShapeContext) context).getEntity();
        if (entity != null) {
            boolean bl = entity instanceof FallingBlockEntity;
            if (bl || CloudBlock.canWalkOnCloud(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false)) {
                return super.getCollisionShape(state, world, pos, context);
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SAD);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

}

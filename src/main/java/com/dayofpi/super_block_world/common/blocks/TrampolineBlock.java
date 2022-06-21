package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class TrampolineBlock extends FallingBlock {
    public static final BooleanProperty SQUASHED;
    protected static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape SQUASHED_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        SQUASHED = BooleanProperty.of("squash");
    }

    public TrampolineBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(SQUASHED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(SQUASHED) ? SQUASHED_SHAPE : VoxelShapes.fullCube();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.0F, DamageSource.FALL);
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        BlockState blockState = world.getBlockState(entity.getLandingPos());
        BlockPos blockPos = entity.getLandingPos();
        boolean squashed = world.getBlockState(blockPos).get(SQUASHED);

        if (squashed) {
            if (!entity.isSneaking()) {
                entity.world.setBlockState(blockPos, blockState.with(SQUASHED, false));
                entity.getWorld().emitGameEvent(null, GameEvent.BLOCK_CHANGE, entity.getBlockPos());
                this.bounce(entity, 1.2);
                Random random = entity.world.getRandom();
                for (int i = 0; i < 6; i++) {
                    double rand = random.nextBoolean() ? 0.02 : -0.02;
                    entity.world.addParticle(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(), i * rand, 0.0D, i * rand);
                }
            }
        } else {
            if (!entity.isSneaking()) {
                this.bounce(entity, 0.8);
            } else {
                entity.world.setBlockState(blockPos, blockState.with(SQUASHED, true));
                entity.getWorld().emitGameEvent(null, GameEvent.BLOCK_CHANGE, entity.getBlockPos());
                this.playSound(entity, SoundEvents.BLOCK_WOOD_STEP, 1.0F);
            }
        }

    }

    private void playSound(Entity entity, SoundEvent soundEvent, float pitch) {
        World world = entity.getWorld();
        world.playSound(null, entity.getBlockPos(), soundEvent, SoundCategory.BLOCKS, 1.0F, pitch);
    }

    protected void bounce(Entity entity, double strength) {
        Vec3d vec3d = entity.getVelocity();
        entity.setVelocity(vec3d.x, strength, vec3d.z);
        this.playSound(entity, Sounds.BLOCK_TRAMPOLINE_RELEASE, (float) strength + 0.2F);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, SQUASHED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl);
    }
}

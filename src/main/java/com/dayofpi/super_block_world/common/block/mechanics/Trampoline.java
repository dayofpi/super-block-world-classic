package com.dayofpi.super_block_world.common.block.mechanics;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class Trampoline extends FallingBlock implements Waterloggable {
    protected static final BooleanProperty WATERLOGGED;
    protected static final BooleanProperty SQUASH;
    protected static final VoxelShape SQUASHED_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    public Trampoline(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SQUASH, false));
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        SQUASH = BooleanProperty.of("squash");
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(SQUASH) ? SQUASHED_SHAPE : VoxelShapes.fullCube();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.0F, DamageSource.FALL);
        if (entity.isSneaking())
            world.setBlockState(pos, state.with(SQUASH, true));
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        BlockState blockState = world.getBlockState(entity.getLandingPos());
        if (world.getBlockState(entity.getLandingPos()).isOf(BlockInit.TRAMPOLINE) && world.getBlockState(entity.getLandingPos()).get(SQUASH)) {
            // If squashed
            if (!entity.isSneaking()) {
                entity.world.setBlockState(entity.getLandingPos(), blockState.with(SQUASH, false));
                this.bounce(entity, 1.2);
                entity.playSound(SoundInit.BLOCK_TRAMPOLINE_SUPER_JUMP, 1.0F, this.getSoundPitch(entity));
                Random random = new Random();
                for (int i = 0; i < 6; i++) {
                    double rand = random.nextBoolean() ? 0.02 : -0.02;
                    entity.world.addParticle(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(), i * rand, 0.0D, i * rand);
                }
            }
        }
        else if (world.getBlockState(entity.getLandingPos()).isOf(BlockInit.TRAMPOLINE) && !world.getBlockState(entity.getLandingPos()).get(SQUASH)) {
            // If not squashed
            if (!entity.isSneaking()) {
                this.bounce(entity, 0.8);
                entity.playSound(SoundInit.BLOCK_TRAMPOLINE_JUMP, 1.0F, this.getSoundPitch(entity));
            }  else {
                entity.world.setBlockState(entity.getLandingPos(), blockState.with(SQUASH, true));
                entity.playSound(SoundEvents.BLOCK_WOOD_STEP, 1.0F, 1.1F);
            }
        }

    }

    protected void bounce(Entity entity, double strength) {
        Vec3d vec3d = entity.getVelocity();
        entity.setVelocity(vec3d.x, strength, vec3d.z);

    }

    public float getSoundPitch(Entity entity) {
        Random random = new Random();
        if (entity instanceof LivingEntity livingEntity && livingEntity.isBaby()) {
            return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.5F;
        } else return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, SQUASH);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl);
    }
}

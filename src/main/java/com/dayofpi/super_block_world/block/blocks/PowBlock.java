package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PowBlock extends ReactiveBlock {
    private static final IntProperty LEVEL;
    private static final BooleanProperty POWERED;
    private static final VoxelShape SHAPE_1;
    private static final VoxelShape SHAPE_2;

    static {
        LEVEL = IntProperty.of("level", 0, 2);
        POWERED = Properties.POWERED;
        SHAPE_1 = Block.createCuboidShape(0.0D, 2.0D, 0.0D, 16.0D, 14.0D, 16.0D);
        SHAPE_2 = Block.createCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    }

    @Nullable
    private ServerPlayerEntity player = null;

    public PowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LEVEL, 0).with(POWERED, false));
    }

    @Override
    public void react(World world, BlockPos blockPos, @Nullable LivingEntity entity) {
        if (entity instanceof ServerPlayerEntity)
            player = (ServerPlayerEntity) entity;
        world.createAndScheduleBlockTick(blockPos, this, 2);
    }

    private void pow(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        world.emitGameEvent(player, GameEvent.EXPLODE, blockPos);

        List<LivingEntity> entityList = world.getEntitiesByClass(LivingEntity.class, Box.from(Vec3d.ofCenter(blockPos)).expand(5, 4, 5), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
        entityList.forEach(livingEntity -> {
            livingEntity.damage(ModDamageSource.POW, 5);
            Vec3d vec3d = livingEntity.getVelocity();
            livingEntity.setVelocity(vec3d.x, 0.2D, vec3d.z);
        });

        world.playSound(null, blockPos, Sounds.BLOCK_POW_BLOCK_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F + (state.get(LEVEL)) * 0.12F);

        for (int i = 0; i < 4; ++i) {
            world.spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        if (state.get(LEVEL) < 2) {
            world.setBlockState(blockPos, state.with(LEVEL, state.get(LEVEL) + 1).with(POWERED, true));
            world.createAndScheduleBlockTick(blockPos, this, 4);
        } else
            world.removeBlock(blockPos, false);

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 6; ++i) {
            mutable.set(blockPos.offset(Direction.byId(i)));
            BlockState neighbor = world.getBlockState(mutable);
            if (neighbor.getBlock() instanceof ReactiveBlock reactiveBlock)
                reactiveBlock.react(world, mutable, null);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        if (!state.get(POWERED))
            pow(state, world, blockPos, random);
        else if (!world.isReceivingRedstonePower(blockPos)) {
            world.setBlockState(blockPos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(LEVEL)) {
            default -> VoxelShapes.fullCube();
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
        };
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            boolean bl = state.get(POWERED);
            if (bl != world.isReceivingRedstonePower(blockPos)) {
                if (bl) {
                    world.createAndScheduleBlockTick(blockPos, this, 4);
                } else {
                    world.setBlockState(blockPos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                    pow(state, (ServerWorld) world, blockPos, world.getRandom());
                }
            }
        }
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(LEVEL) != 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(POWERED);
    }
}

package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class DashBlock extends HorizontalFacingBlock {
    private static final DirectionProperty FACING;
    public static final BooleanProperty USED;

    static {
        FACING = HorizontalFacingBlock.FACING;
        USED = BooleanProperty.of("used");
    }

    public DashBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(USED, false));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.getBlockState(pos).get(USED)) {
            if (!world.isClient) {
                world.setBlockState(pos, state.with(USED, true));
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
                world.createAndScheduleBlockTick(pos, this, 20);
            }
            Vec3d vec3d = entity.getVelocity();
            double boost = 1.0D;
            if (state.get(FACING) == Direction.EAST)
                entity.setVelocity(vec3d.x + boost, vec3d.y, vec3d.z);
            else if (state.get(FACING) == Direction.WEST)
                entity.setVelocity(vec3d.x - boost, vec3d.y, vec3d.z);
            else if (state.get(FACING) == Direction.SOUTH)
                entity.setVelocity(vec3d.x, vec3d.y, vec3d.z + boost);
            else
                entity.setVelocity(vec3d.x, vec3d.y, vec3d.z - boost);
            if (entity instanceof LivingEntity)
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 1, true, false));
            world.playSound(null, pos, Sounds.BLOCK_DASH_BLOCK_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);

        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(USED, false));
        world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, USED);
    }

}

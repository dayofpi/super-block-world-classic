package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PSwitchBlock extends AbstractPressurePlateBlock {
    private static final BooleanProperty POWERED = Properties.POWERED;
    private static final IntProperty TIME = IntProperty.of("time", 1, 10);
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

    public PSwitchBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false).with(TIME, 1));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(TIME) == 10)
            world.setBlockState(pos, state.with(TIME, 1));
        else
            world.setBlockState(pos, state.with(TIME, state.get(TIME) + 1));
        world.playSound(null, pos, Sounds.BLOCK_P_SWITCH_CLICK, SoundCategory.BLOCKS, 0.5F, 1.0F);
        return ActionResult.success(true);
    }

    @Override
    protected void updatePlateState(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int output) {
        int redstoneOutput = this.getRedstoneOutput(world, pos);
        boolean bl = output > 0;
        boolean bl2 = redstoneOutput > 0;
        if (output != redstoneOutput) {
            BlockState blockState = this.setRedstoneOutput(state, redstoneOutput);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            this.updateNeighbors(world, pos);
            world.scheduleBlockRerenderIfNeeded(pos, state, blockState);
        }
        if (!bl2 && bl) {
            this.playDepressSound(world, pos);
            world.emitGameEvent(entity, GameEvent.BLOCK_DEACTIVATE, pos);
        } else if (bl2 && !bl) {
            this.playPressSound(world, pos);
            world.emitGameEvent(entity, GameEvent.BLOCK_ACTIVATE, pos);
        }
        if (bl2) {
            world.scheduleBlockTick(new BlockPos(pos), this, this.getTickRate(state));
        }
    }

    private int getTickRate(BlockState state) {
        int time = state.get(TIME);
        return time * 20;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void playPressSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, Sounds.BLOCK_P_SWITCH_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void playDepressSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, Sounds.BLOCK_P_SWITCH_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected int getRedstoneOutput(World world, BlockPos pos) {
        Box box = BOX.offset(pos);
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, box);

        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity.canAvoidTraps()) continue;
                return 15;
            }
        }
        return 0;
    }

    @Override
    protected int getRedstoneOutput(BlockState state) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setRedstoneOutput(BlockState state, int rsOut) {
        return state.with(POWERED, rsOut > 0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED).add(TIME);
    }
}

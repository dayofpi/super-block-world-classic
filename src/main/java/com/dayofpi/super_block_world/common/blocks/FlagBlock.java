package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.block_entities.FlagBE;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class FlagBlock extends FlagpoleBlock implements BlockEntityProvider {
    public static final List<String> TRANS = List.of("Trans", "trans", "transgender", "Transgender");
    public static final List<String> BI = List.of("Bi", "bisexual", "Bisexual");
    public static final List<String> LESBIAN = List.of("Lesbian", "lesbian");

    public static final IntProperty ROTATION = Properties.ROTATION;
    private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(6.0, 12.0, 6.0, 10.0, 16.0, 10.0);
    private static final VoxelShape SHAPE = VoxelShapes.union(FLAGPOLE_SHAPE, TOP_SHAPE);
    private final boolean rainbow;
    private final DyeColor color;

    public FlagBlock(DyeColor color, Settings settings) {
        super(settings);
        this.rainbow = false;
        this.color = color;
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0));
    }

    public FlagBlock(Settings settings) {
        super(settings);
        this.rainbow = true;
        this.color = DyeColor.WHITE;
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0));
    }

    public boolean isRainbow() {
        return this.rainbow;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof FlagBE) {
            ((FlagBE) blockEntity).setCustomName(itemStack.getName());
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        state = state.cycle(ROTATION);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
        world.updateComparators(pos, this);
        world.playSound(null, pos, SoundEvents.UI_LOOM_SELECT_PATTERN, SoundCategory.BLOCKS, 0.5F, 1.0F);
        return ActionResult.CONSUME;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(ROTATION);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState state) {
        return new FlagBE(blockPos, state, this.getColor(), this.isRainbow());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) {
            return false;
        }
        return blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

}

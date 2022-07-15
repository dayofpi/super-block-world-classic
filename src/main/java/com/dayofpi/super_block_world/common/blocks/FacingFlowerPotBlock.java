package com.dayofpi.super_block_world.common.blocks;

import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

@SuppressWarnings("deprecation")
public class FacingFlowerPotBlock extends HorizontalFacingBlock {
    private static final Map<Block, Block> CONTENT_TO_POTTED = Maps.newHashMap();
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final Block content;

    public FacingFlowerPotBlock(Block content, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
        this.content = content;
        CONTENT_TO_POTTED.put(content, this);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean bl2;
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        BlockState blockState = (item instanceof BlockItem ? CONTENT_TO_POTTED.getOrDefault(((BlockItem)item).getBlock(), Blocks.AIR) : Blocks.AIR).getDefaultState();
        boolean bl = blockState.isOf(Blocks.AIR);
        if (bl != (bl2 = this.isEmpty())) {
            if (bl2) {
                world.setBlockState(pos, blockState.with(FACING, player.getHorizontalFacing().getOpposite()), Block.NOTIFY_ALL);
                player.incrementStat(Stats.POT_FLOWER);
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
            } else {
                ItemStack itemStack2 = new ItemStack(this.content);
                if (itemStack.isEmpty()) {
                    player.setStackInHand(hand, itemStack2);
                } else if (!player.giveItemStack(itemStack2)) {
                    player.dropItem(itemStack2, false);
                }
                world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), Block.NOTIFY_ALL);
            }
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if (this.isEmpty()) {
            return super.getPickStack(world, pos, state);
        }
        return new ItemStack(this.content);
    }

    private boolean isEmpty() {
        return this.content == Blocks.AIR;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

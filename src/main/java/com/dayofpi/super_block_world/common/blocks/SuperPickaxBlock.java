package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.common.block_entities.SuperPickaxBE;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class SuperPickaxBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    private static final int MAX_USES = 50;
    public static final IntProperty USES = IntProperty.of("uses", 0, MAX_USES);
    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    public SuperPickaxBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ACTIVE, false).with(USES, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE, USES);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ItemStack itemStack = ctx.getStack();
        BlockState state = this.getDefaultState().with(FACING, ctx.getPlayerFacing());
        if (itemStack.isDamaged()) {
            state = state.with(USES, itemStack.getDamage());
        }
        return state;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(ACTIVE), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(ACTIVE) && (entity instanceof LivingEntity || entity instanceof FallingBlockEntity))
            entity.damage(ModDamageSource.POW, 5.0F);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        ItemStack itemStack = new ItemStack(ModBlocks.SUPER_PICKAX);
        itemStack.setDamage(state.get(USES));
        return List.of(itemStack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        state = state.cycle(ACTIVE);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        world.createAndScheduleBlockTick(pos, this, 4);
        return ActionResult.success(world.isClient);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(ACTIVE)) return;
        BlockPos targetPos = pos.offset(state.get(FACING));
        BlockState target = world.getBlockState(targetPos);
        if (state.get(USES) >= MAX_USES) {
            world.breakBlock(pos, false);
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        } else {
            if (target.isOf(this))
                return;
            float hardness = target.getHardness(world, targetPos);
            if (hardness > -1.0F && hardness < 50.0F) {
                world.breakBlock(targetPos, true);
                world.setBlockState(targetPos, state.with(USES, state.get(USES) + 1));
                world.createAndScheduleBlockTick(targetPos, this, 4);
                world.removeBlock(pos, false);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SuperPickaxBE(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}

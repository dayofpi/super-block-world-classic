package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.block_entities.YoshiEggBE;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class YoshiEggBlock extends Block implements BlockEntityProvider, Waterloggable {
    private static final BooleanProperty WATERLOGGED;
    private static final BooleanProperty NATURAL;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        NATURAL = BooleanProperty.of("natural");
    }

    public YoshiEggBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NATURAL, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NATURAL, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean isInFluid = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, isInFluid);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof YoshiEggBE) {
            return ((YoshiEggBE) blockEntity).getStoredEntity().isEmpty() ? 0 : 15;
        }
        return super.getComparatorOutput(state, world, pos);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if (!(blockEntity instanceof YoshiEggBE))
            return;
        NbtCompound nbtCompound = ((YoshiEggBE) blockEntity).getStoredEntity();
        if (nbtCompound == null)
            return;
        if (!world.isClient()) {
            if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0 && nbtCompound.isEmpty()) {
                if (state.get(NATURAL)) {
                    Random random = world.getRandom();
                    ItemStack itemStack = new ItemStack(ModItems.COIN);
                    if (random.nextInt(10) < 5)
                        itemStack = new ItemStack(ModItems.SUPER_MUSHROOM);
                    if (random.nextInt(10) == 0)
                        itemStack = new ItemStack(ModItems.ONE_UP);
                    Block.dropStack(world, pos, itemStack);
                }
            } else if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0 && !nbtCompound.isEmpty()) {
                EntityType.getEntityFromNbt(nbtCompound, world).ifPresent(entity -> {
                    entity.setPosition(pos.getX(), pos.getY() + (double) 0.1f, pos.getZ());
                    ((ServerWorld) world).tryLoadEntity(entity);
                    world.playSound(null, pos, Sounds.BLOCK_YOSHI_EGG_HATCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                });
            } else if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0) {
                ItemStack itemStack = new ItemStack(this.asItem());
                blockEntity.setStackNbt(itemStack);
                NbtCompound itemCompound = itemStack.getNbt();
                if (itemCompound != null)
                    itemCompound.putBoolean("HasEntity", true);

                ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world instanceof ServerWorld && player.isCreative()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity == null)
                return;
            NbtCompound nbtCompound = ((YoshiEggBE) blockEntity).getStoredEntity();
            if (nbtCompound == null)
                return;
            EntityType.getEntityFromNbt(nbtCompound, world).ifPresent(storedEntity -> {
                storedEntity.setPosition(pos.getX(), pos.getY() + (double) 0.1f, pos.getZ());
                ((ServerWorld) world).tryLoadEntity(storedEntity);
                world.playSound(null, pos, Sounds.BLOCK_YOSHI_EGG_HATCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            });
        }
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        BlockEntity blockEntity;
        if (!((blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY)) instanceof YoshiEggBE))
            return super.getDroppedStacks(state, builder);
        Entity entity = builder.getNullable(LootContextParameters.THIS_ENTITY);
        if (!(entity instanceof PlayerEntity)) {
            ServerWorld world = builder.getWorld();
            BlockPos pos = blockEntity.getPos();
            NbtCompound nbtCompound = ((YoshiEggBE) blockEntity).getStoredEntity();
            if (nbtCompound == null)
                return super.getDroppedStacks(state, builder);
            if (!nbtCompound.isEmpty()) {
                EntityType.getEntityFromNbt(nbtCompound, world).ifPresent(storedEntity -> {
                    storedEntity.setPosition(pos.getX(), pos.getY() + (double) 0.1f, pos.getZ());
                    world.tryLoadEntity(storedEntity);
                    world.playSound(null, pos, Sounds.BLOCK_YOSHI_EGG_HATCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                });
            }
        }
        return super.getDroppedStacks(state, builder);
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new YoshiEggBE(pos, state);
    }
}

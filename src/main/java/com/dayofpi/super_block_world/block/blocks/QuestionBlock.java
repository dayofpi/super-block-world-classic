package com.dayofpi.super_block_world.block.blocks;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.world.WorldInit;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.block_entities.QuestionBlockBE;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.criterion.ModCriteria;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
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
public class QuestionBlock extends ReactiveBlock implements BlockEntityProvider {
    private static final IntProperty TYPE;

    static {
        TYPE = IntProperty.of("type", 0, 6);
    }

    public QuestionBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, 0));
    }

    private static int getTypeFromStack(ItemStack stack) {
        if (stack.isOf(ModItems.BOTTLED_GHOST))
            return 1;
        if (stack.isOf(ModBlocks.TOADSTONE_BRICKS.asItem()))
            return 2;
        if (stack.isOf(ModBlocks.GLOOMSTONE_BRICKS.asItem()))
            return 3;
        if (stack.isOf(ModBlocks.SEASTONE_BRICKS.asItem()))
            return 4;
        if (stack.isOf(ModBlocks.GOLDEN_BRICKS.asItem()))
            return 5;
        if (stack.isOf(ModBlocks.CRYSTAL_BRICKS.asItem()))
            return 6;
        return 0;
    }

    private static ItemStack getStackFromType(int type) {
        Item item = switch (type) {
            default -> ModItems.BOTTLED_GHOST;
            case 2 -> ModBlocks.TOADSTONE_BRICKS.asItem();
            case 3 -> ModBlocks.GLOOMSTONE_BRICKS.asItem();
            case 4 -> ModBlocks.SEASTONE_BRICKS.asItem();
            case 5 -> ModBlocks.GOLDEN_BRICKS.asItem();
            case 6 -> ModBlocks.CRYSTAL_BRICKS.asItem();
        };
        return new ItemStack(item);
    }

    public static List<ItemStack> defaultItems(ServerWorld world, BlockState state, BlockPos blockPos) {
        LootContext lootContext = new LootContext.Builder(world).parameter(LootContextParameters.BLOCK_STATE, state).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos)).build(LootContextTypes.BLOCK);
        Identifier mushroom_kingdom = new Identifier(Main.MOD_ID,"question_blocks/mushroom_kingdom");
        Identifier bowsers_kingdom = new Identifier(Main.MOD_ID,"question_blocks/bowsers_kingdom");
        LootTable lootTable = lootContext.getWorld().getServer().getLootManager().getTable(world.getRegistryKey() == WorldInit.BOWSERS_KINGDOM_WORLD ? bowsers_kingdom : mushroom_kingdom);

        return lootTable.generateLoot(lootContext);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == 1)
            return VoxelShapes.empty();
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(TYPE) == 1;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState state) {
        return new QuestionBlockBE(blockPos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        int type = state.get(TYPE);
        if (type > 0) {
            Block.dropStack(world, blockPos, getStackFromType(type));
            world.setBlockState(blockPos, state.with(TYPE, 0));
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
            world.playSound(null, blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.success(world.isClient);
        } else if (getTypeFromStack(itemStack) > 0) {
            world.setBlockState(blockPos, state.with(TYPE, getTypeFromStack(itemStack)));
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
            if (!player.isCreative())
                itemStack.decrement(1);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (state.get(TYPE) == 1)
            return BlockRenderType.INVISIBLE;
        return super.getRenderType(state);
    }

    public void empty(World world, BlockPos blockPos) {
        world.setBlockState(blockPos, ModBlocks.EMPTY_BLOCK.getDefaultState());
        world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
    }

    public void playSound(World world, BlockPos blockPos, SoundEvent soundEvent) {
        world.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        Inventory inventory = (Inventory) world.getBlockEntity(blockPos);
        if (inventory == null)
            return;
        if (inventory.getStack(0).isEmpty()) {
            ItemStack fallback = defaultItems(world, world.getBlockState(blockPos), blockPos).get(0);
            inventory.setStack(0, fallback);
        }
        react(world, blockPos, random, inventory);
    }

    private void react(ServerWorld world, BlockPos blockPos, Random random, Inventory inventory) {
        ItemStack itemStack = inventory.getStack(0);
        if (itemStack.isOf(ModItems.COIN)) {
            if (itemStack.getCount() == 1)
                empty(world, blockPos);
            itemStack.decrement(1);
            dropStack(world, blockPos, new ItemStack(ModItems.COIN));
            playSound(world, blockPos, Sounds.ITEM_COIN);
        } else {
            empty(world, blockPos);
            dropStack(world, blockPos, itemStack);
            playSound(world, blockPos, Sounds.BLOCK_QUESTION_BLOCK_ACTIVATE);
        }

        world.spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void react(World world, BlockPos blockPos, @Nullable LivingEntity entity) {
        if (world.isClient)
            return;
        if (entity instanceof ServerPlayerEntity)
            ModCriteria.JUMP_UNDER_BLOCK.trigger((ServerPlayerEntity) entity);
        world.scheduleBlockTick(blockPos, this, 1);
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
}

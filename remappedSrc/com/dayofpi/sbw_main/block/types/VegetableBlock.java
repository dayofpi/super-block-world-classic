package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("deprecation")
public class VegetableBlock extends Block {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public VegetableBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    private List<ItemStack> getDroppedItem(ServerWorld world, BlockState state, BlockPos blockPos) {
        LootContext lootContext = new LootContext.Builder(world).parameter(LootContextParameters.BLOCK_STATE, state).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos)).build(LootContextTypes.BLOCK);
        ServerWorld serverWorld = lootContext.getWorld();

        LootTable lootTable = serverWorld.getServer().getLootManager().getTable(this.getLootTableId());
        return lootTable.generateLoot(lootContext);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            if (!world.isClient()) {
                List<ItemStack> list = this.getDroppedItem((ServerWorld) world, state, blockPos);
                Iterator<ItemStack> stackIterator = list.iterator();
                player.setStackInHand(hand, stackIterator.next());
                world.removeBlock(blockPos, false);
                world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_PLUCK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            } else {
                world.addBlockBreakParticles(blockPos.down(), world.getBlockState(blockPos.down()));
            } return ActionResult.success(world.isClient);
        } else return ActionResult.PASS;

    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isSolidBlock(world, pos);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return type == NavigationType.AIR && !this.collidable || super.canPathfindThrough(state, world, pos, type);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }
}

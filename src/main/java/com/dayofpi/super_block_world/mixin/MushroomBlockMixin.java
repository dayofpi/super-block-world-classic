package com.dayofpi.super_block_world.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomBlock.class)
@SuppressWarnings("deprecation")
public class MushroomBlockMixin extends Block {
    public MushroomBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "getPlacementState", cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(this.getDefaultState());
    }

    @Inject(at = @At("HEAD"), method = "getStateForNeighborUpdate", cancellable = true)
    private void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Direction direction = hit.getSide();
        BooleanProperty capSide = null;
        if (direction == Direction.UP) {
            capSide = MushroomBlock.UP;
        } else if (direction == Direction.DOWN) {
            capSide = MushroomBlock.DOWN;
        } else if (direction == Direction.EAST) {
            capSide = MushroomBlock.EAST;
        } else if (direction == Direction.WEST) {
            capSide = MushroomBlock.WEST;
        } else if (direction == Direction.NORTH) {
            capSide = MushroomBlock.NORTH;
        } else if (direction == Direction.SOUTH) {
            capSide = MushroomBlock.SOUTH;
        }
        if (itemStack.getItem() instanceof AxeItem) {
            world.playSound(player, pos, SoundEvents.BLOCK_WART_BLOCK_BREAK, SoundCategory.BLOCKS, 0.6F, 1.2F);
            world.setBlockState(pos, state.cycle(capSide), 2);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
}

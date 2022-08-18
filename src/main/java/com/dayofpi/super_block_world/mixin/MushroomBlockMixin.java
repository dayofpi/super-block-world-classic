package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
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

    private boolean isBouncy(BlockState blockState) {
        return blockState.isOf(Blocks.RED_MUSHROOM_BLOCK) || blockState.isOf(ModBlocks.ORANGE_MUSHROOM_BLOCK) || blockState.isOf(ModBlocks.PINK_MUSHROOM_BLOCK);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else if (this.isBouncy(state)) {
            super.onLandedUpon(world, state, pos, entity, fallDistance * 0.5f);
        }
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else if (this.isBouncy(world.getBlockState(entity.getLandingPos()))){
            this.bounce(entity);
        }
    }

    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            if (entity.fallDistance > 0)
                entity.world.playSound(null, entity.getBlockPos(), Sounds.BLOCK_MUSHROOM_BLOCK_BOUNCE, SoundCategory.BLOCKS, 0.2F, 1.0F);
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
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
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 0.6F, 1.2F);
            world.setBlockState(pos, state.cycle(capSide), 2);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
}

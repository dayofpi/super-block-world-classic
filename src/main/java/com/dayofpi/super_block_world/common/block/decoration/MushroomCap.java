package com.dayofpi.super_block_world.common.block.decoration;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class MushroomCap extends Block {
    private final boolean bouncy;
    private static final BooleanProperty DOWN;
    protected static final BooleanProperty NORTH;
    protected static final BooleanProperty EAST;
    protected static final BooleanProperty SOUTH;
    protected static final BooleanProperty WEST;
    protected static final BooleanProperty UP;

    static {
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        UP = ConnectingBlock.UP;
        DOWN = ConnectingBlock.DOWN;
    }

    public MushroomCap(boolean bouncy, Settings settings) {
        super(settings);
        this.bouncy = bouncy;
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, true));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0.5F, DamageSource.FALL);
            if (entity.fallDistance > 0 && this.bouncy)
                entity.playSound(SoundEvents.BLOCK_WART_BLOCK_STEP, 0.8F, 1.0F);
        }

    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else if (this.bouncy) {
            this.bounce(entity);
        }

    }

    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    // Striping
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Direction direction = hit.getSide();
        BooleanProperty capSide = null;
        if (direction == Direction.UP) {
            capSide = MushroomCap.UP;
        } else if (direction == Direction.DOWN) {
            capSide = MushroomCap.DOWN;
        } else if (direction == Direction.EAST) {
            capSide = MushroomCap.EAST;
        } else if (direction == Direction.WEST) {
            capSide = MushroomCap.WEST;
        } else if (direction == Direction.NORTH) {
            capSide = MushroomCap.NORTH;
        } else if (direction == Direction.SOUTH) {
            capSide = MushroomCap.SOUTH;
        }
        if (itemStack.isIn(FabricToolTags.AXES)) {
            world.playSound(player, pos, SoundEvents.BLOCK_WART_BLOCK_BREAK, SoundCategory.BLOCKS, 0.6F, 1.2F);
            world.setBlockState(pos, state.cycle(capSide), 2);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
}

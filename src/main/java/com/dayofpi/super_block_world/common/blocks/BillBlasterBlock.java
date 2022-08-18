package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.hostile.BulletBillEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.util.DirectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BillBlasterBlock extends Block {
    private static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    private static final BooleanProperty POWERED = Properties.POWERED;

    public BillBlasterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X).with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, POWERED);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        Random random = world.random;
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0 && dropExperience) {
            int i = 5 + random.nextInt(15) + random.nextInt(15);
            this.dropExperience(world, pos, i);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block block, BlockPos fromPos, boolean notify) {
        boolean isPowered = state.get(POWERED);
        if (isPowered != world.isReceivingRedstonePower(blockPos)) {
            if (isPowered) {
                world.createAndScheduleBlockTick(blockPos, this, 30);
            } else {
                world.setBlockState(blockPos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                world.createAndScheduleBlockTick(blockPos, this, 30);
            }

        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            if (!world.isReceivingRedstonePower(pos)) {
                world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                return;
            }
        } else return;
        world.createAndScheduleBlockTick(pos, this, 30);
        Box box = Box.from(Vec3d.ofCenter(pos)).expand(32);
        List<BulletBillEntity> bulletBillEntities = world.getEntitiesByClass(BulletBillEntity.class, box, bulletBillEntity -> bulletBillEntity.getHomePos() == pos);
        if (!bulletBillEntities.isEmpty()) return;
        LivingEntity target;
        target = getTarget(world, pos, box);
        if (target == null) return;
        prepareBulletBill(state, world, pos, target);
    }

    private static int getDistance(BlockPos blockPos1, BlockPos blockPos2) {
        return blockPos1.getManhattanDistance(blockPos2);
    }

    private static void prepareBulletBill(BlockState state, ServerWorld world, BlockPos origin, LivingEntity target) {
        BlockPos targetPos = target.getBlockPos();
        Direction direction = Direction.from(state.get(AXIS), Direction.AxisDirection.POSITIVE).rotateYClockwise();
        BlockPos front = origin.offset(direction, 1);
        BlockPos back = origin.offset(direction.getOpposite(), 1);
        int frontDistance = getDistance(targetPos, front);
        int backDistance = getDistance(targetPos, back);
        boolean isFrontCloser = frontDistance < backDistance;
        BlockPos spawnPos;
        if (isFrontCloser)
            spawnPos = front;
        else spawnPos = back;
        spawnBulletBill(world, origin, target, direction, spawnPos, isFrontCloser);
    }

    private static void spawnBulletBill(ServerWorld world, BlockPos origin, LivingEntity target, Direction direction, BlockPos spawnPos, boolean isFrontCloser) {
        if (!world.getBlockState(spawnPos).isSolidBlock(world, spawnPos)) {
            BulletBillEntity bulletBill = ModEntities.BULLET_BILL.create(world);
            if (bulletBill != null) {
                world.playSound(null, origin, Sounds.ENTITY_BULLET_FIRE, SoundCategory.HOSTILE, 1.0F, 1.0F);
                bulletBill.updatePositionAndAngles(spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5, DirectionHelper.directionToInt(isFrontCloser ? direction : direction.getOpposite()), 0.0F);
                bulletBill.setTarget(target);
                bulletBill.setHomePos(origin);
                world.spawnEntity(bulletBill);
            }
        }
    }

    @Nullable
    private static LivingEntity getTarget(ServerWorld world, BlockPos pos, Box box) {
        LivingEntity target;
        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 16.0, true);
        if (playerEntity == null) {
            List<IronGolemEntity> ironGolemEntities = world.getEntitiesByClass(IronGolemEntity.class, box, EntityPredicates.VALID_LIVING_ENTITY);
            IronGolemEntity ironGolemEntity = world.getClosestEntity(ironGolemEntities, TargetPredicate.DEFAULT, null, pos.getX(), pos.getY(), pos.getZ());
            if (ironGolemEntity != null)
                target = ironGolemEntity;
            else return null;
        } else {
            target = playerEntity;
        }
        return target;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
                switch (state.get(AXIS)) {
                    case X -> {
                        return state.with(AXIS, Direction.Axis.Z);
                    }
                    case Z -> {
                        return state.with(AXIS, Direction.Axis.X);
                    }
                }
                return state;
            }
        }
        return state;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getPlayerFacing().rotateYClockwise().getAxis()).with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.createAndScheduleBlockTick(pos, this, 10);
    }
}

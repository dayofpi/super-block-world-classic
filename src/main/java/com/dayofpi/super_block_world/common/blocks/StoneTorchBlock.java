package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.projectile.ModFireballEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class StoneTorchBlock extends Block implements Waterloggable {
    private static final BooleanProperty LIT;
    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);

    static {
        LIT = Properties.LIT;
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public StoneTorchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, true).with(WATERLOGGED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean extinguish = itemStack.getItem() instanceof ShovelItem;
        boolean ignite = itemStack.isOf(Items.FLINT_AND_STEEL);
        boolean ignite2 = itemStack.isOf(Items.FIRE_CHARGE);
        if (state.get(LIT)) {
            if (extinguish) {
                world.setBlockState(pos, state.cycle(LIT), 1);
                world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, pos, 0);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                return ActionResult.success(world.isClient);
            }
        } else if (!state.get(WATERLOGGED)) {
            if (ignite || ignite2) {
                world.setBlockState(pos, state.cycle(LIT), 1);
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (ignite)
                    itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                else
                    itemStack.decrement(1);
                return ActionResult.success(world.isClient);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LIT) ? 15 : 0;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos blockPos = hit.getBlockPos();
        boolean isProjectileFiery = projectile.isOnFire() || projectile instanceof ModFireballEntity;
        if (!world.isClient && isProjectileFiery && projectile.canModifyAt(world, blockPos) && !(Boolean) state.get(LIT)) {
            world.setBlockState(blockPos, state.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            if (projectile instanceof ModFireballEntity)
                projectile.discard();
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(LIT)) {
            if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.damage(DamageSource.IN_FIRE, 1F);
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = (double) pos.getX() + 0.5D;
            double e = (double) pos.getY() + 1.0D;
            double f = (double) pos.getZ() + 0.5D;
            world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            if (random.nextInt(10) == 0)
                world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, Sounds.BLOCK_STONE_TORCH_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.6F, true);

        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(LIT, !bl);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }

}

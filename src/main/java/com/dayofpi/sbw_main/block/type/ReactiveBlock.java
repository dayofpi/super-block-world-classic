package com.dayofpi.sbw_main.block.type;

import com.dayofpi.sbw_main.entity.type.bases.AbstractBuzzy;
import com.dayofpi.sbw_main.entity.type.mobs.ThwompEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
@SuppressWarnings("deprecation")
public abstract class ReactiveBlock extends Block {
    public ReactiveBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos blockPos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(blockPos)) {
            this.activate(world.getBlockState(blockPos), world, blockPos);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.2D, 0.0D, 16.0D, 16D, 16.0D);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos blockPos, PlayerEntity player) {
        if (!world.isClient) {
            this.activate(state, world, blockPos);
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof ThwompEntity thwompEntity && ((ThwompEntity) entity).getStage() == 3)
            this.activate(state, world, pos);
        else if (entity instanceof AbstractBuzzy && ((AbstractBuzzy) entity).isUpsideDown() && entity.fallDistance > 0)
            this.activate(state, world, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        boolean jumpUnder = entity.getY() < blockPos.getY();
        if (jumpUnder) {
            this.activate(state, world, blockPos);
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        this.activate(state, world, hit.getBlockPos());
    }

    public void activate(BlockState state, World world, BlockPos blockPos) {}
}

package com.dayofpi.sbw_main.block.types;

import com.dayofpi.sbw_main.entity.types.AbstractShell;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BrickBlock extends Block {
    public BrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.5D, 0.5D, 0.5D, 15.5D, 16D, 15.5D);
    }

    public void breakImmediately(World world, BlockPos blockPos) {
        world.breakBlock(blockPos, false);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        //boolean bool1 = entity.getY() < blockPos.getY() && entity instanceof LivingEntity livingEntity && livingEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagList.SHELLMETS);
        boolean shellHits = entity instanceof AbstractShell shellEntity && shellEntity.isSpinning() && entity.getBlockPos().getY() == blockPos.getY();
        if (shellHits) {
            this.breakImmediately(world, blockPos);
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }
}

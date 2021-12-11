package com.dayofpi.sbw_main.block.type;

import com.dayofpi.sbw_main.ModTags;
import com.dayofpi.sbw_main.entity.type.bases.AbstractBuzzy;
import com.dayofpi.sbw_main.entity.type.mobs.ThwompEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BrickBlock extends Block {
    public BrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.2D, 0.0D, 16.0D, 16D, 16.0D);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos blockPos, Entity entity) {
        boolean jumpUnder = entity.getY() < blockPos.getY();
        if (jumpUnder && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(ModTags.SHELLMETS))
            world.breakBlock(blockPos, true);
        }
        super.onEntityCollision(state, world, blockPos, entity);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof ThwompEntity thwompEntity && ((ThwompEntity) entity).getStage() == 3)
            world.breakBlock(pos, false);
        else if (entity instanceof AbstractBuzzy && ((AbstractBuzzy) entity).isUpsideDown() && entity.fallDistance > 0)
            world.breakBlock(pos, false);
    }
}

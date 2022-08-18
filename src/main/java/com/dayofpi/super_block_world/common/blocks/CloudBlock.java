package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class CloudBlock extends Block {
    public CloudBlock(Settings settings) {
        super(settings);
    }

    public static boolean canWalkOnCloud(Entity entity) {
        if (entity instanceof PlayerEntity && entity.getDataTracker().get(FormManager.POWER_UP).equals(PowerUp.BEE.asString()))
            return true;
        if (entity instanceof LivingEntity) {
            return ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.CLOUD_BOOTS);
        }
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!(context instanceof EntityShapeContext))
            return VoxelShapes.empty();
        Entity entity = ((EntityShapeContext) context).getEntity();
        if (entity != null) {
            boolean bl = entity instanceof FallingBlockEntity;
            if (bl || canWalkOnCloud(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false)) {
                return super.getCollisionShape(state, world, pos, context);
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }
}

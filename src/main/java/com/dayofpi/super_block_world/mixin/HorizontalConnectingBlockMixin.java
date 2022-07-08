package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@SuppressWarnings("deprecation")
@Mixin(HorizontalConnectingBlock.class)
public class HorizontalConnectingBlockMixin extends Block {
    public HorizontalConnectingBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity && entity.getDataTracker().get(FormManager.GOO_ME_UUID).isPresent()) {
            entity.slowMovement(state, new Vec3d(0.8, 0.8, 0.8));
        }
    }

    @Inject(at=@At("HEAD"), method = "getCollisionShape", cancellable = true)
    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityShapeContext) {
            Entity entity = ((EntityShapeContext) context).getEntity();
            if (entity instanceof PlayerEntity && entity.getDataTracker().get(FormManager.GOO_ME_UUID).isPresent()) {
                cir.setReturnValue(VoxelShapes.empty());
                cir.cancel();
            }
        }
    }
}

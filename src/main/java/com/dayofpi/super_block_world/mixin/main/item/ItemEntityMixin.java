package com.dayofpi.super_block_world.mixin.main.item;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at=@At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 8, 8, 8)) {
            BlockState state = world.getBlockState(blockPos);
            if (state.isOf(BlockRegistry.PULL_BLOCK) && state.get(Properties.POWERED)) {
                this.setNoGravity(true);
                Vec3d vec3d;
                double d;
                if ((d = (vec3d = new Vec3d(blockPos.getX() - this.getX(), blockPos.getY() - this.getY(), blockPos.getZ() - this.getZ())).lengthSquared()) < 64.0) {
                    double e = 1.0 - Math.sqrt(d) / 8.0;
                    this.setVelocity(this.getVelocity().add(vec3d.normalize().multiply(e * e * 0.2)));
                }
            } else {
                this.setNoGravity(false);
            }
        }
    }
}

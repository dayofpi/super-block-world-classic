package com.dayofpi.super_block_world.mixin.main.item;

import com.dayofpi.super_block_world.main.registry.main.BlockInit;
import com.dayofpi.super_block_world.main.registry.main.ItemInit;
import com.dayofpi.super_block_world.main.registry.main.TagInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getStack();

    @Shadow public abstract void setStack(ItemStack stack);

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at=@At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        if (this.updateMovementInFluid(TagInit.POISON, 0.0023)) {
            World world = this.getWorld();
            if (world.isClient)
                world.addParticle(ParticleTypes.LARGE_SMOKE, this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1, this.getBlockPos().getZ() + 0.5, 0.0D, 0.0D, 0.0D);

            if (this.getStack().isOf(ItemInit.ONE_UP)) {
                this.setStack(new ItemStack(ItemInit.POISON_MUSHROOM, this.getStack().getCount()));
                this.playSound(SoundEvents.ENTITY_ZOMBIE_INFECT, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
            } else if (!this.getStack().isOf(ItemInit.POISON_MUSHROOM)) {
                this.discard();
                this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
            }
        }

        for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 8, 8, 8)) {
            BlockState state = world.getBlockState(blockPos);
            if (state.isOf(BlockInit.PULL_BLOCK) && state.get(Properties.POWERED)) {
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

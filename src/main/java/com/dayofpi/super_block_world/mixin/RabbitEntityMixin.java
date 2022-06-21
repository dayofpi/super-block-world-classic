package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.common.entities.tasks.CleanSeedsGoal;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends PathAwareEntity {
    @SuppressWarnings("unused")
    protected RabbitEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return stack.isOf(ModItems.SMILEY_SUNFLOWER_SEED);
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            this.triggerItemPickedUpByEntityCriteria(item);
            this.sendPickup(item, itemStack.getCount());
            item.discard();
        }
    }

    @Inject(at = @At("HEAD"), method = "initGoals")
    private void initGoals(CallbackInfo ci) {
        this.goalSelector.add(5, new CleanSeedsGoal(this));
    }
}
